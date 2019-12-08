package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.User;

public interface UserDAO extends CrudRepository<User, String> {

	List<User> findAll();

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.type LIKE :type")
	List<User> findByType(@Param("type") int type);
}
