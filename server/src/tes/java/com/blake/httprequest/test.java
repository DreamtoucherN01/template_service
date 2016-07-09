package com.blake.httprequest;

import net.sf.json.JSONObject;

import com.blake.httpmethod.HttpGetRequest;
import com.blake.reptile.SpiderThread;
import com.blake.util.Constants;

import junit.framework.TestCase;

public class test {
	
	public void testTest() {
		
		int i = 0;
		while(true) {
			
	        JSONObject jo = new JSONObject();
	        jo.put("type", "insert");
	        
	        JSONObject innerjo = new JSONObject();
	        innerjo.put("text", "zhangjie" + i);
	        jo.put("body", innerjo.toString());
	        System.out.println(jo.toString());
	        System.out.println(HttpGetRequest.sendPost(Constants.serverUrl, jo.toString()));
//	        try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
	        i++;
        }
	}

}
