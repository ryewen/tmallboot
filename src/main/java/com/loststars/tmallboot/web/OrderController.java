package com.loststars.tmallboot.web;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loststars.tmallboot.pojo.Order;
import com.loststars.tmallboot.service.OrderService;
import com.loststars.tmallboot.util.PageNavigator;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping(value = "/orders")
    public PageNavigator<Order> listOrders(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        return orderService.listOrders(start, size, 5);
    }
    
    @PutMapping(value = "/deliveryOrder/{id}")
    public void updateDeliveryOrder(@PathVariable("id") int id) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(Order.STATUS_WAIT_CONFIRM);
        order.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
        orderService.updateDeliveryOrder(order);
    }
}
