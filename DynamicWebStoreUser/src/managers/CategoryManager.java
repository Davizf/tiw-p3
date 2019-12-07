package managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.Category;

public class CategoryManager {

	private EntityManagerFactory emf;

	public CategoryManager() {

	}

	public CategoryManager(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	private EntityManager getEntityManager() {
		if (emf == null) {
			throw new RuntimeException(
					"The EntityManagerFactory is null.  This must be passed in to the constructor or set using the setEntityManagerFactory() method.");
		}
		return emf.createEntityManager();
	}

	public String createCategory(Category category) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(category);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return "";
	}

	public String deleteCategory(Category category) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			category = em.merge(category);
			em.remove(category);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return "";
	}

	public String updateCategory(Category category) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			category = em.merge(category);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategoryByName(String name) {
		List<Category> category = null;
		EntityManager em = getEntityManager();
		try {
			category = (List<Category>) em.createNamedQuery("Category.findByName").setParameter("name", name).setMaxResults(1).getResultList();
		} finally {
			em.close();
		}
		return category;
	}

	public Category getCategoryById(int id) {
		Category category = null;
		EntityManager em = getEntityManager();
		try {
			category = (Category) em.find(Category.class, id);
		} finally {
			em.close();
		}
		return category;
	}

}