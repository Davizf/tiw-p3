package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.Order;

public interface OrderDAO extends CrudRepository<Order, Integer>{

	List<Order> findAll();
	
	@Query("SELECT o FROM Order o WHERE o.userBean.email LIKE :email")
	List<Order> findAllByEmail(@Param("email") String email);
	
}
