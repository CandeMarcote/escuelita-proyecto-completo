package com.cande.punkbar.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cande.punkbar.entity.User;
import com.cande.punkbar.service.CartService;
import com.cande.punkbar.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserRestController {

	private UserService userService;
	private CartService cartService;

	//inject userDAO at the beginning but after we'll do it with a service
	@Autowired
	public UserRestController(UserService theUserService, CartService theCartService) {
		userService = theUserService;
		cartService = theCartService;
	}
	//Expose "/users" that returns a list of users
	@CrossOrigin
	@GetMapping("/")
	public List<User> findAll(){
		return userService.findAll();
	}
	
	//add mapping for GET /users/{userId}
	@GetMapping("/{userId}")
	public User getUser(@RequestParam int userId) {
		User theUser = userService.findById(userId);
		
		if(theUser == null) {
			throw new RuntimeException("User id not found: " + userId);
		}
		
		return theUser;
	}
	
	//adding mapping for POST /users - add new users
	@PostMapping("/")
	public User addUser(@RequestBody User theUser) {
		//also just in case they pass an id in json ..set id to 0
		//this is to force a save of a new item ... instead of update
		theUser.setId(0);
		userService.save(theUser);
		Cart theCart = new Cart();
		theCart.setUserId(1);
		theCart.setUserId(theUser.getId());
		cartService.save(theCart);
		
		return theUser;
	}
	
	//consulting the user login
	@PostMapping("/login")
	public int validateUser(@RequestBody User theUser){
		Optional<User> user = userService.findByUsernameOrEmailAndPassword(
				theUser.getUsername(), theUser.getEmail(), theUser.getPassword());
		int id = -1;
		if(user.isPresent()) {
			
			if(user.get().getPassword().equals(theUser.getPassword())) {
				
				id = user.get().getId();
			}
		}
		return id;
	}

	
	// add mapping for updating users
	
	@PutMapping("/")
	public User updateUser(@RequestBody User theUser) {
		userService.save(theUser);
		return theUser;
	}
	
	@DeleteMapping("/")
	public String deleteUser(@RequestParam int userId) {
		
		User theUser = userService.findById(userId);
		
		//throw exception if null
		if(theUser == null) {
			throw new RuntimeException("User id not found - " + userId);
		}
		
		userService.deleteById(userId);
		return "Deleted user id - " + userId;
	}
}
