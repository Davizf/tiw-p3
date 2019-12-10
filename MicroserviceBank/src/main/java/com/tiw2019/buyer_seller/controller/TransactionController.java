package com.tiw2019.buyer_seller.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

			return new ResponseEntity<List<Transaction>>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> sendTransaction(@RequestBody Transaction transaction) throws ParseException {
		
		long cardNumber = Long.parseLong(transaction.getCard_number());
		int cardNumberLength = transaction.getCard_number().length();
		int cvvLength = transaction.getCvv().length();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date cardExpiryDate = sdf.parse(transaction.getExpiry_date());
		Date date = new Date();
		String currentDate = sdf.format(date);
		Date current = sdf.parse(currentDate);
		
		
		if(cardNumberLength == 16 &&  cardNumber % 3 == 0 && cardExpiryDate.after(current) && cvvLength == 3) {
			try {
				Transaction tran = transactionDAO.save(transaction);
				
				return new ResponseEntity<String>(tran.getId(), HttpStatus.OK);

			} catch (Exception e) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<Void>(HttpStatus.PAYMENT_REQUIRED);
		}
	}
	
	


}
