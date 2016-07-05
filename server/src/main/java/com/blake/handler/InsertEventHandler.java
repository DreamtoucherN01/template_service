package com.blake.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.blake.util.share.db.UserAccessor;
import com.mongodb.DB;
import com.mongodb.DBObject;


public class InsertEventHandler{

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	
	public InsertEventHandler(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
	}
	
	public void handleEvent(){
		
		BufferedReader reader = null;
		String line = null;
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		JSONObject jo =   new JSONObject(buffer.toString());
		System.out.println(jo.get("type"));
		UserAccessor accessor = new UserAccessor((String) jo.get("sql"));
		accessor.init();
	}
}
