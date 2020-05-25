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

import com.loststars.tmallboot.pojo.Property;
import com.loststars.tmallboot.service.PropertyService;
import com.loststars.tmallboot.util.PageNavigator;

@RestController
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;
    
    @GetMapping(value = "/categories/{cid}/properties")
    public PageNavigator<Property> listCategoryProperties(@PathVariable("cid") int categoryId, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        return propertyService.listCategoryProperties(categoryId, start, size, 5);
    }
    
    @PostMapping(value = "/properties")
    public void add(@RequestBody Property property) {
        propertyService.addProperty(property);
    }
    
    @GetMapping(value = "/properties/{id}")
    public Property getProperty(@PathVariable("id") int id) {
        return propertyService.getPropertyById(id);
    }
    
    @PutMapping(value = "/properties")
    public void updateProperty(@RequestBody Property property) {
        propertyService.updateProperty(property);
    }
    
    @DeleteMapping(value = "/properties/{id}")
    public void deleteProperty(@PathVariable("id") int id) {
        propertyService.deleteProperty(id);
    }
}
