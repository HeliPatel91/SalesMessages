package com.jpm.SalesMessages.model;

/*
 * the adjustment place holder for internal logic
 */
public class Adjustment {

	private Operation operation;
	
	private long value;
	
	private String type;

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
