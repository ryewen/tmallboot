package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.Category;

public interface CategoryDAO {

    public List<Category> listCategory();
    
    public void addCategory(Category category);
    
    public void deleteCategoryById(int id);
    
    public Category getCategoryById(int id);
    
    public void updateCategory(Category category);
    
    public List<Category> listCategoryWithProduct();
    
    public Category getCategoryAndProductsById(int id);
}
