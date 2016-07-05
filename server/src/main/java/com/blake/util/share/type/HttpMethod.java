package com.blake.util.share.type;

public enum HttpMethod {

	GET("GET"),
	POST("POST"),
	OPTIONS("OPTIONS"),
	HEAD("HEAD"),
	PUT("PUT"),
	DELETE("DELETE"),
	TRACE("TRACE"),
	CONNECT("CONNECT");
	
	private String methodName;

	private HttpMethod(String methodName) {
		
		this.methodName = methodName;
	}
	
	public static HttpMethod fromString(String methodName) {
		
		for(HttpMethod name : HttpMethod.values()) {
			
			if(name.methodName.equals(methodName)) {
				
				return name;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
