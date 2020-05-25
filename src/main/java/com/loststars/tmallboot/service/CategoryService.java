package com.loststars.tmallboot.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loststars.tmallboot.comparator.ProductAllComparator;
import com.loststars.tmallboot.comparator.ProductDateComparator;
import com.loststars.tmallboot.comparator.ProductPriceComparator;
import com.loststars.tmallboot.comparator.ProductReviewComparator;
import com.loststars.tmallboot.comparator.ProductSaleCountComparator;
import com.loststars.tmallboot.dao.CategoryDAO;
import com.loststars.tmallboot.dao.CategoryImageDAO;
import com.loststars.tmallboot.dao.ProductImageDAO;
import com.loststars.tmallboot.pojo.Category;
import com.loststars.tmallboot.pojo.CategoryImage;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.util.ImageUtil;
import com.loststars.tmallboot.util.PageNavigator;

@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;
    
    @Autowired
    private CategoryImageDAO categoryImageDAO;
    
    @Autowired
    private ProductImageDAO productImageDAO;
    
    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Cacheable(key = "'categories-all'")
    public List<Category> listCategory() {
        return categoryDAO.listCategory();
    }
    
    @Cacheable(key = "'categories-page-' + #p0")
    public PageNavigator<Category> listCategory(int start, int size, int navigatePages) {
        PageHelper.startPage(start, size);
        List<Category> categories = categoryDAO.listCategory();
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        PageNavigator<Category> page = new PageNavigator<>(pageInfo, categories, navigatePages);
        return page;
    }
    
    @Transactional
    @CacheEvict(allEntries = true)
    public void addCategory(Category category, MultipartFile image, HttpServletRequest request) throws IllegalStateException, IOException {
         categoryDAO.addCategory(category);
         CategoryImage categoryImage = new CategoryImage();
         categoryImage.setCategory(category);
         categoryImageDAO.addCategoryImage(categoryImage);
         ImageUtil.saveImage(image, request.getServletContext().getRealPath("img/category"), categoryImage.getId() + ".jpg");
    }
    
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteCategory(int id, HttpServletRequest request) {
        Category category = categoryDAO.getCategoryById(id);
        categoryImageDAO.deleteCategoryImageByCategoryId(id);
        categoryDAO.deleteCategoryById(id);
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getCategoryImage().getId() + ".jpg");
        file.delete();
    }
    
    @Cacheable(key = "'categories-one'+ #p0")
    public Category getCategory(int id) {
        return categoryDAO.getCategoryById(id);
    }
    
    @Transactional
    @CacheEvict(allEntries = true)
    public void updateCategory(Category category, MultipartFile image, HttpServletRequest request) throws IllegalStateException, IOException {
        categoryDAO.updateCategory(category);
        category = categoryDAO.getCategoryById(category.getId());
        if (image != null) ImageUtil.saveImage(image, request.getServletContext().getRealPath("img/category"), category.getCategoryImage().getId() + ".jpg");
    }
    
    @Cacheable(key = "'categories-row'")
    public List<Category> listCategoryWithProduct(int rowSize, int productSize) {
        List<Category> categories = categoryDAO.listCategoryWithProduct();
        for (Category category : categories) {
            List<List<Product>> productsByRow = new LinkedList<>();
            List<Product> tempProducts = new LinkedList<>();
            List<Product> products = category.getProducts();
            int i = 0;
            for (Product product : products) {
                if (i < productSize) {
                    List<ProductImage> productImages = productImageDAO.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
                    if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
                }
                tempProducts.add(product);
                if (tempProducts.size() == rowSize) {
                    productsByRow.add(tempProducts);
                    tempProducts = new LinkedList<>();
                }
                ++ i;
            }
            if (tempProducts.size() > 0) productsByRow.add(tempProducts);
            category.setProductsByRow(productsByRow);
            products = products.subList(0, Math.min(productSize, products.size()));
            products = new ArrayList<>(products);
            category.setProducts(products);
        }
        return categories;
    }
    
    @Cacheable(key = "'categories-one-sort-'+ #p0 + '-' + #p1")
    public Category getCategoryAndProducts(int id, String sort) {
        Category category = categoryDAO.getCategoryAndProductsById(id);
        for (Product product : category.getProducts()) {
            List<ProductImage> productImages = productImageDAO.listProductImagesByProductIdAndType(product.getId(), "single");
            if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
            product.setSaleCount(orderItemService.getSaleCount(product.getId()));
            product.setReviewCount(reviewService.listReviewsByProductId(product.getId()).size());
        }
        Comparator<Product> comparator = null;
        if (sort == null) {
            comparator = new ProductAllComparator();
        } else {
            switch (sort) {
            case "all":
                comparator = new ProductAllComparator();
                break;
            case "date":
                comparator = new ProductDateComparator();
                break;
            case "price":
                comparator = new ProductPriceComparator();
                break;
            case "review":
                comparator = new ProductReviewComparator();
                break;
            case "saleCount":
                comparator = new ProductSaleCountComparator();
                break;
            default:
                comparator = new ProductAllComparator();
            }
        }
        Collections.sort(category.getProducts(), comparator);
        return category;
    }
}
