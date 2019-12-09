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

import com.tiw2019.buyer_seller.dao.MessageDAO;
import com.tiw2019.buyer_seller.model.Message;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class ChatController {

	@Autowired
	MessageDAO messageRepository;

	@RequestMapping(value = "/messages", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllMessages() {

		try {
			List<Message> entityList = messageRepository.findAll();

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/messages", params = "receiver", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUserMessages(@RequestParam(value = "receiver", required = true) String receiver) {

		try {
			
			List<Message> entityList = messageRepository.findByReceiver(receiver);

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/messages", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> sendMessage(@RequestBody Message message) {

		try {

			messageRepository.save(message);
			return new ResponseEntity<Void>(HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@DeleteMapping("messages/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
		try {
			messageRepository.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}



}
