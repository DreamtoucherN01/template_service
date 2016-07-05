package com.blake.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.blake.handler.InsertEventHandler;
import com.blake.util.share.type.HttpMethod;

public class InsertRequest implements HttpRequestHandler{

	final static Logger logger = LoggerFactory.getLogger(InsertRequest.class);
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			
			logger.debug("InsertHandler invoked");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		if(HttpMethod.fromString(request.getMethod()) != HttpMethod.POST) {
			
			String respStr = "{\"result\":false,\"err\":1001,\"ver\":1}";
			if(logger.isDebugEnabled()) {
				
				logger.debug("the response is {}" , respStr);
			}
			response.setContentLength(respStr.getBytes().length);
			try {
				
				response.getWriter().write(respStr);
				response.getWriter().close();
				return;
			} catch (IOException e) {
				
				logger.error("catch IOEXception : {}", e);
			}
		}
		
		InsertEventHandler eventHandler = new InsertEventHandler(request, response);
		eventHandler.handleEvent();
		
		String respStr = "{\"result\":true,\"err\":0,\"ver\":1}";
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , respStr);
		}
		response.setContentLength(respStr.getBytes().length);
		try {
			
			response.getWriter().write(respStr);
			response.getWriter().close();
			return;
		} catch (IOException e) {
			
			logger.error("catch IOEXception : {}", e);
		}
		
	}

}
