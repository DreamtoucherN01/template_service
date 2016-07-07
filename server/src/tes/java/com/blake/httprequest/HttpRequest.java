package com.blake.httprequest;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.blake.httpmethod.HttpGetRequest;
import com.blake.util.Constants;

import junit.framework.TestCase;

public class HttpRequest extends TestCase {

	
	public void testRequest(){
		
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
//		System.out.println(HttpGetRequest.sendGet("https://www.google.com/", "gfe_rd=cr&ei=Tal7V5XdCY-7ygXO4aHwDw&gws_rd=ssl"));
//		String data = HttpGetRequest.sendPost(Constants.oauthUrl, "");
//		System.out.println(data);
//		System.out.println(HttpGetRequest.sendGet(Constants.twitterUrl, Constants.twitterParams));
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(Constants.consumerKey); // 设置consumerKey
        configurationBuilder.setOAuthConsumerSecret(Constants.consumerSecret); // 设置consumerSecret
        configurationBuilder.setJSONStoreEnabled(true);
        Configuration configuration = configurationBuilder.build();
	
	    Twitter twitter = new TwitterFactory(configuration).getInstance();
	    RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	    System.out.println("Got access token.");
        System.out.println("Request token: " + requestToken.getToken());
        System.out.println("Request token secret: " + requestToken.getTokenSecret());
        AccessToken accessToken = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            try {
            	 //调用浏览器访问Twiiter的授权页面，帐号登录成功也就是授权成功后将会跳转回到申请的APP中的Redirect_URI地址，并携带了request_token  
                Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            } catch (UnsupportedOperationException ignore) {
            } catch (IOException ignore) {
            } catch (URISyntaxException e) {
                throw new AssertionError(e);
            }
          //输入浏览器跳转时携带的code参数（应该将Redirect_URI设置成本系统中的一个Servlet的地址，并且下面的代码放到Servlet中来chǔ理授权信息)  
            System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
            String pin = null;
			try {
				pin = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken(requestToken);
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        System.out.println("Got access token.");
        System.out.println("Access token: " + accessToken.getToken());
        System.out.println("Access token secret: " + accessToken.getTokenSecret());

        try {
            prop.setProperty("oauth.accessToken", accessToken.getToken());
            prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
            prop.store(os, "twitter4j.properties");
            os.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignore) {
                }
            }
        }
        System.exit(0);
	}
	
}
