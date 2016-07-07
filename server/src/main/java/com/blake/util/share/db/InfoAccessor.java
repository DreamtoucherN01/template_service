package com.blake.util.share.db;

import java.util.ArrayList;
import java.util.List;

import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.dao.IInfoDao;
import com.blake.util.share.db.dao.Info;
import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class InfoAccessor implements IInfoDao{

	private DB db;
    private DBCollection twitter;

	public InfoAccessor(DBaccessor dbaccessor) {

		this.db = dbaccessor.getDb();
		twitter = db.getCollection("twitter");
	}

	public void insert(Info info) {
		
		Gson gson=new Gson();
	    DBObject dbObject = (DBObject) JSON.parse(gson.toJson(info));
		twitter = db.getCollection("twitter");
		if(twitter == null){
			
			db.createCollection("twitter", dbObject);
		} else {
			
			twitter.insert(dbObject);
		}
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

		if(twitter == null) {
			
			return false;
		}
		DBCursor cur = twitter.find();
	    while (cur.hasNext()) {
	    	
	        twitter.remove(cur.next());
	    }
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
		
		if(twitter == null) {
			
			return null;
		}
		List<Info> infolist = new ArrayList<Info>();
		DBCursor cur = twitter.find();
	    while (cur.hasNext()) {
	    	
	    	Gson gson=new Gson();
	    	Info info = gson.fromJson( gson.toJson(cur.next()), Info.class);
	    	infolist.add(info);
	    }
		return infolist;
	}

	public List<Info> find(Info criteriaInfo, int skip, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public Info findAndModify(Info criteriaInfo, Info updateInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Info findAndRemove(Info criteriaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count(Info criteriaInfo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
    public void destory() {
    	
    	twitter = null;
        System.gc();
    }

}
