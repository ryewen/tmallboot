package com.loststars.tmallboot.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loststars.tmallboot.dao.OrderDAO;
import com.loststars.tmallboot.dao.OrderItemDAO;
import com.loststars.tmallboot.dao.ProductDAO;
import com.loststars.tmallboot.dao.ProductImageDAO;
import com.loststars.tmallboot.dao.ReviewDAO;
import com.loststars.tmallboot.pojo.Order;
import com.loststars.tmallboot.pojo.OrderItem;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.pojo.Review;
import com.loststars.tmallboot.pojo.User;
import com.loststars.tmallboot.util.PageNavigator;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private ProductImageDAO productImageDAO;
    
    @Autowired
    private OrderItemDAO orderItemDAO;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ReviewDAO reviewDAO;
    
    public PageNavigator<Order> listOrders(int start, int size, int navigatePages) {
        PageHelper.startPage(start, size);
        List<Integer> orderIds = orderDAO.listOrderIds();
        PageInfo<Integer> pageInfo = new PageInfo<>(orderIds);
        List<Order> orders = new LinkedList<>();
        for (Integer id : orderIds) {
            Order order = orderDAO.getOrderById(id);
            float total = 0;
            int totalNumber = 0;
            for (OrderItem orderItem : order.getOrderItems()) {
                totalNumber += orderItem.getNumber();
                Product product = orderItem.getProduct();
                total += orderItem.getNumber() * product.getPromotePrice();
                List<ProductImage> productImages = productImageDAO.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
                if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
            }
            order.setTotal(total);
            order.setTotalNumber(totalNumber);
            orders.add(order);
        }
        PageNavigator<Order> page = new PageNavigator<>(pageInfo, orders, navigatePages);
        return page;
    }
    
    public void updateDeliveryOrder(Order order) {
        orderDAO.updateDeliveryOrder(order);
    }
    
    @Transactional(rollbackForClassName="Exception")
    public float createOrder(int[] oiids, Order order) throws Exception {
        User user = order.getUser();
        orderDAO.addOrder(order);
        float total = 0f;
        List<OrderItem> orderItems = orderItemDAO.listOrderItemsInCartByIdsLock(oiids);
        if (orderItems.size() != oiids.length) throw new Exception();
        for (OrderItem orderItem : orderItems) {
            if (orderItem == null) throw new Exception();
            if (orderItem.getUser().getId() != user.getId()) throw new Exception();
            int number = orderItem.getNumber();
            Product product = orderItem.getProduct();
            if (productDAO.updateStock(product.getId(), number) == 0) throw new Exception();
            orderItem.setOrder(order);
            if (orderItemDAO.updateOrderItemOrderId(orderItem) == 0) throw new Exception();
            total += number * product.getPromotePrice();
        }
        return total;
    }
    
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }
    
    public void updatePayedOrder(Order order) {
        orderDAO.updatePayedOrder(order);
    }
    
    public List<Order> listBoughtOrderByUserId(int userId) {
        return orderDAO.listBoughtOrdersByUserId(userId);
    }
    
    public void updateConfirmedOrder(Order order) {
        orderDAO.updateConfirmedOrder(order);
    }
    
    public int deleteOrder(Order order) {
        return orderDAO.deleteOrder(order);
    }
    
    @Transactional(rollbackForClassName="Exception")
    @CacheEvict(cacheNames = "review", key = "#review.product.id")
    public void updateReviewOrder(Order order, Review review) throws Exception {
        if (orderDAO.updateReviewOrder(order) == 0) throw new Exception();
        reviewDAO.addReview(review);
    }
}
