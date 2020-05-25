package com.loststars.tmallboot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class CategoryDAOTest {

    @Autowired
    private CategoryDAO categoryDAO;
    
    @Test
    public void addCategoryTest() {
        Category category = new Category();
        category.setName("笔记本电脑");
        categoryDAO.addCategory(category);
        System.out.println(category.getId() + " " + category.getName());
    }
}
