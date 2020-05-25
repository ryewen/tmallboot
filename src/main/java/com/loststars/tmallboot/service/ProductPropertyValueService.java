package com.loststars.tmallboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.loststars.tmallboot.dao.ProductPropertyValueDAO;
import com.loststars.tmallboot.dao.PropertyDAO;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductPropertyValue;
import com.loststars.tmallboot.pojo.Property;

@Service
@CacheConfig(cacheNames = "propertyvalue")
public class ProductPropertyValueService {

    @Autowired
    private ProductPropertyValueDAO productPropertyValueDAO;
    
    @Autowired
    private PropertyDAO propertyDAO;
    
    @Cacheable(key = "#productId")
    public List<ProductPropertyValue> listProductPropertyValuesByProductId(int productId) {
        List<Property> properties = propertyDAO.listPropertiesByProductId(productId);
        for (Property property : properties) {
            ProductPropertyValue value = productPropertyValueDAO.getProductPropertyValueByProductIdAndPropertyId(productId, property.getId());
            if (value == null) {
                value = new ProductPropertyValue();
                Product product = new Product();
                product.setId(productId);
                value.setProduct(product);
                value.setProperty(property);
                value.setValue("");
                productPropertyValueDAO.addProductPropertyValue(value);
            }
        }
        return productPropertyValueDAO.listProductPropertyValuesByProductId(productId);
    }
    
    @CacheEvict(allEntries = true)
    public void updateProductPropertyValue(ProductPropertyValue productPropertyValue) {
        productPropertyValueDAO.updateProductPropertyValue(productPropertyValue);
    }
}
