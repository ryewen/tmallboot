package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.Order;

public interface OrderDAO {

    public List<Order> listOrders();
    
    public List<Integer> listOrderIds();
    
    public Order getOrderById(int id);
    
    public void updateDeliveryOrder(Order order);
    
    public void addOrder(Order order);
    
    public void updatePayedOrder(Order order);
    
    public List<Order> listBoughtOrdersByUserId(int userId);
    
    public void updateConfirmedOrder(Order order);
    
    public int deleteOrder(Order order);
    
    public int updateReviewOrder(Order order);
}
