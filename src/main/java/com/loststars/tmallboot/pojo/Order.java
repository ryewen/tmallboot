package com.loststars.tmallboot.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    
    public static final String STATUS_WAIT_PAY = "waitPay";
    
    public static final String STATUS_WAIT_DELIVERY = "waitDelivery";
    
    public static final String STATUS_WAIT_CONFIRM = "waitConfirm";
    
    public static final String STATUS_WAIT_REVIEW = "waitReview";
    
    public static final String STATUS_FINISH = "finish";
    
    public static final String STATUS_DELETE = "delete"; 
    
    private int id;
    
    private String orderCode;
    
    private String address;
    
    private String post;
    
    private String receiver;
    
    private String mobile;
    
    private String userMessage;
    
    private Timestamp createDate;
    
    private Timestamp payDate;
    
    private Timestamp deliveryDate;
    
    private Timestamp confirmDate;
    
    private User user;
    
    private String status;
    
    private String statusDesc;
    
    private float total;
    
    private int totalNumber;
    
    private List<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Timestamp getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Timestamp confirmDate) {
        this.confirmDate = confirmDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        if (statusDesc != null) return statusDesc;
        if (status == null) return null;
        switch (status) {
        case Order.STATUS_WAIT_PAY:
            statusDesc = "待付";
            break;
        case Order.STATUS_WAIT_DELIVERY:
            statusDesc = "待发";
            break;
        case Order.STATUS_WAIT_CONFIRM:
            statusDesc = "待收";
            break;
        case Order.STATUS_WAIT_REVIEW:
            statusDesc = "待评";
            break;
        case Order.STATUS_FINISH:
            statusDesc = "完成";
            break;
        case Order.STATUS_DELETE:
            statusDesc = "删除";
            break;
        default:
            statusDesc = "未知";
        }
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}