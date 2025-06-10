package com.fmc.mocktest.service;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fmc.mocktest.dto.CustomerDto;
import com.fmc.mocktest.dto.OrderDto;
import com.fmc.mocktest.entities.Order;

public interface MockService {
	
	CustomerDto createCustomer(CustomerDto customerDto);
	
	OrderDto placeOrder(OrderDto orderDto);
	
	CustomerDto getCustomerById(Long customerId);
	
	List<CustomerDto> getAllCustomers(Pageable pageable);
	
	String deleteOrderById(Long customerId);
	
	String updateCustomerById(CustomerDto customerDto);
	
	List<OrderDto> getAllOrderByCustomerId(Long customerId);
	
	Double getTotalAmountByCustomerId(Long customerId);
}
