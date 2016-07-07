package com.blake.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.blake.handler.EventHandler;
import com.blake.response.UnifiedResponse;
import com.blake.util.share.type.HttpMethod;

public class DBRequestHandler implements HttpRequestHandler{

	final static Logger logger = LoggerFactory.getLogger(DBRequestHandler.class);
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			
			logger.debug("InsertHandler invoked");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		if(HttpMethod.fromString(request.getMethod()) != HttpMethod.POST) {
			
			UnifiedResponse.sendErrResponse(response, 1001);
		}
		
		EventHandler eventHandler = new EventHandler(request, response);
		eventHandler.handleEvent();
		
		UnifiedResponse.sendSuccessResponse(response);
		
	}

}
