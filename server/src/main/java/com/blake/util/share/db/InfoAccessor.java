package com.blake.util.share.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.blake.mongoutil.DBCollectionCommander;
import com.blake.response.UnifiedResponse;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.dao.IInfoDao;
import com.blake.util.share.db.dao.Info;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class InfoAccessor implements IInfoDao{

	private DBaccessor dbaccessor;
    private DBCollection twitter;

	public InfoAccessor(DBaccessor dbaccessor) {

		this.dbaccessor = dbaccessor;
	}

	public void insert(Info info) {
		
		twitter = DBCollectionCommander.getDBCollection(dbaccessor, info.getKey());
		System.out.println("insert into " + twitter.getName());
		Gson gson=new Gson();
	    DBObject dbObject = (DBObject) JSON.parse(gson.toJson(info));
	    twitter.insert(dbObject);
	}

	public void insertAll(List<Info> users) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Info criteriaInfo) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteAll() {

		dbaccessor.getDb().dropDatabase();
	    return true;
	}

	public void updateById(Info user) {
		// TODO Auto-generated method stub
		
	}

	public void update(Info criteriaInfo, Info user) {
		// TODO Auto-generated method stub
		
	}

	public Info findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Info> findAll() {
		
		List<Info> infolist = new ArrayList<Info>();
		for(int i = 0;i < Constants.collectionNumber; i++) {
			
			twitter = dbaccessor.getDb().getCollection("twitter_" + i);
			DBCursor cur = twitter.find();
		    while (cur.hasNext()) {
		    	
		    	Gson gson=new Gson();
		    	Info info = gson.fromJson( gson.toJson(cur.next()), Info.class);
		    	infolist.add(info);
		    }
		}
		return infolist;
	}

	public List<Info> find(String criteriaInfo, int skip, int limit) {
		
		if(criteriaInfo == null) {
			
			return findAll();
		}
		List<Info> infolist = new ArrayList<Info>();

		twitter = DBCollectionCommander.getDBCollection(dbaccessor, criteriaInfo);
		System.out.println("find in " + twitter.getName());
		DBCursor cur = twitter.find(new BasicDBObject("key", criteriaInfo));
		
	    while (cur.hasNext()) {
	    	
	    	Gson gson=new Gson();
	    	Info info = gson.fromJson( gson.toJson(cur.next()), Info.class);
	    	if(null != info.getKey() && info.getKey().contains(criteriaInfo)) {
	    		
	    		infolist.add(info);
	    	}
	    }
	    
		System.out.println(infolist);
		return infolist;
	}
	
	public List<Info> fuzzyFind(String criteriaInfo, int skip, int limit) {
		
		if(criteriaInfo == null) {
			
			return null;
		}
		List<Info> infolist = new ArrayList<Info>();

		for(int i = 0;i < Constants.collectionNumber; i++) {
			
			twitter = dbaccessor.getDb().getCollection("twitter_" + i);
			System.out.println("query " + twitter.getName());
			Pattern john = Pattern.compile(criteriaInfo,Pattern.CASE_INSENSITIVE);
			DBObject query = new BasicDBObject("key", john);
			DBCursor cur = twitter.find(query);
			
		    while (cur.hasNext()) {
		    	
		    	Gson gson=new Gson();
		    	Info info = gson.fromJson( gson.toJson(cur.next()), Info.class);
		    	if(null != info.getKey() && info.getKey().contains(criteriaInfo)) {
		    		
		    		infolist.add(info);
		    	}
		    	if(infolist.size() > Constants.queryListMaxSize) {
		    		
		    		infolist = null;
		    		return null;
		    	}
		    }
	    }
		return infolist;
	}

	public Info findAndModify(Info criteriaInfo, Info updateInfo) {
		return null;
	}

	public Info findAndRemove(Info criteriaInfo) {
		return null;
	}

	public long count(Info criteriaInfo) {
		
		int count = 0;
		for(int i = 0;i < Constants.collectionNumber; i++) {
			
			twitter = dbaccessor.getDb().getCollection("twitter_" + i);
			count += twitter.count();
		}
		return count;
	}
	
	public void createIndex(String key) {

		for(int i = 0;i < Constants.collectionNumber; i++) {
			
			twitter = dbaccessor.getDb().getCollection("twitter_" + i);
			twitter.createIndex(new BasicDBObject("key", 1));
		}
	}
	
	@SuppressWarnings("static-access")
	public boolean hasIndex() {

		for(int i = 0;i < Constants.collectionNumber; i++) {
			
			twitter = dbaccessor.getDb().getCollection("twitter_" + i);
			if(twitter.genIndexName(new BasicDBObject("key", 1)) != null){
				
				return true;
			}
		}
		return false;
	}
	
    public void destory() {
    	
    	twitter = null;
        System.gc();
    }

}
