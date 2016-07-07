package com.blake.reptile;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.blake.util.Constants;


public class TwitterRecognition {
	
	public static String getAuthCertified() {
		
		byte[] key = Base64.encodeBase64("xvz1evFS4wEEPTGEFPHBog:L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg".getBytes());
		try {
			System.out.println(new String(key, "GB2312"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String decodeString = null;
		try {
			
			System.out.println(Constants.consumerKey+ ":" + Constants.consumerSecret);
			decodeString = new String(Base64.encodeBase64( (Constants.consumerKey+ ":" + Constants.consumerSecret).getBytes()),"GB2312");
			System.out.println(decodeString);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		
		return decodeString;
	}

}
