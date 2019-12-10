package com.tiw2019.buyer_seller.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tiw2019.buyer_seller.dao.TransactionDAO;
import com.tiw2019.buyer_seller.model.Transaction;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class TransactionController {

	@Autowired
	TransactionDAO transactionDAO;

	@RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllTransactions() {

		try {
			List<Transaction> entityList = transactionDAO.findAll();

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> sendTransaction(@RequestBody Transaction transaction) {
		
		int card_number_length = transaction.getCard_number().length();
		int cvv_length = transaction.getCvv().length();
		
		if(card_number_length == 16 && Long.parseLong(transaction.getCard_number()) % 3 == 0 && cvv_length == 3) {
			try {
				Transaction tran = transactionDAO.save(transaction);
				
				return new ResponseEntity<>(tran.getId(), HttpStatus.OK);

			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
		}
	}
	
	


}
