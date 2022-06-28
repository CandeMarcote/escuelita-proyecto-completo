package com.cande.punkbar.service;

import java.util.List;
import com.cande.punkbar.entity.CartItem;

public interface CartItemService {

	public void save(CartItem theCartItem);
	
	public void deleteById(int theId);
	
	public void deleteAll();
	
	public List<CartItem> findAll();
	
	public CartItem findByCartIdAndProductNumberAndCategory(int cartId, int theProductNumber, String theCategory);
}
