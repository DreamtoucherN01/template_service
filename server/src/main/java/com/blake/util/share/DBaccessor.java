package com.blake.util.share;

import javax.servlet.ServletContext;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mongodb.DB;

public class DBaccessor {
	
	private MongoTemplate mongoTemplate;
	private DB db;

	public DBaccessor() {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	mongoTemplate = wac.getBean("mongoTemplate", MongoTemplate.class);  
    	db = mongoTemplate.getDb();
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

}
