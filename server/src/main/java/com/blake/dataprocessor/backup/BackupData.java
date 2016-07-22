package com.blake.dataprocessor.backup;

import com.blake.util.share.DBaccessor;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BackupData {
	
	/**
	 * backup data in mongodb
	 */
	
	public static void backupData(DBaccessor mongoDBAccessor, int num) {
		
		DBCollection currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + num);
		DBCollection currentCollectionBak = mongoDBAccessor.getDb().getCollection("twitter_" + num + "_bak");
		
		//first transfer data
		DBCursor cur = currentCollection.find();
	    while (cur.hasNext()) {
	    	
	    	DBObject next = cur.next();
	    	currentCollectionBak.insert(next);
	    }
	    
	    //delete old data
	    currentCollection.drop();
	}

}
