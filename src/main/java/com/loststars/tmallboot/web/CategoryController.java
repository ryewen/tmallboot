package com.loststars.tmallboot.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loststars.tmallboot.pojo.Category;
import com.loststars.tmallboot.service.CategoryService;
import com.loststars.tmallboot.util.PageNavigator;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping(value = "/categories")
    public PageNavigator<Category> list(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageNavigator<Category> page = categoryService.listCategory(start, size, 5);
        return page;
    }
    
    @PostMapping(value = "/categories")
    public Category add(@RequestParam(value = "name") String name, @RequestParam(value = "image") MultipartFile image, HttpServletRequest request) throws IllegalStateException, IOException {
        Category category = new Category();
        category.setName(name);
        categoryService.addCategory(category, image, request);
        return category;
    }
    
    @DeleteMapping(value = "/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        categoryService.deleteCategory(id, request);
        return null;
    }
    
    @GetMapping(value = "/categories/{id}")
    public Category get(@PathVariable("id") int id) {
        return categoryService.getCategory(id);
    }
    
    @PutMapping(value = "/categories/{id}")
    public void update(@PathVariable("id") int id, @RequestParam(value = "name") String name,  @RequestParam(value = "image") MultipartFile image, HttpServletRequest request) throws IllegalStateException, IOException {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryService.updateCategory(category, image, request);
    }
}
