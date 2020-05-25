package com.loststars.tmallboot.web;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForePageController {

    @GetMapping(value = "/")
    public String index() {
        return "redirect:home";
    }
    
    @GetMapping(value = "/home")
    public String home() {
        return "fore/home";
    }
    
    @GetMapping(value = "/register")
    public String register() {
        return "fore/register";
    }
    
    @GetMapping(value = "/registerSuccess")
    public String registerSuccess() {
        return "fore/registerSuccess";
    }
    
    @GetMapping(value = "/login")
    public String login() {
        return "fore/login";
    }
    
    @GetMapping(value = "/forelogout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/home";
    }
    
    @GetMapping(value = "/product")
    public String product() {
        return "fore/product";
    }
    
    @GetMapping(value = "/category")
    public String category() {
        return "fore/category";
    }
    
    @GetMapping(value = "/search")
    public String search() {
        return "fore/search";
    }
    
    @GetMapping(value = "/buy")
    public String buy() {
        return "fore/buy";
    }
    
    @GetMapping(value = "/cart")
    public String cart() {
        return "fore/cart";
    }
    
    @GetMapping(value = "/alipay")
    public String alipay() {
        return "fore/alipay";
    }
    
    @GetMapping(value = "/payed")
    public String payed() {
        return "fore/payed";
    }
    
    @GetMapping(value = "/bought")
    public String bought() {
        return "fore/bought";
    }
    
    @GetMapping(value = "/confirmPay")
    public String confirmPay() {
        return "fore/confirmPay";
    }
    
    @GetMapping(value = "/orderConfirmed")
    public String orderConfirmed() {
        return "fore/orderConfirmed";
    }
    
    @GetMapping(value = "/review")
    public String review() {
        return "fore/review";
    }
    
    @GetMapping(value = "/roleError")
    public String roleError() {
        return "fore/roleError";
    }
}
