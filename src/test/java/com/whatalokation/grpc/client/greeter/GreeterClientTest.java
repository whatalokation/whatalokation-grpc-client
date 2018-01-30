package com.whatalokation.grpc.client.greeter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jose4j.jwt.JwtClaims;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.protobuf.ByteString;
import com.whatalokation.grpc.client.greeter.GreeterClient;

import io.grpc.StatusRuntimeException;

public class GreeterClientTest {
	

	String environment = null;
	int port;
	GreeterClient gclient;

	private static final Logger logger = Logger.getLogger("Whatalokation");
	
	private void readProperties(){
		ResourceBundle unittestBundle = ResourceBundle.getBundle("unittest", Locale.getDefault());
		this.environment = unittestBundle.getString("environment");
		this.port = Integer.parseInt(unittestBundle.getString("port"));
	}
	
	@Before
    public void setUp() {
		logger.setLevel(Level.INFO);
		readProperties();
		gclient = new GreeterClient(String.format("greeter-%s.endpoint.whatalokationdev.com",environment), this.port);	
    }
	
	@Test
	public void testHealthcheck() {
		
	    try {
	    	String response = gclient.healthcheck();
	    	assertTrue(response.contains("Succeeded"));
	    } 
	    catch (StatusRuntimeException e){
	    	
	    	fail("healthcheck threw StatusRuntimeException");
	    }    
	}

	
	
	
	
	@After
    public void tearDown() {
		try {
			gclient.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
