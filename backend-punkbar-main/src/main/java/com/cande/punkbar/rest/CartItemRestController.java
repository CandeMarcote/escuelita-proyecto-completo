package com.cande.punkbar.rest;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cande.punkbar.entity.Cart;
import com.cande.punkbar.entity.CartItem;
import com.cande.punkbar.service.CartItemService;
import com.cande.punkbar.service.CartService;

@RestController
@RequestMapping("/cartItems")
public class CartItemRestController {

	private CartItemService cartItemService;
	private CartService cartService;
	
	public CartItemRestController (CartItemService theCartItemService, CartService theCartService) {
		cartItemService = theCartItemService;
		cartService = theCartService;
	}
	
	@GetMapping("/all")
	@CrossOrigin
	public List<CartItem> findAll() {
		List<CartItem> theCartItems = cartItemService.findAll();
		return theCartItems;
	}
	
	
	@PostMapping("/")
	@CrossOrigin
	public CartItem addCartItem(@RequestBody CartItem theCartItem, @RequestParam int userId) {
		
		Cart theCart = cartService.findByUserId(userId);
		cartService.save(theCart);
		
		theCartItem.setId(0);
		theCartItem.setCartId(theCart.getId());
		cartItemService.save(theCartItem);
		return theCartItem;
	}
	
	@DeleteMapping("/")
	@CrossOrigin
	public String deleteCartItem(@RequestBody CartItem theCartItem, @RequestParam int userId) {
		Cart theCart = cartService.findByUserId(userId);
		
		CartItem existingItem = cartItemService.findByCartIdAndProductNumberAndCategory(theCart.getId(), theCartItem.getProductNumber(), theCartItem.getCategory());
		cartItemService.deleteById(existingItem.getId());
		return "deleted";
	}
	
	@DeleteMapping("/deleteAll")
	@CrossOrigin
	public String deleteAll() {
		cartItemService.deleteAll();
		return "the cart is empty";
	}
	
	@PutMapping("/")
	@CrossOrigin
	public CartItem updateItem(@RequestBody CartItem theCartItem, @RequestParam int userId) {
		Cart theCart = cartService.findByUserId(userId);
		CartItem existingItem = cartItemService.findByCartIdAndProductNumberAndCategory(
				theCart.getId(), theCartItem.getProductNumber(), theCartItem.getCategory());
		existingItem.setAmount(theCartItem.getAmount());
		cartItemService.deleteById(existingItem.getId());
		cartItemService.save(existingItem);
		return existingItem;
	}
}
