package com.gabriel.rest.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import com.gabriel.rest.entity.DTO.CreateSaleDTO;
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

	public boolean createSale(CreateSaleDTO saleDTO) {
		Sale sale = new Sale();

		sale.setDate(new Date());
		sale.setTitle(saleDTO.getTitle());
		sale.setDescription(saleDTO.getDescription());
		sale.setValue(saleDTO.getValue());

		return saleRepository.create(sale);
	}
}
