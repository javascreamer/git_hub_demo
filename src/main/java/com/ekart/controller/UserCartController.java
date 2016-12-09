package com.ekart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ekart.model.Customer;
import com.ekart.service.CustomerService;

@Controller
@RequestMapping("/user/cart")
public class UserCartController {
	
@Autowired
private CustomerService customerService;

@RequestMapping
public String getCartItems(@AuthenticationPrincipal User activeUser)
{
	Customer customer=customerService.getCustomerByName(activeUser.getUsername());
	int cartId=customer.getCart().getCartId();
	return "redirect:/user/cart/"+cartId;
}
@RequestMapping("/{cartId}")
public String getNewUrl(@PathVariable (value = "cartId") int cartId, Model model) {
    model.addAttribute("cartId", cartId);
    return "cart";
}
	
}
