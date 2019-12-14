package com.tiw2019.buyer_seller.controller;

import java.util.List;
import java.util.Optional;

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

import com.tiw2019.buyer_seller.dao.CategoryDAO;
import com.tiw2019.buyer_seller.model.Category;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class CategoryController {

	@Autowired
	CategoryDAO categoryDAO;

	@RequestMapping(value="categories", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategories(@RequestParam(value = "name", required = false) String name){
		try {
			List<Category> categories;

			if (name==null) {
				categories = categoryDAO.findAll();
			} else {
				categories = categoryDAO.findByName(name);
			}
			return new ResponseEntity<List<Category>>(categories, 
					(categories.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="categories/{id}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategoryById(@PathVariable(value = "id", required = true) Integer id){
		try {
			Optional<Category> c = categoryDAO.findById(id);

			if (c.isPresent())
				return new ResponseEntity<Category>(c.get(), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@RequestMapping(value = "categories", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> addCategory(@RequestBody(required = true) Category category) {
		try {
			categoryDAO.save(category);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "categories/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> updateCategory(@PathVariable(value = "id", required = true) Integer id, @RequestBody(required = true) Category category) {
		try {
			Optional<Category> c = categoryDAO.findById(id);

			if (!c.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			category.setId(id);
			categoryDAO.save(category);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
		try {
			categoryDAO.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {// No existe con ese id
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}