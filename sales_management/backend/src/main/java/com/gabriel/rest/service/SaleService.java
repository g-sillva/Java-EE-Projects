package com.gabriel.rest.service;

import java.util.List;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.repository.SaleRepository;
import com.sun.jersey.spi.inject.Inject;

public class SaleService {

	@Inject
	SaleRepository saleRepository;

	public List<Sale> getAll() {
		return saleRepository.findAll();
	}

	public Sale findById(Long id) {
		Sale sale = saleRepository.findById(id);
		return sale;
	}
}
