package com.tiw2019.buyer_seller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.UserDAO;
import com.tiw2019.buyer_seller.model.User;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="users", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUsers(){
		try {
			List<User> users = userDAO.findAll();
			return new ResponseEntity<>(users, 
					(users.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="users/{email}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserByEmail(@PathVariable(value = "email", required = true) String email){
		try {
			User user = userDAO.findByEmail(email);
			return new ResponseEntity<>(user, 
					(user != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="users", params = "type", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUsersByType(@RequestParam(value = "type", required = true) Integer type){
		try {
			List<User> users = userDAO.findByType(type);
			return new ResponseEntity<List<User>>(users, 
					(users.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "users", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createUser(@RequestBody(required = true) User user) {
		try {
			User userFind = userDAO.findByEmail(user.getEmail());

			if (userFind != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			userDAO.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "users/{email}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateUser(@PathVariable(value = "email", required = true) String email, @RequestBody(required = true) User user) {// TODO probar
		try {
			User userFind = userDAO.findByEmail(email);

			if (userFind == null || !user.getEmail().equals(email)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			user.setEmail(email);
			userDAO.save(user);

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("users/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable String email) {
		try {
			userDAO.deleteById(email);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}
