package com.fmc.mocktest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fmc.mocktest.dto.CustomerDto;
import com.fmc.mocktest.dto.OrderDto;
import com.fmc.mocktest.entities.Order;
import com.fmc.mocktest.service.MockService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/mock")
public class MockController {

	@Autowired
	MockService mockService;

	@PostMapping
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto create = mockService.createCustomer(customerDto);
		return new ResponseEntity<CustomerDto>(create, HttpStatus.CREATED);
	}
	
	@PostMapping("/order")
	public ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody OrderDto orderDto){
		OrderDto placed = mockService.placeOrder(orderDto);
		return new ResponseEntity<OrderDto>(placed, HttpStatus.CREATED);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Long customerId) {
		CustomerDto findById = mockService.getCustomerById(customerId);
		return new ResponseEntity<CustomerDto>(findById, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CustomerDto>> getCustomerById(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		PageRequest request = PageRequest.of(pageNo, pageSize, sort);
		List<CustomerDto> getAllCustomers = mockService.getAllCustomers(request);
		return new ResponseEntity<List<CustomerDto>>(getAllCustomers, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateCustomerById(@RequestBody CustomerDto customerDto){
		String response = mockService.updateCustomerById(customerDto);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("orderId") Long orderId){
		String response = mockService.deleteOrderById(orderId);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@GetMapping("/allorders/{customerId}")
	public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable("customerId") Long customerId){
		List<OrderDto> orderDtos = mockService.getAllOrderByCustomerId(customerId);
		return new ResponseEntity<List<OrderDto>>(orderDtos, HttpStatus.OK);
	}
	
	@GetMapping("/totalamount/{customerId}")
	public ResponseEntity<Double> getTotalAmount(@PathVariable("customerId") Long customerId){
		Double totalAmount = mockService.getTotalAmountByCustomerId(customerId);
		return new ResponseEntity<Double>(totalAmount, HttpStatus.OK);
	}
}
