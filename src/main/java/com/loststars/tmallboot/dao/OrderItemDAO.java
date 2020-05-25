package com.loststars.tmallboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loststars.tmallboot.pojo.OrderItem;

public interface OrderItemDAO {

    public List<OrderItem> listOrderItemsByProductId(int productId);
    
    public OrderItem getOrderItemInCartByUserIdAndProductId(@Param("userId") int userId, @Param("productId") int productId);
    
    public void addOrderItem(OrderItem orderItem);
    
    public int updateOrderItem(OrderItem orderItem);
    
    public OrderItem getOrderItemInCartByIdAndUserId(@Param("id") int id, @Param("userId") int userId);
    
    public List<OrderItem> listOrderItemsInCartByUserId(int userId);
    
    public int getProductCountInCartByUserId(int userId);
    
    public int updateOrderItemNumber(OrderItem orderItem);
    
    public int deleteOrderItemInCart(int id);
    
    public List<OrderItem> listOrderItemsInCartByIdsLock(int[] ids);
    
    public int updateOrderItemOrderId(OrderItem orderItem);
}
