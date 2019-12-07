package es.uc3m.buyer_seller.controller;

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

import es.uc3m.buyer_seller.dao.CategoryDAO;
import es.uc3m.buyer_seller.model.Category;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class CategoryController {

	@Autowired
	CategoryDAO categoryDAO;

	@RequestMapping(value="categories", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategories(){
		try {
			List<Category> c = categoryDAO.findAll();
			return new ResponseEntity<>(c, 
					(c.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="categories/{id}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategoryById(@PathVariable(value = "id", required = true) Integer id){
		try {
			Optional<Category> c = categoryDAO.findById(id);
			return new ResponseEntity<>(c, 
					(c.isPresent()) ? HttpStatus.OK : HttpStatus.NO_CONTENT
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="categories", params = "name", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCategoryByName(@RequestParam(value = "name", required = true) String name){
		try {
			List<Category> c = categoryDAO.findByName(name);
			return new ResponseEntity<>(c, 
					(c.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK
					);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "categories", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createCategory(@RequestBody(required = true) Category category) {
		try {
			categoryDAO.save(category);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "categories/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCategory(@PathVariable(value = "id", required = true) Integer id, @RequestBody(required = true) Category category) {// TODO probar
		try {
			Optional<Category> c = categoryDAO.findById(id);

			if (!c.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			category.setId(id);
			categoryDAO.save(category);

			return new ResponseEntity<>(category, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
		try {
			categoryDAO.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}