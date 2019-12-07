package managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.WishList;

public class WishListManager {

	private EntityManagerFactory emf;

	public WishListManager() {

	}

	public WishListManager(EntityManagerFactory emf) {
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

	public String createWishList(WishList wishList) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(wishList);
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

	public String deleteWishList(WishList wishList) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			wishList = em.merge(wishList);
			em.remove(wishList);
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

	public String updateWishList(WishList wishList) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			wishList = em.merge(wishList);
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
	public List<WishList> getWishListByUserAndProduct(String email, int product) {
		List<WishList> WishList = null;
		EntityManager em = getEntityManager();
		try {
			WishList = (List<WishList>) em.createNamedQuery("WishList.findByUserAndProduct").setParameter("email", email)
					.setParameter("product", product).setMaxResults(1).getResultList();
		} finally {
			em.close();
		}
		return WishList;
	}

}
