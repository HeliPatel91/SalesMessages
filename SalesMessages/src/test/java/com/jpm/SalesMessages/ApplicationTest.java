package com.jpm.SalesMessages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpm.SalesMessages.model.Message;
import com.jpm.SalesMessages.model.Sale;
import com.jpm.SalesMessages.model.SalesRecord;
import com.jpm.SalesMessages.service.SalesService;

/**
 * Unit test for Sales Messages App API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest
{
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private SalesService salesService;
	
	@Test
    public void testSalesMessage() throws Exception
    {
		Message message = new Message();
		Sale sale = new Sale();
		sale.setType("apple");
		sale.setValue(new Long(10));
		sale.setCount(5);
		message.setSale(sale);
		mvc.perform(MockMvcRequestBuilders.post("/Sales")
				  .content(asJsonString(message))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
        		  .andExpect(status().isOk());
    }
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	} 

	@Test
	public void testMessageRecorded() throws Exception
    {
		SalesRecord salesRecord = salesService.getListOfSalesRecords().get("APPLE");
		assertThat(salesRecord)
			.isNotNull();
		assertThat(salesRecord.getTotalAmount())
	     	.isEqualTo(50);
		assertThat(salesRecord.getSalesCount())
			.isEqualTo(5);
		assertThat(salesRecord.getListOfSales().get(0).getValue())
			.isEqualTo(10);
		assertThat(salesRecord.getListOfSales().get(0).getCount())
			.isEqualTo(5);
		assertThat(salesRecord.getListOfSales().get(0).getType())
			.isEqualToIgnoringCase("apple");
    }
    
	
}
