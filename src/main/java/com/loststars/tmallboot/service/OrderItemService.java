package com.loststars.tmallboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loststars.tmallboot.dao.OrderItemDAO;
import com.loststars.tmallboot.pojo.OrderItem;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDAO orderItemDAO;
    
    public int getSaleCount(int productId) {
        List<OrderItem> orderItems = orderItemDAO.listOrderItemsByProductId(productId);
        int saleCount = 0;
        for (OrderItem orderItem : orderItems) {
            saleCount += orderItem.getNumber();
        }
        return saleCount;
    }
    
    public OrderItem getOrderItemInCartByUserIdAndProductId(int userId, int productId) {
        return orderItemDAO.getOrderItemInCartByUserIdAndProductId(userId, productId);
    }
    
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }
    
    public int updateOrderItem(OrderItem orderItem) {
        return orderItemDAO.updateOrderItem(orderItem);
    }
    
    public OrderItem getOrderItemInCartByIdAndUserId(int id, int userId) {
        return orderItemDAO.getOrderItemInCartByIdAndUserId(id, userId);
    }
    
    public List<OrderItem> listOrderItemsInCartByUserId(int userId) {
        return orderItemDAO.listOrderItemsInCartByUserId(userId);
    }
    
    public int getProductCountInCartByUserId(int userId) {
        return orderItemDAO.getProductCountInCartByUserId(userId);
    }
    
    public int updateOrderItemNumber(OrderItem orderItem) {
        return orderItemDAO.updateOrderItemNumber(orderItem);
    }
    
    public int deleteOrderItemInCart(int id) {
        return orderItemDAO.deleteOrderItemInCart(id);
    }
}
