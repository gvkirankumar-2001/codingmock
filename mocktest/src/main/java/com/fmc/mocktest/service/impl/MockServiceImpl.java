package com.fmc.mocktest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fmc.mocktest.dto.CustomerDto;
import com.fmc.mocktest.dto.OrderDto;
import com.fmc.mocktest.entities.Customer;
import com.fmc.mocktest.entities.Order;
import com.fmc.mocktest.exception.ResourceNotFoundException;
import com.fmc.mocktest.repo.CustomerRepo;
import com.fmc.mocktest.repo.OrderRepo;
import com.fmc.mocktest.service.MockService;
import com.fmc.mocktest.utility.Mapper;

@Service
public class MockServiceImpl implements MockService {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	Mapper mapper;

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = customerRepo.save(mapper.mapCustomerDtoToCustomer(customerDto));
		return mapper.mapCustomerToCustomerDto(customer);
	}

	@Override
	public String deleteOrderById(Long orderId) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "OrderID", orderId));
		orderRepo.delete(order);
		return "Order with Order ID : " + orderId + " was found and deleted successfully";
	}

	@Override
	public CustomerDto getCustomerById(Long customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId));
		return mapper.mapCustomerToCustomerDto(customer);
	}

	@Override
	public List<CustomerDto> getAllCustomers(Pageable pageable) {
		Page<Customer> page = customerRepo.findAll(pageable);
		List<Customer> customers = page.getContent();
		return customers.stream().map(customer -> mapper.mapCustomerToCustomerDto(customer))
				.collect(Collectors.toList());
	}

	@Override
	public OrderDto placeOrder(OrderDto orderDto) {
		customerRepo.findById(orderDto.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", orderDto.getCustomerId()));
		Order order = orderRepo.save(mapper.mapOrderDtoToOrder(orderDto));
		return mapper.mapOrderToOrderDto(order);
	}

	@Override
	public String updateCustomerById(CustomerDto customerDto) {
		Optional<Long> customerId = Optional.ofNullable(customerDto.getId());
		if (customerId.isPresent()) {
			Customer customer = customerRepo.findById(customerId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.get()));
			customer.setName(customerDto.getName());
			customer.setEmail(customerDto.getEmail());
			customerRepo.save(customer);
		} else {
			throw new ResourceNotFoundException("Customer", "CustomerID", customerId.get());
		}
		return "Customer with customer ID : " + customerDto.getId() + " was found and updated succesfully";
	}

	@Override
	public List<OrderDto> getAllOrderByCustomerId(Long customerId) {
		customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId));
		return orderRepo.getAllOrdersByCustomerId(customerId).stream().map(order -> mapper.mapOrderToOrderDto(order))
				.collect(Collectors.toList());
	}

	@Override
	public Double getTotalAmountByCustomerId(Long customerId) {
		customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId));
		return orderRepo.getTotalAmountByCustomerId(customerId);
	}

}
