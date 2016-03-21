package com.fan.util;

public enum Service {
	music;
	 public static Service getAnimal(String service){  
		      return valueOf(service.toLowerCase());  
		    }  
}
