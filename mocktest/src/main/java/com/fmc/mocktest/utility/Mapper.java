package com.fmc.mocktest.utility;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fmc.mocktest.dto.CustomerDto;
import com.fmc.mocktest.dto.OrderDto;
import com.fmc.mocktest.entities.Customer;
import com.fmc.mocktest.entities.Order;
import com.fmc.mocktest.exception.ResourceNotFoundException;
import com.fmc.mocktest.repo.CustomerRepo;

@Component
public class Mapper {
	
	@Autowired
	CustomerRepo customerRepo;
	
	public Order mapOrderDtoToOrder(OrderDto orderDto) {
		Order order = new Order();
		order.setTotalAmount(orderDto.getTotalAmount());
		order.setDate(LocalDate.now());
		order.setCustomer(customerRepo.findById(orderDto.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", orderDto.getCustomerId())));
		return order;
	}
	
	public OrderDto mapOrderToOrderDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setTotalAmount(order.getTotalAmount());
		orderDto.setOrderDate(order.getDate());
		orderDto.setCustomerId(order.getCustomer().getId());
		return orderDto;
	}
	
	public Customer mapCustomerDtoToCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		return customer;
	}
	
	public CustomerDto mapCustomerToCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		return customerDto;
	}
}
