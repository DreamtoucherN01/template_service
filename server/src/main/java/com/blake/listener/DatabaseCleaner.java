package com.blake.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DatabaseCleaner implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	
	public void contextDestroyed(ServletContextEvent arg0) {

		scheduler.shutdownNow();
	}

	public void contextInitialized(ServletContextEvent event) {

		 ServletContext servletContext = event.getServletContext();
		 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		 
		 scheduler = Executors.newScheduledThreadPool(3);
		 
		 System.out.println("contextInitialized");
	}

}
