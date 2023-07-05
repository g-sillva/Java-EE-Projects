package com.gabriel.rest.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManager {

	private EntityManagerFactory factory;
	private EntityManager em;

	public JpaEntityManager(String persistenceUnitName) {
		factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		em = factory.createEntityManager();
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void close() {
		em.close();
		factory.close();
	}
}
