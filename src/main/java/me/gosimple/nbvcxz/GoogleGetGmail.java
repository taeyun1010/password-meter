package me.gosimple.nbvcxz;

import java.util.Random;
import java.util.Scanner;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.awt.Desktop;
import java.awt.Label;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleGetGmail {

	private static final String NETWORK_NAME = "G+";
	// private static final String PROTECTED_RESOURCE_URL =
	// "https://www.googleapis.com/plus/v1/people/me";
	// private static final String PROTECTED_RESOURCE_URL =
	// "https://www.googleapis.com/auth/gmail.readonly";
	// private static final String PROTECTED_RESOURCE_URL =
	// "https://www.googleapis.com/gmail/v1/users";
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/auth/gmail.readonly";
	private static final String SCOPE = "https://www.googleapis.com/auth/gmail.readonly";
	private static final OAuth20Service service = new ServiceBuilder(
			"842780042103-6o7a5f53rotqtspuqtvmotc6640lv3c0.apps.googleusercontent.com")
					.apiSecret("F-2Y7dMbMsRxrdoqzJwX7OIy").scope("https://www.googleapis.com/auth/gmail.readonly") // replace
																													// with
																													// desired
																													// scope
					// .state(secretState)
					// .callback("https://www.google.com")
					.build(GoogleApi20.instance());

	private GoogleGetGmail() {
	}

	public static void openBrowser(SubGUIProgram subgui) throws IOException, InterruptedException, ExecutionException {
		// Replace these with your client id and secret

		// final String secretState = "secret" + new Random().nextInt(999_999);
		// final OAuth20Service service = new
		// ServiceBuilder("842780042103-6o7a5f53rotqtspuqtvmotc6640lv3c0.apps.googleusercontent.com")
		// .apiSecret("F-2Y7dMbMsRxrdoqzJwX7OIy")
		// .scope("https://www.googleapis.com/auth/gmail.readonly") // replace with
		// desired scope
		// //.state(secretState)
		// //.callback("https://www.google.com")
		// .build(GoogleApi20.instance());
		final Scanner in = new Scanner(System.in, "UTF-8");

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();

		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		// pass access_type=offline to get refresh token
		// https://developers.google.com/identity/protocols/OAuth2WebServer#preparing-to-start-the-oauth-20-flow
		final Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("access_type", "offline");
		// force to reget refresh token (if usera are asked not the first time)
		additionalParams.put("prompt", "consent");
		// final String authorizationUrl =
		// service.getAuthorizationUrl(additionalParams);
		// System.out.println("Got the Authorization URL!");
		// System.out.println("Now go and authorize ScribeJava here:");
		// System.out.println(authorizationUrl);
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(service.getAuthorizationUrl(additionalParams)));

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getGmail(final String verifier, SubGUIProgram subgui) throws IOException, InterruptedException, ExecutionException {
		// System.out.println("And paste the authorization code here");
		// System.out.print(">>");
		// final String code = in.nextLine();
		//// System.out.println();
		// final Scanner in = new Scanner(System.in, "UTF-8");

		final String code = verifier;

		// System.out.println("And paste the state from server here. We have set
		// 'secretState'='" + secretState + "'.");
		// System.out.print(">>");
		// final String value = in.nextLine();
		// if (secretState.equals(value)) {
		// System.out.println("State value does match!");
		// } else {
		// System.out.println("Ooops, state value does not match!");
		// System.out.println("Expected = " + secretState);
		// System.out.println("Got = " + value);
		// System.out.println();
		// }

		// Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		OAuth2AccessToken accessToken = service.getAccessToken(code);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious the raw answer looks like this: " + accessToken.getRawResponse() + "')");

		System.out.println("Refreshing the Access Token...");
		accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
		System.out.println("Refreshed the Access Token!");
		System.out.println("(if your curious the raw answer looks like this: " + accessToken.getRawResponse() + "')");
		System.out.println();

		// Now let's go and ask for a protected resource!
		System.out.println("Now we're going to access a protected resource...");

		// System.out.println("Paste fieldnames to fetch (leave empty to get profile,
		// 'exit' to stop example)");
		// System.out.print(">>");
		// final String query = in.nextLine();
		// System.out.println();

		String requestUrl = "https://www.googleapis.com/gmail/v1/users/me/messages";
		// if ("exit".equals(query)) {
		// break;
		// } else if (query == null || query.isEmpty()) {
		// requestUrl = PROTECTED_RESOURCE_URL;
		// } else {
		// //requestUrl = PROTECTED_RESOURCE_URL + "?fields=" + query;
		// //requestUrl = PROTECTED_RESOURCE_URL + query;
		// requestUrl = query;
		// }

		OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl);
		service.signRequest(accessToken, request);
		Response response = service.execute(request);
		String responseBody = response.getBody();
		JSONObject responseBodyinJSON = new JSONObject(responseBody);
		System.out.println(responseBodyinJSON);
		JSONArray emails = responseBodyinJSON.getJSONArray("messages");
		System.out.println(emails);
		
		String allcontent = "";
		
		subgui.add(new Label("found a total of " + emails.length() + " gmails"));
		subgui.setVisible(true);
		Label gmailcount = new Label("processing 0th gmail...");
		subgui.add(gmailcount);
		subgui.setVisible(true);
		for (int i = 0; i < emails.length(); i++) {
			gmailcount.setText("processing " + (i+1) + "th gmail...");
			JSONObject email = emails.getJSONObject(i);
			String id = email.getString("id");
			requestUrl = "https://www.googleapis.com/gmail/v1/users/me/messages/" + id;
			request = new OAuthRequest(Verb.GET, requestUrl);
			service.signRequest(accessToken, request);
			response = service.execute(request);
			responseBody = response.getBody();
			//System.out.println(responseBody);
			responseBodyinJSON = new JSONObject(responseBody);
			String content = responseBodyinJSON.getString("snippet");
			//System.out.println(content);
			allcontent = allcontent + content;
		}

		// System.out.println();
		// System.out.println(response.getCode());
		// System.out.println(response.getBody());

		//System.out.println();
		return allcontent;

	}
}
