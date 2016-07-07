package com.blake.util.share.db.dao;

import java.io.Serializable;

public class Info implements Serializable {  
  
    private static final long serialVersionUID = -5785857960597910259L;  
      
    private int incre;  
      
    private String body;
    
	public Info(int incre, String body) {

		this.incre = incre;
		this.body = body;
	}

	public int getIncre() {
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

	@Override
	public String toString() {
		return "User [incre=" + incre + ", body=" + body + "]";
	}  
}
