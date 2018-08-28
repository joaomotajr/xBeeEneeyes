package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import xbee.MainApp;

public class SendRest   {
	
	final static Logger logger = Logger.getLogger(SendRest.class);

	public String host;
	public String id;
	public String value;
		

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public SendRest() {
		
		host = getHost();
		id = getId();
		value = getValue();
	}
	
	public Boolean TestRest(String host) {
		
		try {					
			
			URL url = new URL("http://" + host + "/api/historic/SaveByPositionUid2/0/0");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200) {
				MainApp.logger.error("FAILED : Falha de Acesso ao Servidor [ " + host + "] Info: " + conn.getResponseCode());
				
			}	
				
			conn.disconnect();
	
		  } catch (MalformedURLException e) {
	
			  MainApp.logger.error("URL Malformed or wrong parameters value .... \n");
			  
	
		  } catch (IOException e) {
	
			  MainApp.logger.error("Failed Test. [" + e.getMessage() + "] /n" );
			  
		 }
		
		return true;
	}

	public SendRest(String host, String id, String value) {
		try {					
			
			URL url = new URL("http://" + host + "/api/historic/SaveByPositionUid2/" + id + "/" + value);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200) {
				MainApp.logger.error("FAILED : Falha de Acesso ao Servidor [ " + host + "] Info: " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String output;
			
			while ((output = br.readLine()) != null) {
								
				if(output.toString().equalsIgnoreCase("true")) 
					logger.info("SUCCESS :: Message Delivered to E-Gas");
				else if (output.toString().equalsIgnoreCase("false")) 
					logger.info("WARNING :: Message Delivered to E-Gas - Device id Invalid  ");			
				else 
					logger.info("WARNING :: " + output);					
				
			}
	
			conn.disconnect();
	
		  } catch (MalformedURLException e) {
	
			  logger.warn("URL Malformed or wrong parameters value .... \n");
	
		  } catch (IOException e) {
	
			  logger.warn("Message not Delivered. [" + e.getMessage() + "] /n" );
	
		 }
	}

}
