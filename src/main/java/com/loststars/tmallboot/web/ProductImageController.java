package com.loststars.tmallboot.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.service.ProductImageService;

@RestController
public class ProductImageController {
    
    @Autowired
    private ProductImageService productImageService;

    @GetMapping(value = "/products/{pid}/productImages")
    public List<ProductImage> listProductImagesByProductIdAndType(@PathVariable("pid") int productId, @RequestParam("type") String type) {
        if (type.equals(ProductImage.TYPE_SINGLE) || type.equals(ProductImage.TYPE_DETAIL))
            return productImageService.listProductImagesByProductIdAndType(productId, type);
        else
            return null;
    }
    
    @PostMapping(value = "/productImages")
    public void addProductImage(@RequestParam("pid") int productId, @RequestParam("image") MultipartFile image, @RequestParam("type") String type, HttpServletRequest request) throws IllegalStateException, IOException {
        if (type.equals(ProductImage.TYPE_SINGLE) || type.equals(ProductImage.TYPE_DETAIL)) {
            ProductImage productImage = new ProductImage();
            Product product = new Product();
            product.setId(productId);
            productImage.setProduct(product);
            productImage.setType(type);
            productImageService.addProductImage(productImage, image, request);
        }
    }
    
    @DeleteMapping(value = "/productImages/{id}")
    public void deleteProductImage(@PathVariable("id") int id, HttpServletRequest request) {
        productImageService.deleteProductImage(id, request);
    }
}
