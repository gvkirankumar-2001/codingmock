package com.fmc.mocktest.payload;

import java.util.Date;

import lombok.Data;

@Data
public class CustomExceptionResponse {

	private String message;
	private Date date;
	private String details;
}
