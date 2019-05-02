package com.jpm.SalesMessages.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jpm.SalesMessages.exceptions.SalesApplicationException;
import com.jpm.SalesMessages.model.Adjustment;
import com.jpm.SalesMessages.model.Message;
import com.jpm.SalesMessages.model.Sale;
import com.jpm.SalesMessages.model.SalesRecord;

/*
 * Service class for holding the logic to store the data and log the report
 */
@Service
public class SalesService {

	//This map holds the <type of the sale, sales record details>
	private Map<String, SalesRecord> listOfSalesRecords = new HashMap<String, SalesRecord>();
	
	//Records the message in SalesRecord
	public void recordMessage(Message message) throws SalesApplicationException
	{
		//if the operation is not null means this is an adjustment message
		if(message.getOperation() != null)
		{
			//if the sale type already exists in the data then continue
			if(listOfSalesRecords.containsKey(message.getSale().getType()))
			{
				//get the existing sales record of the given sale type
				SalesRecord existingTypeSale = listOfSalesRecords.get(message.getSale().getType());
				
				//save the adjustment details
				Adjustment adjust = new Adjustment();
				adjust.setOperation(message.getOperation());
				adjust.setType(message.getSale().getType());
				adjust.setValue(message.getSale().getValue());
				existingTypeSale.getListOfAdjustments().add(adjust);
				
				//perform the adjustment operation and update the total amount
				switch(message.getOperation())
				{
				case ADD:
					existingTypeSale.setTotalAmount(existingTypeSale.getTotalAmount() + (message.getSale().getValue() * existingTypeSale.getSalesCount()));
					break;
				case SUBSTRACT:
					existingTypeSale.setTotalAmount(existingTypeSale.getTotalAmount() - (message.getSale().getValue() * existingTypeSale.getSalesCount()));
					break;
				case MULTIPLY:
					existingTypeSale.setTotalAmount(existingTypeSale.getTotalAmount() * message.getSale().getValue());
					break;
				}
			}
			//else throw the exception that request values are not supported
			else
				throw new SalesApplicationException();
		}
		//if the message is of type sale
		else
		{
			//if the sale type already exists in the message then modify
			if(listOfSalesRecords.containsKey(message.getSale().getType())) 
			{
				//get the sales record to add new sale data
				SalesRecord existingTypeSale = listOfSalesRecords.get(message.getSale().getType());
				existingTypeSale.getListOfSales().add(message.getSale());
				
				//update the sales count and total amount by retrieving existing values and adding new sale information
				if(null!= message.getSale().getCount() && message.getSale().getCount() != 0)
				{
					existingTypeSale.setSalesCount(existingTypeSale.getSalesCount() + message.getSale().getCount());
					existingTypeSale.setTotalAmount(existingTypeSale.getTotalAmount() + (message.getSale().getCount() * message.getSale().getValue()));
				}
				//if the sale count is not present in the request , consider it 1
				else
				{
					existingTypeSale.setSalesCount(existingTypeSale.getSalesCount() + 1);
					existingTypeSale.setTotalAmount(existingTypeSale.getTotalAmount() + message.getSale().getValue());
				}
			}
			//if the sale type does not exists in the message then create
			else
			{
				//blank list of sale
				List<Sale> sales = new ArrayList<Sale>();
				
				//sales record with message data
				SalesRecord salesRecord = new SalesRecord();
				sales.add(message.getSale());
				salesRecord.setListOfSales(sales);
				
				//add the sales count and total amount
				if(null!= message.getSale().getCount() && message.getSale().getCount() != 0)
				{
					salesRecord.setSalesCount(message.getSale().getCount());
					salesRecord.setTotalAmount(message.getSale().getValue() * message.getSale().getCount());
				}
				//if the sale count is not present in the request , consider it 1
				else
				{
					salesRecord.setSalesCount(1);
					salesRecord.setTotalAmount(message.getSale().getValue());
				}
				//finally, add to the map as it is a new entry
				listOfSalesRecords.put(message.getSale().getType(), salesRecord);
			}
		}
	}

	//logs the message when incoming messages reaches 10
	public void logMessage()
	{
		//retrieve the SalesRecord for each type
		for(String key:listOfSalesRecords.keySet())
		{
			long totalValue = listOfSalesRecords.get(key).getTotalAmount();
			long noOfSales = listOfSalesRecords.get(key).getSalesCount();
			
			//print the totalAmount and SalesCount of SalesRecord
			System.out.println("The number of sales of "+key+" is "+noOfSales+" with total amount "+totalValue);
		}
	}
	
	//logs the adjustments when incoming messages reaches 50
	public void logAdjustments()
	{
		System.out.println("Application is pausing and will not accept any more requests");
		for(String key:listOfSalesRecords.keySet())
		{
			List<Adjustment> adjustments = listOfSalesRecords.get(key).getListOfAdjustments();
			for(Adjustment adjusment :adjustments)
			{
				System.out.println("The Adjustments made for "+key+" is "+adjusment.getOperation()+" with value "+adjusment.getValue());
			}
		}
	}
	
}
