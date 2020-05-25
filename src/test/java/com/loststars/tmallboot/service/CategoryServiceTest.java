package com.loststars.tmallboot.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.Category;
import com.loststars.tmallboot.util.PageNavigator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    
    @Test
    public void listTest() {
        List<Category> categories = categoryService.listCategory();
        System.out.println("Size: " + categories.size());
        for (Category category : categories) {
            System.out.println(category.getId() + " " + category.getName() + " " + category.getCategoryImage().getId());
        }
    }
    
    @Test
    public void pageTest() {
        PageNavigator<Category> page = categoryService.listCategory(5, 5, 5);
        System.out.println(page.getTotalPages() + " " + page.getTotalElements());
        List<Category> categories = page.getContent();
        System.out.println("Size: " + categories.size());
        for (Category category : categories) {
            System.out.println(category.getId() + " " + category.getName() + " " + category.getCategoryImage().getId());
        }
        System.out.println(Arrays.toString(page.getNavigatepageNums()));
    }
}
