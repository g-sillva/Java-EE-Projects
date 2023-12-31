package com.gabriel.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gabriel.rest.entity.DTO.CreateSaleDTO;
import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.entity.responses.PaginationResponse;
import com.gabriel.rest.repository.SaleRepository;
import com.sun.jersey.spi.inject.Inject;

import javax.ejb.Stateless;

@Stateless
public class SaleService {

	SaleRepository saleRepository = new SaleRepository();

	public SaleService() {
	}

	public PaginationResponse<Sale> getAll(int page, int pageSize) {
		int totalItems = saleRepository.countAllSales();
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		List<Sale> sales = saleRepository.findAll(page, pageSize);
		return new PaginationResponse<>(page, pageSize, totalItems, totalPages, sales);
	}

	public Sale findById(Long id) {
		return saleRepository.findById(id);
	}

	public Sale createSale(CreateSaleDTO saleDTO) {
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

	public Sale deleteSale(Long id) {
		return saleRepository.delete(id);
	}
}
