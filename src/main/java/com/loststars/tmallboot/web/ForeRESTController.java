package com.loststars.tmallboot.web;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.loststars.tmallboot.pojo.Category;
import com.loststars.tmallboot.pojo.Order;
import com.loststars.tmallboot.pojo.OrderItem;
import com.loststars.tmallboot.pojo.Product;
import com.loststars.tmallboot.pojo.ProductImage;
import com.loststars.tmallboot.pojo.ProductPropertyValue;
import com.loststars.tmallboot.pojo.Review;
import com.loststars.tmallboot.pojo.Role;
import com.loststars.tmallboot.pojo.User;
import com.loststars.tmallboot.service.CategoryService;
import com.loststars.tmallboot.service.OrderItemService;
import com.loststars.tmallboot.service.OrderService;
import com.loststars.tmallboot.service.ProductImageService;
import com.loststars.tmallboot.service.ProductPropertyValueService;
import com.loststars.tmallboot.service.ProductService;
import com.loststars.tmallboot.service.ReviewService;
import com.loststars.tmallboot.service.UserService;
import com.loststars.tmallboot.util.Result;

@RestController
public class ForeRESTController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductImageService productImageService;
    
    @Autowired
    private ProductPropertyValueService productPropertyValueService;
    
    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/forehome")
    public List<Category> listCategoryWithProduct(HttpSession session) {
        return categoryService.listCategoryWithProduct(8, 5);
    }
    
    @PostMapping(value = "/foreregister")
    public Result register(@RequestBody User user) {
        if (user == null) return new Result(Result.CODE_FAILED, "非法输入", null);
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        if (userService.userExists(user)) return new Result(Result.CODE_FAILED, "用户名已使用", null);
        userService.addUser(user, Role.USER);
        return new Result(Result.CODE_SUCCESS, null, null);
    }
    
    @PostMapping(value = "/forelogin")
    public Result login(@RequestBody User user, HttpSession session) {
        if (user == null) return new Result(Result.CODE_FAILED, "非法输入", null);
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        try {
            subject.login(token);
            User savedUser = userService.getUser(user.getName());
            session.setAttribute("user", savedUser);
            return new Result(Result.CODE_SUCCESS, null, null);
        } catch (AuthenticationException e) {
            return new Result(Result.CODE_FAILED, null, null);
        }
    }
    
    @GetMapping(value = "/foreproduct/{id}")
    public Result getProductAndReview(@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        productImageService.setProductImages(product);
        List<ProductPropertyValue> productPropertyValue = productPropertyValueService.listProductPropertyValuesByProductId(product.getId());
        product.setSaleCount(orderItemService.getSaleCount(product.getId()));
        List<Review> reviews = reviewService.listReviewsByProductId(product.getId());
        product.setReviewCount(reviews.size());
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("pvs", productPropertyValue);
        map.put("reviews", reviews);
        return new Result(Result.CODE_SUCCESS, null, map);
    }
    
    @GetMapping(value = "/forecheckLogin")
    public Result checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return new Result(Result.CODE_FAILED, null, null);
        else
            return new Result(Result.CODE_SUCCESS, null, null);
    }
    
    @GetMapping(value = "/forecategory/{id}")
    public Category getCategory(@PathVariable("id") int id, @RequestParam(value = "sort", defaultValue = "all") String sort) {
        if (sort == null) {
            sort = "all";
        } else {
            if (sort.equals("null")) {
                sort = "all";
            }
        }
        Category category = categoryService.getCategoryAndProducts(id, sort);
        return category;
    }
    
    @PostMapping(value = "/foresearch")
    public List<Product> searchProducts(@RequestParam("keyword") String keyWord) {
        return productService.listProductsByKeyWord(keyWord);
    }
    
    @GetMapping(value = "/forebuyone")
    public Integer buyAndAddProductToCart(@RequestParam("pid") int productId, @RequestParam("num") int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return -1;
        OrderItem orderItem = orderItemService.getOrderItemInCartByUserIdAndProductId(user.getId(), productId);
        boolean noOrderItem = false;
        if (orderItem == null) {
            orderItem = new OrderItem();
            noOrderItem = true;
        }
        orderItem.setUser(user);
        Product product = new Product();
        product.setId(productId);
        orderItem.setProduct(product);
        orderItem.setNumber(orderItem.getNumber() + num);
        if (noOrderItem)
            orderItemService.addOrderItem(orderItem);
        else
            orderItemService.updateOrderItem(orderItem);
        return orderItem.getId();
    }
    
    @GetMapping("/forebuy")
    public Result getBuyOrderItems(@RequestParam("oiid") int[] oiids, HttpSession session) {
        if (oiids == null) return  new Result(Result.CODE_FAILED, null, null);
        if (oiids.length == 0) return  new Result(Result.CODE_FAILED, null, null);
        User user = (User) session.getAttribute("user");
        if (user == null) return new Result(Result.CODE_FAILED, "用户超时", null);
        List<OrderItem> orderItems = new LinkedList<>();
        float total = 0f;
        for (int oiid : oiids) {
            OrderItem orderItem = orderItemService.getOrderItemInCartByIdAndUserId(oiid, user.getId());
            if (orderItem == null) continue;
            Product product = orderItem.getProduct();
            List<ProductImage> productImages = productImageService.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
            if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
            total += product.getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        session.setAttribute("ois", orderItems);
        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return new Result(Result.CODE_SUCCESS, null, map);
    }
    
    @GetMapping(value = "/foreaddCart")
    public Result addProductToCart(@RequestParam("pid") int productId, @RequestParam("num") int num, HttpSession session) {
        buyAndAddProductToCart(productId, num, session);
        return new Result(Result.CODE_SUCCESS, null, null);
    }
    
    @GetMapping(value = "/forecart")
    public List<OrderItem> getOrderItemInCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return null;
        List<OrderItem> orderItems = orderItemService.listOrderItemsInCartByUserId(user.getId());
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            List<ProductImage> productImages = productImageService.listProductImagesByProductIdAndType(product.getId(), ProductImage.TYPE_SINGLE);
            if (productImages.size() > 0) product.setFirstProductImage(productImages.get(0));
        }
        return orderItems;
    }
    
    @GetMapping(value = "/forechangeOrderItem")
    public Result changeOrderItemNumber(@RequestParam("pid") int productId, @RequestParam("num") int number, HttpSession session) {
        OrderItem orderItem = new OrderItem();
        User user = (User) session.getAttribute("user");
        if (user == null) return new Result(Result.CODE_FAILED, "用户超时", null);
        orderItem.setUser(user);
        orderItem.setNumber(number);
        Product product = new Product();
        product.setId(productId);
        orderItem.setProduct(product);
        if (orderItemService.updateOrderItemNumber(orderItem) > 0)
            return new Result(Result.CODE_SUCCESS, null, null);
        else
            return new Result(Result.CODE_FAILED, null, null);
    }
    
    @GetMapping(value = "/foredeleteOrderItem")
    public Result deleteOrderItemInCart(@RequestParam("oiid") int id) {
        if (orderItemService.deleteOrderItemInCart(id) > 0)
            return new Result(Result.CODE_SUCCESS, null, null);
        else
            return new Result(Result.CODE_FAILED, null, null);
    }
    
    @PostMapping(value = "/forecreateOrder")
    public Result createOrder(@RequestParam("oiid") int[] oiids, @RequestBody Order order, HttpSession session) {
        if (oiids == null) return null;
        if (oiids.length == 0) return null;
        User user = (User) session.getAttribute("user");
        if (user == null) return null;
        order.setUser(user);
        order.setCreateDate(new Timestamp(System.currentTimeMillis()));
        order.setStatus(Order.STATUS_WAIT_PAY);
        Timestamp time = order.getCreateDate();
        Random random = new Random();
        String orderCode = String.format("%s%s%s%s", random.nextInt(10), random.nextInt(10), random.nextInt(10), random.nextInt(10));
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        orderCode = sdf.format(time) + orderCode;
        order.setOrderCode(orderCode);
        float total = 0f;
        try {
            total = orderService.createOrder(oiids, order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Result.CODE_FAILED, null, null);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return new Result(Result.CODE_SUCCESS, null, map);
    }
    
    @GetMapping(value = "/forepayed")
    public Order getPayedOrder(@RequestParam("oid") int id) {
        Order order = orderService.getOrderById(id);
        order.setStatus(Order.STATUS_WAIT_DELIVERY);
        order.setPayDate(new Timestamp(System.currentTimeMillis()));
        orderService.updatePayedOrder(order);
        return order;
    }
    
    @GetMapping(value = "/forebought")
    public List<Order> listBoughtOrders(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return null;
        List<Order> orders = orderService.listBoughtOrderByUserId(user.getId());
        for (Order order : orders) {
            int totalNumber = 0;
            float total = 0f;
            for (OrderItem orderItem : order.getOrderItems()) {
                totalNumber += orderItem.getNumber();
                Product product = orderItem.getProduct();
                productImageService.setProductImages(product);
                total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            }
            order.setTotalNumber(totalNumber);
            order.setTotal(total);
        }
        return orders;
    }
    
    @GetMapping(value = "/foreconfirmPay")
    public Order confirmPayOrder(@RequestParam("oid") int id) {
        Order order = orderService.getOrderById(id);
        int totalNumber = 0;
        float total = 0f;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalNumber += orderItem.getNumber();
            Product product = orderItem.getProduct();
            productImageService.setProductImages(product);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        order.setTotalNumber(totalNumber);
        order.setTotal(total);
        return order;
    }
    
    @GetMapping(value = "/foreorderConfirmed")
    public void confirmOrder(@RequestParam("oid") int id) {
        Order order = new Order();
        order.setStatus(Order.STATUS_WAIT_REVIEW);
        order.setConfirmDate(new Timestamp(System.currentTimeMillis()));
        order.setId(id);
        orderService.updateConfirmedOrder(order);
    }
    
    @PutMapping(value = "/foredeleteOrder")
    public Result deleteOrder(@RequestParam("oid") int id) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(Order.STATUS_DELETE);
        if (orderService.deleteOrder(order) > 0)
            return new Result(Result.CODE_SUCCESS, null, null);
        else
            return new Result(Result.CODE_FAILED, null, null);
    }
    
    @GetMapping(value = "/forereview")
    public Map<String, Object> listReviews(@RequestParam("oid") int orderId) {
        Order order = orderService.getOrderById(orderId);
        Product product = order.getOrderItems().get(0).getProduct();
        productImageService.setProductImages(product);
        product.setSaleCount(orderItemService.getSaleCount(product.getId()));
        List<Review> reviews = reviewService.listReviewsByProductId(product.getId());
        product.setReviewCount(reviews.size());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("o", order);
        map.put("p", product);
        map.put("reviews", reviews);
        return map;
    }
    
    @PostMapping(value = "/foredoreview")
    public Result review(@RequestParam("oid") int orderId, @RequestParam("pid") int productId, @RequestParam("content") String content, HttpSession session) throws Exception {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(Order.STATUS_FINISH);
        Review review = new Review();
        User user = (User) session.getAttribute("user");
        if (user == null) return new Result(Result.CODE_FAILED, null, null);
        review.setUser(user);
        Product product = new Product();
        product.setId(productId);
        review.setProduct(product);
        review.setContent(content);
        review.setCreateDate(new Timestamp(System.currentTimeMillis()));
        orderService.updateReviewOrder(order, review);
        return new Result(Result.CODE_SUCCESS, null, null);
    }
}
