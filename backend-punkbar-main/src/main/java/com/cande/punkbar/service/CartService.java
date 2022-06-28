package com.cande.punkbar.service;

import java.util.List;

import com.cande.punkbar.entity.Cart;

public interface CartService {

	public List<Cart> findAll();
	
	public Cart findById(int theId);
	
	public void save(Cart theCart);
	
	public void deleteById(int theId);
	
	public Cart findByUserId(int theUserId);
}
