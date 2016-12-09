package com.ekart.controller;

import com.ekart.model.Cart;
import com.ekart.model.Customer;
import com.ekart.model.UserOrder;
import com.ekart.service.CartService;
import com.ekart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * This controller is used to handle user order
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;
    /*
     * createOrder method is used to insert user order into the database.
     */
    @RequestMapping("/order/{cartId}")
    public String createOrder(@PathVariable("cartId") int cartId) {
    	UserOrder userOrder = new UserOrder();
        Cart cart=cartService.getCartById(cartId);
        System.out.println("cart:"+cart.getCartId());
        userOrder.setCart(cart);

        Customer usersDetail = cart.getCustomer();
        userOrder.setCustomer(usersDetail);
        orderService.addOrder(userOrder);

        return "redirect:/checkout?cartId="+cartId;
    }
}
