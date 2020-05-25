package com.loststars.tmallboot.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.loststars.tmallboot.pojo.Product;

public interface ProductESDAO extends ElasticsearchRepository<Product,Integer> {

}
