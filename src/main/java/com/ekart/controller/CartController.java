package com.ekart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ekart.model.Cart;
import com.ekart.model.CartItem;
import com.ekart.model.Customer;
import com.ekart.model.Item;
import com.ekart.service.CartItemService;
import com.ekart.service.CartService;
import com.ekart.service.CustomerService;
import com.ekart.service.ItemService;

@Controller
@RequestMapping("/usercart/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	@Autowired
	CustomerService customerService;
	@Autowired
	ItemService itemService;
	@Autowired
	CartItemService cartItemService;
	
@RequestMapping(value = "/addItem/{itemId}", method = RequestMethod.PUT)
 @ResponseStatus(value = HttpStatus.NO_CONTENT)
public void addItem(@PathVariable(value="itemId") int itemId,@AuthenticationPrincipal org.springframework.security.core.userdetails.User activeUser)
{
	System.out.println("I am in addItem method");
	System.out.println("Item id is:"+itemId);
	System.out.println("User:"+activeUser.getUsername());
	Customer customer=customerService.getCustomerByName(activeUser.getUsername());
	Cart cart=customer.getCart();
	System.out.println("customer's cart id:"+cart.getCartId());
	Item item=itemService.getItembyid(itemId);
	System.out.println("Itemdetails:"+item.getItemName());
	List<CartItem> cartItems=cart.getCartItems();
	for(int i=0;i<cartItems.size();i++)
	{
		if(item.getItemId()==cartItems.get(0).getItem().getItemId())
		{
			System.out.println("Inside for loop");
			CartItem  cartItem=cartItems.get(i);
			cartItem.setQuantity(cartItem.getQuantity()+1);
		    cartItem.setTotalPrice(item.getItemPrice()*cartItem.getQuantity());
		    cartItemService.addCartItem(cartItem);
		    return;
		}
	}
	CartItem cartItem=new CartItem();
	cartItem.setItem(item);
	cartItem.setQuantity(1);
	cartItem.setTotalPrice(item.getItemPrice()*cartItem.getQuantity());
	cartItem.setCart(cart);
	cartItemService.addCartItem(cartItem);
	System.out.println("Item added bhayaaaa");
}
@RequestMapping("/refreshCart/{cartId}")
public @ResponseBody
Cart getCartById (@PathVariable(value = "cartId") int cartId) {
	System.out.println("inside refresh cart");
     Cart cart=cartService.getCartById(cartId);
     System.out.println("cart object:"+cart);
     return cart;
}
@RequestMapping(value = "/removeItem/{itemId}", method = RequestMethod.PUT)
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public void removeItem (@PathVariable(value = "itemId") int itemId) {
	System.out.println("itemId:"+itemId);
	System.out.println("about to get the item");
    CartItem cartItem = cartItemService.getCartItemByItemId(itemId);
    System.out.println("cart item:"+cartItem);
    cartItemService.removeCartItem(cartItem);
}
/*
 * clearCartItems method is used to remove all items from user cart.
 */
@RequestMapping(value = "/clearCartItems/{cartId}", method = RequestMethod.DELETE)
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public void clearCartItems(@PathVariable(value = "cartId") int cartId) {
	
    Cart cart = cartService.getCartById(cartId);
    System.out.println("mycart:"+cart.getCartId());
   // System.out.println("cart value:"+cartId);
    cartItemService.removeAllCartItems(cart);
    System.out.println(" after remove cart clear");
}
@ExceptionHandler(IllegalArgumentException.class)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload.")
public void handleClientErrors (Exception e) {}

@ExceptionHandler(Exception.class)
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
public void handleServerErrors (Exception e) {}
}
