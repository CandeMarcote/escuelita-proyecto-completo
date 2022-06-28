package com.cande.punkbar.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cande.punkbar.entity.Cart;
import com.cande.punkbar.entity.User;
import com.cande.punkbar.service.CartService;
import com.cande.punkbar.service.UserService;

@RestController
@RequestMapping("/cart")
public class CartRestController {

	private CartService cartService;
	private UserService userService;
	
	@Autowired
	public CartRestController(CartService theCartService, UserService theUserService) {
		cartService = theCartService;
		userService = theUserService;
	}
	
	@GetMapping(value="/")
	@CrossOrigin
	public List<Cart> findAll() {
		return cartService.findAll();
	}
	
	@GetMapping(value="/{cartId}")
	@CrossOrigin
	public Cart getCart(@PathVariable int cartId) {
		Cart theCart = cartService.findById(cartId);
		
		if(theCart == null) {
			throw new RuntimeException("Cart id not found - " + cartId);
		}
		
		return theCart;
	}
	
	@PostMapping("/")
	@CrossOrigin
	public Cart addCart(@RequestBody Cart theCart) {
		theCart.setId(0);
		
		cartService.save(theCart);
		return theCart;
	}
	
	@DeleteMapping("/users/{userId}/cart/{cartId}")
	@CrossOrigin
	public String deleteCart(@PathVariable int cartId, @PathVariable int userId) {
		Cart theCart = cartService.findById(cartId);
		User theUser = userService.findById(userId);
		if(theCart == null) {
			throw new RuntimeException("Cart not found " + cartId);
		}
		
		cartService.deleteById(cartId);
		
		return "Cart (id: " + cartId + ") deleted! user: " + theUser.getUsername() + " " + userId;
	}
}
