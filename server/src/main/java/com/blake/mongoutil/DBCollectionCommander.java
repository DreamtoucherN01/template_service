package com.blake.mongoutil;

import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.mongodb.DBCollection;

public class DBCollectionCommander {
	
	private static int currentNum = -1;
	
	public static DBCollection getDBCollection(DBaccessor dbaccessor, String key){
		
		if(currentNum == -1) {
			
			currentNum = getCurrentNum(dbaccessor);
		}
		return dbaccessor.getDb().getCollection("twitter_" + currentNum);
	}

	private static int getCurrentNum(DBaccessor dbaccessor) {

		for(int i= 0; i< Constants.collectionNumber; i++) {
			
			long num = dbaccessor.getDb().getCollection("twitter_" + i).count() ;
			if(num < Constants.maxItemNumberPerCollection && num != 0) {
				
				return i;
			}
		}
		dbaccessor.getDb().getCollection("twitter_0").drop();
		return 0;
	}

}
