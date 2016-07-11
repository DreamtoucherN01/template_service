package com.blake.httprequest;

import java.util.List;

import net.sf.json.JSONObject;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import com.blake.httpmethod.HttpGetRequest;
import com.blake.util.Constants;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import junit.framework.TestCase;

@SuppressWarnings("deprecation")
public class TwitterSearch{
	
	
	/**  
     * Main entry of this application.  
     *  
     * @param args  
     */  
    public DBCollection collection;  
    public Mongo mongo;  
    public int count = 1;  
      
    public void LinkMongodb() throws Exception {  
          
        /*  
         * Link Mongodb   
         * build a data named FourS2  
         * build a collection named Foursquare  
         *    
         */  
        mongo = new Mongo("localhost", 27017);  
        DB db = mongo.getDB("TwitterMe");  
        collection = db.getCollection("DreamD");  
        System.out.println("Link Mongodb!");  
    }  
      
  
    public static void main(String[] args) throws TwitterException {{  
          
//        final TwitterSearch pr = new TwitterSearch();  
//          
//        try {  
//            pr.LinkMongodb();  
//        }  catch (Exception e) {  
//            e.printStackTrace();  
//        }    
          
        ConfigurationBuilder cb = new ConfigurationBuilder();  
        cb.setDebugEnabled(true)  
          .setOAuthConsumerKey(Constants.consumerKey)  
          .setOAuthConsumerSecret(Constants.consumerSecret)  
          .setOAuthAccessToken(Constants.accessToken)  
          .setOAuthAccessTokenSecret(Constants.accessTokenKey);  
        cb.setJSONStoreEnabled(true);  
        
//        TwitterFactory tf = new TwitterFactory(cb.build());  
//        Twitter twitter = tf.getInstance();  
//        Query query = new Query("夏洛特烦恼");
//        query.setLang("zh");
//        query.setCount(10);
//        QueryResult result = twitter.search(query);
//        for (Status status : result.getTweets()) {
//          System.out.println(status.getCreatedAt()+":"+status.getText());  
//        }     
        
        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());  
        TwitterStream twitterStream = tf.getInstance(); 
        
        StatusListener listener = new StatusListener() {  
            public void onStatus(Status status) {  
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());  
                //System.out.println(status);  
                String str = DataObjectFactory.getRawJSON(status);              
                try {  
                    //JSONObject nnstr = new JSONObject(newstr);  
//                    DBObject dbObject =(DBObject)JSON.parse(str); 
                    JSONObject jo = JSONObject.fromObject(str);
                    System.out.println(jo.get("text") + "\n");  
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
  
            public void onException(Exception ex) {  
                ex.printStackTrace();  
            }  
        };  
        twitterStream.addListener(listener);
//        String[] Track = {"加拿大西部", "艾伯塔省","McMurray","Fort", "Rachel Notley" , "FortMcMurray","china","America"};  
//        String[] Track = {"Obama"};  
        String[] Track = {"obama"};
          
        FilterQuery filter = new FilterQuery();  
        filter.track(Track); 
//        filter.language("EN");
        twitterStream.filter(filter);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
   
        
////        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());  
//        TwitterFactory tf = new TwitterFactory(cb.build());  
////        TwitterStream twitterStream = tf.getInstance();  
//        Twitter twitter = tf.getInstance();  
//          
//        Query q = new Query("BarackObama");  
//          
//        QueryResult result = twitter.search(q);  
//        List<Status> tweets = result.getTweets();  
//        for (Status tweet : tweets) {  
//              
//            //System.out.println(tweet);  
//            String str = DataObjectFactory.getRawJSON(tweet);
//            JSONObject jo = new JSONObject();
//            jo.put("type", "insert");
//            jo.put("data", str);
////            HttpGetRequest.sendPost(Constants.serverUrl, jo.toString());
////            System.out.println(str);  
//            try {  
//            	
//              JSONObject object = JSONObject.fromObject(str);
//              System.out.println(object.get("created_at"));
////              pr.collection.save(dbObject);  
//            } catch (Exception e) {  
//                e.printStackTrace();  
//            }   
//        }  
//        /*Paging paging = new Paging(15, 200);  
//        List<Status> statuses = twitter.getUserTimeline("BarackObama", paging);  
//          
//        Iterator it=statuses.iterator();  
//  
//        while(it.hasNext())  
//        {  
//          Status value=(Status)it.next();  
//          String str = DataObjectFactory.getRawJSON(value);  
//          try {  
//              DBObject dbObject =(DBObject)JSON.parse(str);  
//              pr.collection.save(dbObject);  
//          } catch (Exception e) {  
//            e.printStackTrace();  
//        }   
//          System.out.println(str);  
//        }  */  
////        pr.mongo.close();         
    }     
    }
}
