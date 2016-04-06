package com.iflytek.voicedemo;

public enum ServiceName {
	
	baike,calc,music,schedule,translation,weather,openQA,datetime,faq,chat,CALL,message,noService;
	
	public static ServiceName getServiceName(String serviceName){
		
		if (serviceName == null) {
			return valueOf("noService");
		}
		
		return valueOf(serviceName);
	}
}
