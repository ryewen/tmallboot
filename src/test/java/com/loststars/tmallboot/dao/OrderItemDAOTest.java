package com.loststars.tmallboot.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loststars.tmallboot.TmallbootApplication;
import com.loststars.tmallboot.pojo.OrderItem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmallbootApplication.class)
public class OrderItemDAOTest {

    @Autowired
    private OrderItemDAO orderItemDAO;
    
    @Test
    public void getOrderItemTest() {
        OrderItem orderItem = orderItemDAO.getOrderItemInCartByUserIdAndProductId(5, 958);
        System.out.println(orderItem);
    }
}
