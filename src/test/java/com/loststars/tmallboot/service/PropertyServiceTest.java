package com.loststars.tmallboot.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.Property;
import com.loststars.tmallboot.util.PageNavigator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private A a;
    
    @Test
    public void pageTest() {
        PageNavigator<Property> page = propertyService.listCategoryProperties(60, 2, 3, 5);
        List<Property> properties = page.getContent();
        System.out.println("Size: " + properties.size());
        for (Property property : properties) { 
            System.out.println(property.getId() + " " + property.getName());
        }
        System.out.println(Arrays.toString(page.getNavigatepageNums()));
    }
    
    @Test
    public void aspectTest() {
        a.printA();
        a.printB();
    }
}
