package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.Product;

public interface ProductDAO {

    public List<Product> listProductByCategoryId(int categoryId);
    
    public void addProduct(Product product);
    
    public Product getProductById(int id);
    
    public void updateProduct(Product product);
    
    public void deleteProduct(int id);
    
    public List<Product> listProductsByKeyWord(String keyWord);
    
    public int updateStock(int id, int count);
    
    public List<Product> listAllProducts();
}
