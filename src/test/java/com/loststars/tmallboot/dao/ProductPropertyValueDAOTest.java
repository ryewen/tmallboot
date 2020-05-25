package com.loststars.tmallboot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.ProductPropertyValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class ProductPropertyValueDAOTest {
    
    @Autowired
    private ProductPropertyValueDAO productPropertyValueDAO;
    
    @Test
    public void addCategoryTest() {
        ProductPropertyValue value = productPropertyValueDAO.getProductPropertyValueByProductIdAndPropertyId(958, 264);
        System.out.println(value);
        if (value != null) System.out.println(value.getValue());
    }
}