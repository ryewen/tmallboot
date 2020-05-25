package com.loststars.tmallboot.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.page.PageMethod;
import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.Order;
import com.loststars.tmallboot.service.OrderService;
import com.loststars.tmallboot.util.PageNavigator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class OrderDAOTest {

    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void list() {
        PageNavigator<Order> page = orderService.listOrders(1, 20, 5);
        for (Order order : page.getContent()) {
            //System.out.println(order.getId());
        }
    }
}
