package com.tiw2019.buyer_seller.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.tiw2019.buyer_seller.model.Transaction;

public interface TransactionDAO extends CrudRepository<Transaction, String>{
		
	List<Transaction> findAll();

}



