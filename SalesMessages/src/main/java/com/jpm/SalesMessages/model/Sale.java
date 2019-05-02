package com.jpm.SalesMessages.model;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModelProperty;

/*
 * Sale data for incoming  messages
 */
@Component
public class Sale {
 
	@ApiModelProperty(notes = "the type of the sale" , required = true)
	@NotNull
	private String type;
	
	@ApiModelProperty(notes = "the value of each sale" , required = true)
	@NotNull
	private Long value;
	
	@ApiModelProperty(notes = "the quantity/count of sale")
	private Integer count;

	public String getType() {
		return type.toUpperCase();
	}

	public void setType(String type) {
		this.type = type.toUpperCase();
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
