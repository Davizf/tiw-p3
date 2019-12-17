package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.Orders_has_Product;

public interface Orders_has_ProductDAO extends CrudRepository<Orders_has_Product, Integer> {
	
	@Query("SELECT ohp FROM Orders_has_Product ohp WHERE ohp.productBean.id LIKE :productId")
	List<Orders_has_Product> findAllByProduct(@Param("productId") int productId);
	
}
