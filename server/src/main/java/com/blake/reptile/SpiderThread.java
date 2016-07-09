package com.blake.reptile;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.blake.util.Constants;


public class SpiderThread implements Runnable{

	public void run() {

		 ConfigurationBuilder cb = new ConfigurationBuilder();  
		 cb.setDebugEnabled(true)  
          	.setOAuthConsumerKey(Constants.consumerKey)  
          	.setOAuthConsumerSecret(Constants.consumerSecret)  
          	.setOAuthAccessToken(Constants.accessToken)  
          	.setOAuthAccessTokenSecret(Constants.accessTokenKey);  
		 cb.setJSONStoreEnabled(true);  
		 TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());  
		 TwitterStream twitterStream = tf.getInstance(); 
		 twitterStream.addListener(new StreamStatusListener());
		 String[] Track = {"加拿大西部", "艾伯塔省","McMurray","Fort", "Rachel Notley" , "FortMcMurray"};  
		 FilterQuery filter = new FilterQuery();  
		 filter.track(Track);  
		 twitterStream.filter(filter); 
		 System.out.println("listener inited");
		
	}

}
