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

public class DecouplerSreverHandler implements HttpRequestHandler {

	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
