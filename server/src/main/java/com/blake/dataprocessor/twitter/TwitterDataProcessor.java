package com.blake.dataprocessor.twitter;

import java.util.HashMap;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import twitter4j.GeoLocation;

public class TwitterDataProcessor {

	public static HashMap<String, String> getProperties(JSONObject json) {

		HashMap<String,String> props = new HashMap<String,String>();
		
		props.put("id", String.valueOf( ParseUtil.getLong("id", json)) );
		props.put("createtimestamp", ParseUtil.getUnescapedString("created_at", json));
		props.put("geo", createGeoLocation( json) );
		props.put("place", ParseUtil.getRawString("place", json) );
		props.put("text", ParseUtil.getRawString("text", json) );
		
		JSONObject user = json.getJSONObject("user");
		props.put("name", ParseUtil.getRawString("name", user));
		props.put("time_zone", ParseUtil.getRawString("time_zone", user));
		props.put("userid", ParseUtil.getRawString("id", user));
		props.put("location", ParseUtil.getRawString("location", user));

		props.put("in_reply_to_screen_name", ParseUtil.getRawString("in_reply_to_screen_name", json)); 
		props.put("in_reply_to_user_id_str", ParseUtil.getRawString("in_reply_to_user_id_str", json)); 
		props.put("in_reply_to_user_id_str", ParseUtil.getRawString("in_reply_to_user_id_str", json)); 
		
		JSONObject retweetedStatus = json.getJSONObject("retweeted_status");
		if (!retweetedStatus.isNullObject() ) {
			
			JSONObject retweetedUser = retweetedStatus.getJSONObject("user");
			props.put("retweetedname", ParseUtil.getRawString("name", retweetedUser));
			props.put("retweetedtime_zone", ParseUtil.getRawString("time_zone", retweetedUser));
			props.put("retweeteduserid", ParseUtil.getRawString("id", retweetedUser));
			props.put("retweetedlocation", ParseUtil.getRawString("location", retweetedUser));
		}
		
		return props;
	}
	
    private static String createGeoLocation(JSONObject json) {

    	try {
    		
    		JSONObject jo = json.getJSONObject("coordinates");
            if (!jo.isNullObject() ) {
            	
                String coordinates = jo.getString("coordinates");
                coordinates = coordinates.substring(1, coordinates.length() - 1);
                String[] point = coordinates.split(",");
                return new GeoLocation(Double.parseDouble(point[1]),
                        Double.parseDouble(point[0])).toString();
            }
        } catch (JSONException jsone) {

        	return null;
        }
    	return null;
	}
	
}
