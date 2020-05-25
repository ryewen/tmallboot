package com.loststars.tmallboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.loststars.tmallboot.dao.ProductImageDAO;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.util.ImageUtil;
import com.loststars.tmallboot.util.SpringContext;

@Service
@CacheConfig(cacheNames = "productimage")
public class ProductImageService {

    @Autowired
    private ProductImageDAO productImageDAO;
    
    @Cacheable(key = "#productId + '-' + #type")
    public List<ProductImage> listProductImagesByProductIdAndType(int productId, String type) {
        return productImageDAO.listProductImagesByProductIdAndType(productId, type);
    }
    
    public void setProductImages(Product product) {
        ProductImageService service = SpringContext.getContext().getBean(ProductImageService.class);
        List<ProductImage> productSingleImages = service.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
        product.setProductSingleImages(productSingleImages);
        if (productSingleImages.size() > 0) product.setFirstProductImage(productSingleImages.get(0));
        product.setProductDetailImages(service.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_DETAIL));
    }
    
    @Transactional
    @CacheEvict(key = "#productImage.product.id + '-' + #productImage.type")
    public void addProductImage(ProductImage productImage, MultipartFile image, HttpServletRequest request) throws IllegalStateException, IOException {
        productImageDAO.addProductImage(productImage);
        String dirPath = "img/";
        if (productImage.getType().equals(ProductImage.TYPE_SINGLE)) {
            dirPath += "productSingle";
        } else {
            dirPath += "productDetail";
        }
        dirPath = request.getServletContext().getRealPath(dirPath);
        String imgName = productImage.getId() + ".jpg";
        ImageUtil.saveImage(image, dirPath, imgName);
        if (productImage.getType().equals(ProductImage.TYPE_SINGLE)) {
            String from = Paths.get(request.getServletContext().getRealPath("img/productSingle"), imgName).toString();
            String small = Paths.get(request.getServletContext().getRealPath("img/productSingle_small"), imgName).toString();
            ImageUtil.resizeImage(from, small, 56, 56);
            String middle = Paths.get(request.getServletContext().getRealPath("img/productSingle_middle"), imgName).toString();
            ImageUtil.resizeImage(from, middle, 217, 190);
        }
    }
    
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteProductImage(int id, HttpServletRequest request) {
        productImageDAO.deleteProductImage(id);
        String imgName = id + ".jpg";
        String dirPath = request.getServletContext().getRealPath("img/productSingle");
        File file = new File(dirPath, imgName);
        if (file.exists()) file.delete();
        dirPath = request.getServletContext().getRealPath("img/productDetail");
        file = new File(dirPath, imgName);
        if (file.exists()) file.delete();
        dirPath = request.getServletContext().getRealPath("img/productSingle_small");
        file = new File(dirPath, imgName);
        if (file.exists()) file.delete();
        dirPath = request.getServletContext().getRealPath("img/productSingle_middle");
        file = new File(dirPath, imgName);
        if (file.exists()) file.delete();
    }
}
