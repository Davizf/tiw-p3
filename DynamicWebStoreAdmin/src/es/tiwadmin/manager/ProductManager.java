package es.tiwadmin.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.tiwadmin.model.Product;

public class ProductManager {
	private EntityManagerFactory emf;
	
	public ProductManager(String schemaName) {
		this.emf = Persistence.createEntityManagerFactory(schemaName);
	}
	
	public Product getProduct(int id) {
		Product product = null;
		EntityManager em = this.emf.createEntityManager();

		try {
			product = (Product) em.find(Product.class, id);
		} finally {
			em.close();
		}
		
		return product;
	}
	
	public void deleteProduct(int productId) {
		EntityManager em = this.emf.createEntityManager();
		Product product = this.getProduct(productId);
		if(product == null) return;
		
		try {
			em.getTransaction().begin();
			product = em.merge(product);
			em.remove(product);
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
		return;
	}
	
	public Boolean productExists(int productId) {
		Product Product = null;
		EntityManager em = this.emf.createEntityManager();
		
		try {
			Product = (Product) em.find(Product.class, productId);
		} finally {
			em.close();
		}

		return Product != null;
	}
	
	public void createProduct(Product product) {
		EntityManager em = this.emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
			} catch (Exception e) {
				ex.printStackTrace();
			}
		} finally {
			em.close();
		}
		
		return;
	}

	public Product updateProduct(Product product) {
		EntityManager em = this.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			product = em.merge(product);
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
		return product;
	}
	
	public List<Product> findAllProducts() {
		EntityManager em = this.emf.createEntityManager();
		Query query = em.createNamedQuery("Product.findAll", Product.class);
		
		@SuppressWarnings("unchecked")
		List<Product> out = (List<Product>) query.getResultList();
		em.close();
		return out;
	}
	
}
