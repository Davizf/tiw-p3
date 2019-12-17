package com.tiw2019.buyer_seller.dao;

import org.springframework.data.repository.CrudRepository;

import com.tiw2019.buyer_seller.model.Orders_has_Product;

public interface Orders_has_ProductDAO extends CrudRepository<Orders_has_Product, Integer> {
	
}
