package com.cande.punkbar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cande.punkbar.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	public CartItem findByCartIdAndProductNumberAndCategory(int cartId, int theProductNumber, String theCategory);
}
