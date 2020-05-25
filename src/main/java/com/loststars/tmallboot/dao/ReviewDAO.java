package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.Review;

public interface ReviewDAO {

    public List<Review> listReviewsByProductId(int productId);
    
    public void addReview(Review review);
}
