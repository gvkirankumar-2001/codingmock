package com.fmc.mocktest.exception;

public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;
	private String fieldName;
	private Long fieldId;

	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
		super(String.format("%s is not found with %s : %s", resourceName, fieldName, fieldId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setDetails(Long fieldId) {
		this.fieldId = fieldId;
	}

}
