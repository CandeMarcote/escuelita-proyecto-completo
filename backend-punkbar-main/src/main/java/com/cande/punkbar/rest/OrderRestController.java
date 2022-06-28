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

import com.cande.punkbar.entity.Order;
import com.cande.punkbar.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

	private OrderService orderService;
	
	@Autowired
	public OrderRestController(OrderService theOrderService) {
		orderService = theOrderService;
	}
	
	@GetMapping(value="/")
	@CrossOrigin
	public List<Order> findAll() {
		return orderService.findAll();
	}
	
	@GetMapping(value="/{orderId}")
	@CrossOrigin
	public Order getOrder(@PathVariable int orderId) {
		Order theOrder = orderService.findById(orderId);
		
		if(theOrder == null) {
			throw new RuntimeException("Order id not found - " + orderId);
		}
		
		return theOrder;
	}
	
	@PostMapping("/")
	@CrossOrigin
	public Order addOrder(@RequestBody Order theOrder) {
		orderService.save(theOrder);		
		return theOrder;
	}
	
	@DeleteMapping("/{orderId}")
	@CrossOrigin
	public String deleteOrder(@PathVariable int orderId) {
		Order theOrder = orderService.findById(orderId);
		if(theOrder == null) {
			throw new RuntimeException("Order not found " + orderId);
		}
		
		orderService.deleteById(orderId);
		
		return "Order (id: " + orderId + ") deleted! user: ";
	}
}

