package com.gabriel.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
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
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		sale.setDate(dateFormat.format(date));
		sale.setTitle(saleDTO.getTitle());
		sale.setDescription(saleDTO.getDescription());
		sale.setValue(saleDTO.getValue());

		return saleRepository.create(sale);
	}

	public Sale updateSale(Long id, CreateSaleDTO updatedSale) {
		Sale sale = new Sale();
		sale.setTitle(updatedSale.getTitle());
		sale.setDate(updatedSale.getDate());
		sale.setValue(updatedSale.getValue());
		sale.setDescription(updatedSale.getDescription());
		return saleRepository.update(id, sale);
	}
}
