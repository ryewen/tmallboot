package com.loststars.tmallboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loststars.tmallboot.pojo.ProductPropertyValue;

public interface ProductPropertyValueDAO {

    public List<ProductPropertyValue> listProductPropertyValuesByProductId(int productId);
    
    public ProductPropertyValue getProductPropertyValueByProductIdAndPropertyId(@Param("productId") int productId, @Param("propertyId") int propertyId);

    public void addProductPropertyValue(ProductPropertyValue productPropertyValue);
    
    public void updateProductPropertyValue(ProductPropertyValue productPropertyValue);
    
    public void deleteProductPropertyValueByPropertyId(int propertyId);
}
