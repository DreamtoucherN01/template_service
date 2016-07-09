package com.blake.reptile;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.json.DataObjectFactory;

@SuppressWarnings("deprecation")
public class StreamStatusListener implements StatusListener{

	public void onException(Exception ex) {
		
		ex.printStackTrace();
	}

	public void onStatus(Status status) {

		String str = DataObjectFactory.getRawJSON(status);              
        try {  
            DBObject dbObject =(DBObject)JSON.parse(str);  
            System.out.println(dbObject);  
        }  catch (Exception e) {  
            e.printStackTrace();  
        }   
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId()); 
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses); 
	}

	public void onScrubGeo(long userId, long upToStatusId) {

		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}

	public void onStallWarning(StallWarning warning) {

		System.out.println("Got stall warning:" + warning);  
	}

}
