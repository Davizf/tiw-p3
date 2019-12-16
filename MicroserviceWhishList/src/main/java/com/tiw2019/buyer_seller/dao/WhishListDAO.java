package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.WishList;

public interface WhishListDAO extends CrudRepository<WishList, Integer>{
	
	@Query("SELECT w FROM WishList w WHERE w.userBean.email LIKE :email")
	List<WishList> findByEmail(@Param("email") String email);
	
	@Query("SELECT w FROM WishList w WHERE w.userBean.email LIKE :email AND w.productBean.id LIKE :id")
	WishList findByEmailAndProduct(@Param("email") String email, @Param("id") Integer id);
	
}
