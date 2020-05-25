package com.loststars.tmallboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.loststars.tmallboot.pojo.Role;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String contextPath=session.getServletContext().getContextPath();
        String[] userPages = new String[] {
                "buy",
                "alipay",
                "payed",
                "cart",
                "bought",
                "confirmPay",
                "orderConfirmed",
                "review",
                 
                "forebuyone",
                "forebuy",
                "foreaddCart",
                "forecart",
                "forechangeOrderItem",
                "foredeleteOrderItem",
                "forecreateOrder",
                "forepayed",
                "forebought",
                "foreconfirmPay",
                "foreorderConfirmed",
                "foredeleteOrder",
                "forereview",
                "foredoreview"
        };
        String[] adminPages = new String[] {
                "categories",
                "orders",
                "deliveryOrder",
                "products",
                "productImages",
                "propertyValues",
                "users",
                
                "admin",
                "admin_category_list",
                "admin_category_edit",
                "admin_property_list",
                "admin_property_edit",
                "admin_product_list",
                "admin_product_edit",
                "admin_productImage_list",
                "admin_propertyValue_edit",
                "admin_user_list",
                "admin_order_list"
        };
        String[] requireAuthPages = new String[userPages.length + adminPages.length];
        System.arraycopy(userPages, 0, requireAuthPages, 0, userPages.length);
        System.arraycopy(adminPages, 0, requireAuthPages, userPages.length, adminPages.length);
        
        String uri = httpServletRequest.getRequestURI();
        uri = StringUtils.remove(uri, contextPath+"/");
        String page = uri;
         
        if (begingWith(page, requireAuthPages)){
            Subject subject = SecurityUtils.getSubject();
            if (! subject.isAuthenticated()) {
                httpServletResponse.sendRedirect("login");
                return false;
            } else {
                boolean hasRole = false;
                if (begingWith(page, userPages)) {
                    if (subject.hasRole(Role.USER)) hasRole = true;
                }
                if (begingWith(page, adminPages)) {
                    if (subject.hasRole(Role.ADMIN)) hasRole = true;
                }
                if (! hasRole) {
                    httpServletResponse.sendRedirect("roleError");
                    return false;
                }
            }
        }
        return true;
    }
 
    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true; 
                break;
            }
        }
        return result;
    }
 
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
