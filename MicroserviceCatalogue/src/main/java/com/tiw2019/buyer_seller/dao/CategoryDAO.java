package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.Category;

public interface CategoryDAO extends CrudRepository<Category, Integer> {

	List<Category> findAll();

	Category findById(int id);

	@Query("SELECT c FROM Category c WHERE c.name LIKE :name")
	List<Category> findByName(@Param("name") String name);

}