package com.loststars.tmallboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loststars.tmallboot.pojo.ProductPropertyValue;
import com.loststars.tmallboot.service.ProductPropertyValueService;

@RestController
public class ProductPropertyValueController {
    
    @Autowired
    private ProductPropertyValueService productPropertyValueService;

    @GetMapping(value = "/products/{pid}/propertyValues")
    public List<ProductPropertyValue> listProductPropertyValuesByProductId(@PathVariable("pid") int productId) {
        return productPropertyValueService.listProductPropertyValuesByProductId(productId);
    }
    
    @PutMapping(value = "/propertyValues")
    public ProductPropertyValue updateProductPropertyValue(@RequestBody ProductPropertyValue productPropertyValue) {
        productPropertyValueService.updateProductPropertyValue(productPropertyValue);
        return productPropertyValue;
    }
}
