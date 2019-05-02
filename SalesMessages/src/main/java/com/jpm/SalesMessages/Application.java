package com.jpm.SalesMessages;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Sales Message recording application
 *
 */
@SpringBootApplication
public class Application 
{
	//Spring Context to use later for stopping application
	private static ConfigurableApplicationContext ctx;
	
    public static void main( String[] args )
    {
    	ctx = SpringApplication.run(Application.class, args);
    }
    
    //Shutdown spring context and exit application with return code 0 means no errors 
    public static void exitApplication() {
    	  int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
    	   @Override
    	   public int getExitCode() {
    	    // no errors
    	    return 0;
    	   }
    	  });
    	  System.exit(exitCode);
    	 }
}
