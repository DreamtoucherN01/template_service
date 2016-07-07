package com.blake.reptile;

import com.blake.httpmethod.HttpGetRequest;
import com.blake.util.Constants;

public class SpiderThread implements Runnable{

	public void run() {

		System.out.println(HttpGetRequest.sendGet(Constants.twitterUrl, Constants.twitterParams));
	}

}
