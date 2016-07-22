package com.blake.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.blake.decoupler.DecouplerServiceImpl;
import com.blake.neo4j.Neo4jService;
import com.blake.neo4j.Neo4jServiceImp;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.InfoAccessor;

public class BackgroundListener implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	Logger logger = Logger.getLogger(BackgroundListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {

		scheduler.shutdownNow();
		logger.info("contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		

		event.getServletContext();
		scheduler = Executors.newScheduledThreadPool(3);

		DBaccessor dbaccessor = new DBaccessor();
		InfoAccessor accessor = new InfoAccessor(dbaccessor);
		long count = accessor.count(null);
		if(count > 100000 && !accessor.hasIndex()) {
			
			accessor.createIndex(null);
		}
		Constants.incre = new AtomicLong(count + 1);
		
		logger.info("database inited, we have " + count + "items");
	 
		Neo4jService neo = new Neo4jServiceImp();
		scheduler.scheduleAtFixedRate(new DecouplerServiceImpl(neo, dbaccessor), 1, 1, TimeUnit.MINUTES);
		logger.info("contextInitialized");
	}

}

