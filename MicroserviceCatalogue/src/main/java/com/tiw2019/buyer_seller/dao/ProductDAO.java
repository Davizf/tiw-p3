package com.tiw2019.buyer_seller.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiw2019.buyer_seller.model.Product;

public interface ProductDAO extends CrudRepository<Product, Integer> {

	List<Product> findAll();

	@Query("SELECT p FROM Product p WHERE p.userBean IS NOT NULL")
	List<Product> findAllAvailable();

	@Query("SELECT p FROM Product p WHERE p.categoryBean.name LIKE :category")
	List<Product> findAllByCategoryAdmin(@Param("category") String category);
	@Query("SELECT p FROM Product p WHERE p.categoryBean.name LIKE :category AND p.userBean IS NOT NULL")
	List<Product> findAllByCategory(@Param("category") String category);

	@Query("SELECT p FROM Product p WHERE p.categoryBean.id LIKE :category")
	List<Product> findAllByCategoryIdAdmin(@Param("category") Integer id_category);
	@Query("SELECT p FROM Product p WHERE p.categoryBean.id LIKE :category AND p.userBean IS NOT NULL")
	List<Product> findAllByCategoryId(@Param("category") Integer id_category);

	@Query("SELECT p FROM Product p WHERE p.categoryBean.id IN :categories")
	List<Product> findAllByCategoriesIdAdmin(@Param("categories") List<Integer> categories);
	@Query("SELECT p FROM Product p WHERE p.categoryBean.id IN :categories AND p.userBean IS NOT NULL")
	List<Product> findAllByCategoriesId(@Param("categories") List<Integer> categories);

	@Query("SELECT p FROM Product p WHERE p.userBean IS NOT NULL ORDER BY p.id DESC")
	List<Product> findAllOrderByIdAdmin();
	@Query("SELECT p FROM Product p WHERE p.userBean IS NOT NULL AND p.userBean IS NOT NULL ORDER BY p.id DESC")
	List<Product> findAllOrderById();

	@Query("SELECT p FROM Product p WHERE p.userBean.email LIKE :email")
	List<Product> findAllBySeller(@Param("email") String email);

	@Query("SELECT p FROM Product p WHERE p.name LIKE :name")
	List<Product> getProductByNameAdmin(@Param("name") String name);
	@Query("SELECT p FROM Product p WHERE p.name LIKE :name AND p.userBean IS NOT NULL")
	List<Product> getProductByName(@Param("name") String name);

	@Query("SELECT p FROM Product p WHERE p.shipPrice <= :ship_price AND p.userBean IS NOT NULL")
	List<Product> findAllByShipment(@Param("ship_price") BigDecimal ship_price);

	@Query("SELECT p FROM Product p WHERE p.salePrice BETWEEN :sale_price_1 AND :sale_price_2 AND p.userBean IS NOT NULL")
	List<Product> findAllBetweenSalePrices(@Param("sale_price_1") BigDecimal sale_price_1, @Param("sale_price_2") BigDecimal sale_price_2);

	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :price_1 AND :price_2 AND p.userBean IS NOT NULL")
	List<Product> findAllBetweenPrices(@Param("price_1") BigDecimal price_1, @Param("price_2") BigDecimal price_2);

	@Query("SELECT p FROM Product p WHERE p.stock > :stock AND p.userBean IS NOT NULL")
	List<Product> findAllByStock(@Param("stock") Integer stock);

}