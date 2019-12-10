package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.Message;

public interface MessageDAO extends CrudRepository<Message, String>{
		
	List<Message> findAll();
	
	@Query("SELECT u FROM Messages u WHERE u.receiver=:receiver")
	List<Message> findByReceiver(@Param("receiver")String receiver);

}



