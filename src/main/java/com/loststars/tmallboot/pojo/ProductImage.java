package com.loststars.tmallboot.pojo;

public class ProductImage {
    
    public static final String TYPE_SINGLE = "single";
    
    public static final String TYPE_DETAIL = "detail";

    private int id;
    
    private String type;
    
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
