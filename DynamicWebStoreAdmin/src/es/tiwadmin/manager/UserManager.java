package es.tiwadmin.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.tiwadmin.model.User;

public class UserManager {
	private EntityManagerFactory emf;
	
	public UserManager(String schemaName) {
		this.emf = Persistence.createEntityManagerFactory(schemaName);
	}
	
	public Boolean verifyAdmin(String email, String password) {
		User user = null;
		EntityManager em = this.emf.createEntityManager();
		
		try {
			user = (User) em.find(User.class, email);
		} finally {
			em.close();
		}
		
		return user != null ? user.getType() == 2 && user.getPassword().equals(password) : false;
	}
	
	public User getUser(String email) {
		User user = null;
		EntityManager em = this.emf.createEntityManager();
		
		try {
			user = (User) em.find(User.class, email);
		} finally {
			em.close();
		}
		
		return user;
	}
	
	public boolean userExists(String email) {
		User user = null;
		EntityManager em = this.emf.createEntityManager();
		
		try {
			user = (User) em.find(User.class, email);
		} finally {
			em.close();
		}
		
		return user != null;
	}
	
	public void createUser(User user) {
		EntityManager em = this.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				//throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		
		return;
	}
	
	public void updateUser(User user) {
		EntityManager em = this.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			user = em.merge(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				//throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		
		return;
	}
	
	public void deleteUser(String userEmail) {
		EntityManager em = this.emf.createEntityManager();
		User user = this.getUser(userEmail);
		if(user == null) return;
		
		try {
			em.getTransaction().begin();
			user = em.merge(user);
			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				//throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return;
	}
	
	public List<User> findAllUsers() {
		EntityManager em = this.emf.createEntityManager();
		Query query = em.createNamedQuery("User.findAll", User.class);
		
		@SuppressWarnings("unchecked")
		List<User> out = (List<User>) query.getResultList();
		em.close();
		return out;
	}
	
}
