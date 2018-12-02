package com.waracle.cakemgr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * This class serves as in memory persistent class. Data will be available until the restart of the server
 * @author srkrovi
 *
 */
public class CakeStore {
	
	private List<Cake> cakes;
	
	 // Singleton
   private static volatile CakeStore instance;
   private static Object mutex = new Object();
   
   private CakeStore() {
   	cakes = new ArrayList<>();
   	//initialize();
   }
   
   public static CakeStore getInstance() {
      if(instance == null) {
    	  synchronized (mutex) {
    		  if (instance == null)
    			  instance = new CakeStore();
    	  }
         
      }
      
      return instance;
   }

	public List<Cake> getCakes() {
		return cakes;
	}
	
	
	
	public void addCake(Cake cake) {
		getCakes().add(cake);
		
	}
	
	public String getCakesAsJson() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    
        ObjectMapper mapper = new ObjectMapper();
	    try {
			mapper.writeValue(out, getCakes());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				mapper.writeValue(out, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				mapper.writeValue(out, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				mapper.writeValue(out, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	    final byte[] data = out.toByteArray();
	    return new String(data);
		
	}
	
	/*public String getCakesAsJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getCakes());
	
	}*/
	
	

		
	


}
