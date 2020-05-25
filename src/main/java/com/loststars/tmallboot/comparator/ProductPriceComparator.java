package com.loststars.tmallboot.comparator;

import java.util.Comparator;

import com.loststars.tmallboot.pojo.Product;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o2.getPromotePrice() - o1.getPromotePrice());
    }
}
