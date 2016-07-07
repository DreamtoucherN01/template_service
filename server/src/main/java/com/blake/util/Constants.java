package com.blake.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Constants {

	public static String DBCollectionName = "twitter";
	
	public static AtomicInteger incre = new AtomicInteger(0);
	
	public static String consumerKey = "bLx4Xr9gh4jjFHboL1KzSuvgU";
	
	public static String consumerSecret = "15DZzC8HJtfW0T523Lo7G16Ow0ONtDWxirHdY4GWEtG5udeRtl";
	
	public static String oauthUrl = "https://api.twitter.com/oauth2/token";
	
	public static String twitterUrl = "https://api.twitter.com/1.1/direct_messages/sent.json"; 
	
	public static String twitterParams = "count=2&since_id=240247560269340670";
}
