package com.blake.listener;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.blake.reptile.SpiderThread;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.InfoAccessor;

public class DatabaseCleaner implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	
	public void contextDestroyed(ServletContextEvent arg0) {

		scheduler.shutdownNow();
		System.out.println("contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		

//		 ServletContext servletContext = event.getServletContext();
//		 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//		 scheduler = Executors.newScheduledThreadPool(3);

		DBaccessor dbaccessor = new DBaccessor();
		InfoAccessor accessor = new InfoAccessor(dbaccessor);
		long count = accessor.count(null);
		System.out.println("database inited, we have " + count + "items");
		Constants.incre = new AtomicLong(count + 1);
	 
		Thread thread = new Thread(new SpiderThread());
		thread.start();
	 
		System.out.println("contextInitialized");
	}

}
