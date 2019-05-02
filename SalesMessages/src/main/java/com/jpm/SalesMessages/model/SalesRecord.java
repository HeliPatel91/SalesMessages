package com.jpm.SalesMessages.model;

import java.util.ArrayList;
import java.util.List;

/*
 * hold sales record for internal logic
 */
public class SalesRecord {

	//list which holds all the sale data
	private List<Sale> listOfSales = new ArrayList<Sale>();

	//number of sales of particular type
	private int salesCount = 0;
	
	//total value/amount of all the sales
	private long totalAmount = 0;
	
	private List<Adjustment> listOfAdjustments = new ArrayList<Adjustment>();

	public int getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

	public List<Sale> getListOfSales() {
		return listOfSales;
	}

	public void setListOfSales(List<Sale> listOfSales) {
		this.listOfSales = listOfSales;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Adjustment> getListOfAdjustments() {
		return listOfAdjustments;
	}

	public void setListOfAdjustments(List<Adjustment> listOfAdjustments) {
		this.listOfAdjustments = listOfAdjustments;
	}
	
}
