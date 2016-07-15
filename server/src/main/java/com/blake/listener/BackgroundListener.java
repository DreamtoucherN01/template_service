package com.blake.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.InfoAccessor;

public class BackgroundListener implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	
	public void contextDestroyed(ServletContextEvent arg0) {

		scheduler.shutdownNow();
		System.out.println("contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		

		 ServletContext servletContext = event.getServletContext();
		 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		 scheduler = Executors.newScheduledThreadPool(3);

		DBaccessor dbaccessor = new DBaccessor();
		InfoAccessor accessor = new InfoAccessor(dbaccessor);
		long count = accessor.count(null);
		if(count > 100000 && !accessor.hasIndex()) {
			
			accessor.createIndex(null);
		}
		Constants.incre = new AtomicLong(count + 1);
		System.out.println("database inited, we have " + count + "items");
	 
		System.out.println("contextInitialized");
	}

}
