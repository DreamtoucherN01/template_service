package com.blake.util.share.db.dao;

import java.io.Serializable;

public class Info implements Serializable {  
  
    private static final long serialVersionUID = -5785857960597910259L;  
      
    private long incre;  
    
    private String key;
    
    private String body;
    

	public Info(long l, String key, String body) {

		this.incre = l;
		this.key = key;
		this.body = body;
	}

	public long getIncre() {
		return incre;
	}

	public void setIncre(int incre) {
		this.incre = incre;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Info [incre=" + incre + ", key=" + key + ", body=" + body + "]";
	}

}
