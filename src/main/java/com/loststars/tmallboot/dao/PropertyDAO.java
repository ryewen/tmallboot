package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.Property;

public interface PropertyDAO {

    public List<Property> getPropertiesByCategoryId(int categoryId);
    
    public void addProperty(Property property);
    
    public Property getPropertyById(int id);
    
    public void updateProperty(Property property);
    
    public void deleteProperty(int id);
    
    public List<Property> listPropertiesByProductId(int productId);
}
