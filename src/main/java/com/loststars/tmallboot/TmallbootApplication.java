package com.loststars.tmallboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.loststars.tmallboot.dao")
@EnableTransactionManagement
@EnableElasticsearchRepositories(basePackages = "com.loststars.tmallboot.es")
public class TmallbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmallbootApplication.class, args);
    }
}