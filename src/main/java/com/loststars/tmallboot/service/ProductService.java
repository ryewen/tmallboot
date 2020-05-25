package com.loststars.tmallboot.service;

import java.util.LinkedList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loststars.tmallboot.dao.ProductDAO;
import com.loststars.tmallboot.dao.ProductImageDAO;
import com.loststars.tmallboot.es.ProductESDAO;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.util.PageNavigator;

@Service
@CacheConfig(cacheNames = "products")
public class ProductService {

    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ProductESDAO productESDAO;
    
    @Autowired
    private ProductImageDAO productImageDAO;
    
    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Cacheable(key = "'products-page-' + #p0 + '-' + #p1")
    public PageNavigator<Product> listProductByCategoryId(int categoryId, int start, int size, int navigatePages) {
        PageHelper.startPage(start, size);
        List<Product> products = productDAO.listProductByCategoryId(categoryId);
        for (Product product : products) {
            List<ProductImage> productImages = productImageDAO.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
            if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
        }
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        PageNavigator<Product> page = new PageNavigator<>(pageInfo, products, navigatePages);
        return page;
    }
    
    @CacheEvict(allEntries = true)
    public void addProduct(Product product) {
        productDAO.addProduct(product);
        productESDAO.save(product);
    }
    
    @Cacheable(key = "'product-' + #p0")
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }
    
    @CacheEvict(allEntries = true)
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
        productESDAO.save(product);
    }
    
    @CacheEvict(allEntries = true)
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
    
    public List<Product> listProductsByKeyWord(String keyWord) {
        initESDatabase();
        BoolQueryBuilder orderQuery = QueryBuilders.boolQuery();
        orderQuery.must(QueryBuilders.matchQuery("name", keyWord));
        Iterable<Product> searchPageResults = productESDAO.search(orderQuery);
        List<Product> products = new LinkedList<>();
        searchPageResults.forEach((product) -> {
            products.add(product);
            List<ProductImage> productImages = productImageDAO.listProductImagesByProductIdAndType(product.getId(), "single");
            if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
            product.setSaleCount(orderItemService.getSaleCount(product.getId()));
            product.setReviewCount(reviewService.listReviewsByProductId(product.getId()).size());
        });
        return products;
    }
    
    private void initESDatabase() {
        Page<Product> page = productESDAO.findAll(PageRequest.of(0, 5, Sort.unsorted()));
        if (page.getContent().isEmpty()) {
            List<Product> products = productDAO.listAllProducts();
            for (Product product : products) {
                productESDAO.save(product);
            }
        }
    }
}
