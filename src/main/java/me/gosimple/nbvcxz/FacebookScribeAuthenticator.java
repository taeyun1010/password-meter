package me.gosimple.nbvcxz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class FacebookScribeAuthenticator {
	 public static void openBrowser(SubGUIProgram subgui) {
//		 String accessToken = "EAACEdEose0cBAHcXy6AXNHAW71L6bOs1ktJntO6CVJdT2ckha6xFdW20UK9F0ZBVDZBON6KlpAGL19JoqCJExsBLNzQc7GoSK3k0DJBNSOhdMK4ZAAf0ZAlf1an9ucwEoj48OYiV6rOTegBb4it2HOfjicYvTbMdxQVDhyQdG94ZANeUeYAZAwpXHIYwPOuK0wU7FHHzeuZCDDn3HdkNbL3";
//	 
//		 @SuppressWarnings("deprecation")
//		 FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//		 
//		 User me = fbClient.fetchObject("me", User.class);
//		 
//		 System.out.println(me.getName());
		
		 String domain  = "http://www.google.com";
		 String appId = "216948179046382";
		 String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain;//+"&scope=user_about_me";
	 
		 System.out.println(authUrl);
		 
		 System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		 
		 WebDriver driver = new ChromeDriver();
		 driver.get(authUrl);
		 String accessToken;
		 while(true) {
			 
			 if(!driver.getCurrentUrl().contains("facebook.com")) {
				 String url = driver.getCurrentUrl();
				 accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				 
				 driver.quit();
				 
				 FacebookClient fbClient = new DefaultFacebookClient(accessToken);
				 User user = fbClient.fetchObject("me", User.class);
				 
						 
			 }
			 
		 }
	 
	 }
}
