package com.cande.punkbar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cande.punkbar.dao.CartRepository;
import com.cande.punkbar.entity.Cart;

@Service
public class CartServiceImpl implements CartService {

	private CartRepository cartRepository;
	
	@Autowired
	public CartServiceImpl(CartRepository theCartRepository) {
		cartRepository = theCartRepository;
	}
	
	@Override
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Override
	public Cart findById(int theId) {
		Optional<Cart> result = cartRepository.findById(theId);
		
		Cart theCart = null;
		if(result.isPresent()) {
			theCart = result.get();
		}
		else {
			throw new RuntimeException("The cart id was not found");
		}
		
		return theCart;
	}

	@Override
	public void save(Cart theCart) {
		cartRepository.save(theCart);
	}

	@Override
	public void deleteById(int theId) {
		cartRepository.deleteById(theId);
	}

	@Override
	public Cart findByUserId(int theUserId) {
		Cart theCart = cartRepository.findByUserId(theUserId);
		return theCart;
	}

}