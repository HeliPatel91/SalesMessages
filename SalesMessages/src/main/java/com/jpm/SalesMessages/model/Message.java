package com.jpm.SalesMessages.model;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModelProperty;

/*
 * Message model for incoming messages
 */
@Component
public class Message {
	
	private Sale sale;
	
	@ApiModelProperty(notes = "the adjustment operation to perform", allowableValues="ADD,SUBSTRACT,MULTIPLY")
	private Operation operation;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
