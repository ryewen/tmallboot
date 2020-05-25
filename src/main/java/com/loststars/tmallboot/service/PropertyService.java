package com.loststars.tmallboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loststars.tmallboot.dao.ProductPropertyValueDAO;
import com.loststars.tmallboot.dao.PropertyDAO;
import com.loststars.tmallboot.pojo.Property;
import com.loststars.tmallboot.util.PageNavigator;

@Service
@CacheConfig(cacheNames = "property")
public class PropertyService {
    
    @Autowired
    private PropertyDAO propertyDAO;
    
    @Autowired
    private ProductPropertyValueDAO productPropertyValueDAO;

    @Cacheable(key = "#categoryId + '-' + #start")
    public PageNavigator<Property> listCategoryProperties(int categoryId, int start, int size, int navigatePages) {
        PageHelper.startPage(start, size);
        List<Property> properties = propertyDAO.getPropertiesByCategoryId(categoryId);
        PageInfo<Property> pageInfo = new PageInfo<>(properties);
        PageNavigator<Property> page = new PageNavigator<>(pageInfo, properties, navigatePages);
        return page;
    }
    
    @CacheEvict(allEntries = true)
    public void addProperty(Property property) {
        propertyDAO.addProperty(property);
    }
    
    @Cacheable(key = "#id")
    public Property getPropertyById(int id) {
        return propertyDAO.getPropertyById(id);
    }
    
    @CacheEvict(allEntries = true)
    public void updateProperty(Property property) {
        propertyDAO.updateProperty(property);
    }
    
    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteProperty(int id) {
        propertyDAO.deleteProperty(id);
        productPropertyValueDAO.deleteProductPropertyValueByPropertyId(id);
    }
}
