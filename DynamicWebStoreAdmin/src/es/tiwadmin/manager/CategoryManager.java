package es.tiwadmin.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.tiwadmin.model.Category;

public class CategoryManager {
	private EntityManagerFactory emf;
	
	public CategoryManager(String schemaName) {
		this.emf = Persistence.createEntityManagerFactory(schemaName);
	}

	public Category getCategory(int categoryId) {
		Category cat = null;
		EntityManager em = this.emf.createEntityManager();
		
		try {
			cat = (Category) em.find(Category.class, categoryId);
		} finally {
			em.close();
		}
		
		return cat;
	}
	
	public List<Category> findAllCategories() {
		EntityManager em = this.emf.createEntityManager();
		Query query = em.createNamedQuery("Category.findAll", Category.class);
		
		@SuppressWarnings("unchecked")
		List<Category> out = (List<Category>) query.getResultList();
		em.close();
		return out;
	}
}
