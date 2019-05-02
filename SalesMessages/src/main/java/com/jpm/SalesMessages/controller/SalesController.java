package com.jpm.SalesMessages.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jpm.SalesMessages.Application;
import com.jpm.SalesMessages.exceptions.SalesApplicationException;
import com.jpm.SalesMessages.model.Message;
import com.jpm.SalesMessages.service.SalesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*
 * Application controller to accept API requests
 * Could have created separate API for Adjustments but for simplicity kept one
 */
@RestController
@Api(value="salesMessages", description="Sales message recording service")
public class SalesController {

	//counter to count request messages
	private int counter = 0;
	
	@Autowired
	private SalesService salesService;
	
	@ApiOperation(value = "Record all type of sales messages", response = HashMap.class)
	@PostMapping("/Sales")
	public ResponseEntity<String> salesMessage(@RequestBody Message message)
	{
		try
		{
			counter += 1;
			//record each coming message
			salesService.recordMessage(message);

			//if counter reached 10 then print the report
			if(counter % 10 == 0)
			{
				salesService.logMessage();
			}

			//if counter reaches 50 print the adjustments and stop the application
			if(counter == 50)
			{
				salesService.logAdjustments();
				Application.exitApplication();
			}
			return new ResponseEntity<String>("Recorded", HttpStatus.OK);
		}
		catch (SalesApplicationException exc) {
			 exc.printStackTrace();
	         throw new ResponseStatusException(
	           HttpStatus.BAD_REQUEST, "The requested values are not suppoerted", exc);
	    }
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new ResponseStatusException(
			   HttpStatus.INTERNAL_SERVER_ERROR, "System encountered issue, please report this", ex);
		}
	}
	
}
