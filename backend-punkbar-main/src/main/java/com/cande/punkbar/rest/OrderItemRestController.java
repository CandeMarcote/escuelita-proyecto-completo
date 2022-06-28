package com.cande.punkbar.rest;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cande.punkbar.entity.Cart;
import com.cande.punkbar.entity.CartItem;
import com.cande.punkbar.entity.Order;
import com.cande.punkbar.entity.OrderItem;
import com.cande.punkbar.service.CartItemService;
import com.cande.punkbar.service.CartService;
import com.cande.punkbar.service.OrderItemService;
import com.cande.punkbar.service.OrderService;

@RestController
@RequestMapping("/orderItems")
public class OrderItemRestController {

	private OrderItemService orderItemService;
	private CartItemService cartItemService;
	private OrderService orderService;
	private CartService cartService;
	
	public OrderItemRestController(OrderItemService theOrderItemService, OrderService theOrderService, CartItemService theCartItemService, CartService theCartService) {
		orderItemService = theOrderItemService;
		cartItemService = theCartItemService;
		orderService = theOrderService;
		cartService = theCartService;
	}
	
	@PostMapping("/place_order")
	@CrossOrigin
	public String addOrderItem(@RequestParam int userId) {
		Cart theCart = cartService.findByUserId(userId);
		Order theOrder = new Order();
		theOrder.setCartId(theCart.getId());
		theOrder.setUserId(userId);
		orderService.save(theOrder);
		System.out.println(theOrder.getId());
		
		
		List<CartItem> cartItems = cartItemService.findAll();
		
		for(int i = 0; i < cartItems.size(); i++) {
			OrderItem theOrderItem = new OrderItem();
			theOrderItem.setAmount(cartItems.get(i).getAmount());
			theOrderItem.setCategory(cartItems.get(i).getCategory());
			theOrderItem.setProductNumber(cartItems.get(i).getProductNumber());
			theOrderItem.setOrderId(theOrder.getId());
			orderItemService.save(theOrderItem);
		}
		
		
		return "added sucessfully";
	}
}
