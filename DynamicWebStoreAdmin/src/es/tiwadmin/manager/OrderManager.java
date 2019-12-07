package es.tiwadmin.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.tiwadmin.model.Order;

public class OrderManager {
	private EntityManagerFactory emf;
	
	public OrderManager(String schemaName) {
		this.emf = Persistence.createEntityManagerFactory(schemaName);
	}
	
	public List<Order> findAllOrders() {
		EntityManager em = this.emf.createEntityManager();
		Query query = em.createNamedQuery("Order.findAll", Order.class);
		
		@SuppressWarnings("unchecked")
		List<Order> out = (List<Order>) query.getResultList();
		em.close();
		return out;
	}
	
}
