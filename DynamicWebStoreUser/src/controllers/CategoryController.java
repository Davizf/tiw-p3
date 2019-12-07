package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jdbc.info.InformationProperties;
import managers.CategoryManager;
import model.Category;


public class CategoryController {

	public static ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		Category category = null;
		String query = "SELECT * FROM Categories";
		String userName = InformationProperties.getStrUser();
		String password = InformationProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformationProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false&serverTimezone=UTC";
		ResultSet res = null;
		
		try {
			Class.forName(InformationProperties.getStrClassDriver());

			Connection connection = DriverManager.getConnection(url);

			Statement myStatement = connection.createStatement();

			res = myStatement.executeQuery(query);
			while(res.next()) {
				category = new Category();
				category.setId(res.getInt("id"));
				category.setName(res.getString("name"));
				category.setParentId(res.getInt("parent_id"));
				categories.add(category);
			}
			res.close();
			myStatement.close();
			connection.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripci�n: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripci�n: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		}
		
		return categories;
	}
	
	public static Category  getCategory(String name) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		CategoryManager manager = new CategoryManager();
		manager.setEntityManagerFactory(factory);
		Category category = (Category) manager.getCategoryByName(name).get(0);
		factory.close();
		return category;
	}
	
	public static Category getCategory(int id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiw-p1-buyer-seller");		
		CategoryManager manager = new CategoryManager();
		manager.setEntityManagerFactory(factory);
		Category category = (Category) manager.getCategoryById(id);
		factory.close();
		return category;
	}

}
