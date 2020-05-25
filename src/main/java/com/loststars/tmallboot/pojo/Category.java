package com.loststars.tmallboot.pojo;

import java.util.List;

public class Category {

    private int id;
    
    private String name;
    
    private CategoryImage categoryImage;
    
    private List<Property> properties;
    
    private List<Product> products;
    
    private List<List<Product>> productsByRow;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public CategoryImage getCategoryImage() {
        return categoryImage;
    }
    
    public void setCategoryImage(CategoryImage categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
