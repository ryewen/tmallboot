package com.loststars.tmallboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loststars.tmallboot.pojo.ProductImage;

public interface ProductImageDAO {

    public List<ProductImage> listProductImagesByProductIdAndType(@Param("productId") int productId, @Param("type") String type);
    
    public void addProductImage(ProductImage productImage);
    
    public void deleteProductImage(int id);
}
