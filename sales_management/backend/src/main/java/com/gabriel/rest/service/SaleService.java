package com.gabriel.rest.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.manager.JpaEntityManager;

@Path("sale")
public class SaleService {

	private JpaEntityManager JPAEM = new JpaEntityManager();
	private EntityManager objEM = JPAEM.getEntityManager();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sale> getAll() {
		try {
			String sql = "SELECT c FROM Sale c";
			List<Sale> sales = objEM.createQuery(sql, Sale.class).getResultList();
			objEM.close();
			return sales;
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}
}
