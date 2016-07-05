package com.blake.util.share.db;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.blake.util.share.db.dao.User;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class UserAccessor{

    
	private DB db;
    private DBCollection twitter;
	protected MongoTemplate mongoTemplate;  
	DBObject data = null;
	
    public UserAccessor(String dbObject) {

    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	mongoTemplate = wac.getBean("mongoTemplate", MongoTemplate.class);  
//    	this.data =  dbObject;
    	db = mongoTemplate.getDb();
    }

	public void init() {
		
		User u1=new User();
	    u1.setAge(20);
	    u1.setName("ssss");
	    Gson gson=new Gson();
	    //转换成json字符串，再转换成DBObject对象
	    DBObject dbObject = (DBObject) JSON.parse(gson.toJson(u1));
	    twitter = db.getCollection("twitter");
		if(twitter == null){
			
			db.createCollection("twitter", dbObject);
		} else {
			
			twitter.insert(dbObject);
			
		}
		//查询下数据，看看是否添加成功
    }
    
    public void destory() {
    	
    	twitter = null;
        System.gc();
    }

}
