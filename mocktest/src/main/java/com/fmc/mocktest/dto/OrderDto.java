package com.fmc.mocktest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private LocalDate orderDate;
	@Positive
	private Double totalAmount;
	@Positive
	private Long customerId;
}
