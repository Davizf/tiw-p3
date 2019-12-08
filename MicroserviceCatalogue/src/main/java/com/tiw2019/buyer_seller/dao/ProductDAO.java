package com.tiw2019.buyer_seller.dao;

import org.springframework.data.repository.CrudRepository;

import com.tiw2019.buyer_seller.model.Product;

public interface ProductDAO extends CrudRepository<Product, Integer> {

	/*List<Category> findAll();

	Category findById(int id);

	@Query("SELECT c FROM Category c WHERE c.name LIKE :name")
	List<Category> findByName(@Param("name") String name);*/

}