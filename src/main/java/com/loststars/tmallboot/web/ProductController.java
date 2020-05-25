package com.loststars.tmallboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.service.ProductService;
import com.loststars.tmallboot.util.PageNavigator;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping(value = "/categories/{cid}/products")
    public PageNavigator<Product> listProductByCategoryId(@PathVariable("cid") int categoryId, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        return productService.listProductByCategoryId(categoryId, start, size, 5);
    }
    
    @PostMapping(value = "/products")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }
    
    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        return productService.getProductById(id);
    }
    
    @PutMapping(value = "/products")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }
    
    @DeleteMapping(value = "/products/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
    }
}
