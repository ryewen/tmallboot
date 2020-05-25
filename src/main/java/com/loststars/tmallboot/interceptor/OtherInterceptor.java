package com.loststars.tmallboot.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.loststars.tmallboot.dao.CategoryDAO;
import com.loststars.tmallboot.dao.OrderItemDAO;
import com.loststars.tmallboot.pojo.Category;
import com.loststars.tmallboot.pojo.User;

public class OtherInterceptor implements HandlerInterceptor {
    
    @Autowired
    private OrderItemDAO orderItemDAO;
    
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int productCountInCart = 0;
        if (user != null) {
            productCountInCart = orderItemDAO.getProductCountInCartByUserId(user.getId());
        }
        List<Category> categories = categoryDAO.listCategory();
        session.setAttribute("cartTotalItemNumber", productCountInCart);
        request.getServletContext().setAttribute("categories_below_search", categories);
    }
}
