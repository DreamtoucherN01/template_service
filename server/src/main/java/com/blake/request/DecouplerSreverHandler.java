package com.blake.request;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.blake.decoupler.DecouplerServiceImpl;

public class DecouplerSreverHandler implements HttpRequestHandler {

	private DecouplerServiceImpl decouplerServiceImpl;
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	decouplerServiceImpl = wac.getBean("DecouplerServiceImpl", DecouplerServiceImpl.class);  
	
    	decouplerServiceImpl.startService();
	}
}
