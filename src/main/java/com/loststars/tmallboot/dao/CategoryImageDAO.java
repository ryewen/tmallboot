package com.loststars.tmallboot.dao;

import com.loststars.tmallboot.pojo.CategoryImage;

public interface CategoryImageDAO {

    public void addCategoryImage(CategoryImage categoryImage);
    
    public void deleteCategoryImageByCategoryId(int categoryId);
}
