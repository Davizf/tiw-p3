package com.tiw2019.buyer_seller.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tiw2019.buyer_seller.model.User;

public interface UserDAO extends CrudRepository<User, String> {

	List<User> findAll();
}
