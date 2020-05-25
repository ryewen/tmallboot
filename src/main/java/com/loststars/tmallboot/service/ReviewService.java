package com.loststars.tmallboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.loststars.tmallboot.dao.ReviewDAO;
import com.loststars.tmallboot.pojo.Review;

@Service
@CacheConfig(cacheNames = "review")
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;
    
    @Cacheable(key = "#productId")
    public List<Review> listReviewsByProductId(int productId) {
        return reviewDAO.listReviewsByProductId(productId);
    }
}
