package me.gosimple.nbvcxz;

import java.util.Scanner;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.Label;
import java.awt.TextField;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public final class TwitterGetAllTweets {

//    private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	
	
	//This one returns a tweet with given id, id is located in URL
	//private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/statuses/lookup.json?id=953222461201399808";
	
	//this one gets liked tweets 
	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";

	private final static OAuth10aService service = new ServiceBuilder("qTTeRwRoHMxLO2HroMUbdnPZO")
            .apiSecret("QsPi9IxbnXaBgJH3tifMGQIDkUK2iJNA82lQzNMRy4fOjPmOlL")
            .build(TwitterApi.instance());
	
	private static OAuth1RequestToken requestToken;
	
    private TwitterGetAllTweets() {
    }

    public static void openBrowser(SubGUIProgram subgui) throws IOException, InterruptedException, ExecutionException {
//        final OAuth10aService service = new ServiceBuilder("qTTeRwRoHMxLO2HroMUbdnPZO")
//                .apiSecret("QsPi9IxbnXaBgJH3tifMGQIDkUK2iJNA82lQzNMRy4fOjPmOlL")
//                .build(TwitterApi.instance());
        //final Scanner in = new Scanner(System.in);

        System.out.println("=== Twitter's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        
        //
//        try {
//            URL myURL = new URL(service.getAuthorizationUrl(requestToken));
//            URLConnection myURLConnection = myURL.openConnection();
//            myURLConnection.connect();
//        } 
//        catch (MalformedURLException e) { 
//            System.err.println("MalformedURLException");
//        } 
//        catch (IOException e) {   
//        	 System.err.println("openconnection() failed");
//        }
        
        if (Desktop.isDesktopSupported()) {
            try {
				Desktop.getDesktop().browse(new URI(service.getAuthorizationUrl(requestToken)));
			
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //
       
    }

	public static String getAllTweets(final String verifier) throws IOException, InterruptedException, ExecutionException {
		System.out.println("And paste the verifier here");
        System.out.print(">>");
        //final String oauthVerifier = in.nextLine();
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious the raw answer looks like this: " + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("That's it man! Go and build something awesome with ScribeJava! :)");
        
        return response.getBody();
	}
}