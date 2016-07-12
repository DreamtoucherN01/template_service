package com.blake.mongoutil;

import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.mongodb.DBCollection;

public class DBCollectionCommander {
	
	public static DBCollection getDBCollection(DBaccessor dbaccessor, String key){
		
		int hashcode = key.hashCode();
		return dbaccessor.getDb().getCollection("twitter_" + Math.abs(hashcode%Constants.collectionNumber));
	}

}
