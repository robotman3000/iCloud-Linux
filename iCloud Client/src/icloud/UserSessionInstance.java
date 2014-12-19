package icloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.HttpCookie;
import java.util.List;

public class UserSessionInstance {
	private String userID;
	private String UUID;
	private static final String clientBnum = "14H40";

	public UserSessionInstance(String username, String password, boolean extended_login) throws Exception {

		boolean debugenabled = true;

		// Generate Auth String
		String authString = "{\"apple_id\":" + "\"" + username + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"extended_login\":" + extended_login + "}";

		// Debug Output
		if (debugenabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + (UUID = CommonLogic.generateUUID()));
			UUID = UUID.toUpperCase();
			splitOut();
		}

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		// Create the cookie manager
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);

		URL httpurl = new URL("https://setup.icloud.com:443/setup/ws/1/login?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID);
		HttpURLConnection httpconnection = (HttpURLConnection) httpurl.openConnection();
		// httpurl = new URL("https://setup.icloud.com:443/setup/ws/1/login");

		// Debug Output
		if (debugenabled) {
			System.out.println("URL is: " + httpurl);
			System.out.println("URL Details: {");
			System.out.println("Protocol: " + httpurl.getProtocol());
			System.out.println("Host: " + httpurl.getHost());
			System.out.println("Port: " + httpurl.getPort());
			System.out.println("Path: " + httpurl.getPath());
			System.out.println("File: " + httpurl.getFile());
			System.out.println("Query: " + httpurl.getQuery());
			System.out.println("}");
			splitOut();
		}

		// Set Headers
		// TODO: Remove Magic Values
		httpconnection.setRequestProperty("Host", "setup.icloud.com");
		httpconnection.setRequestProperty("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		httpconnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpconnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		httpconnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		httpconnection.setRequestProperty("Referer", "https://www.icloud.com/");
		httpconnection.setRequestProperty("Content-Type", "text/json; charset=UTF-8");
		httpconnection.setRequestProperty("Origin", "https://www.icloud.com"); // //
		httpconnection.setRequestProperty("Connection", "keep-alive");
		httpconnection.setRequestProperty("Cache-Control", "no-cache"); // HTTP
		httpconnection.setRequestProperty("Pragma", "no-cache"); // HTTP 1.0.

		if (debugenabled) {
			System.out.println(httpconnection.getRequestMethod());
			System.out.println(httpconnection.getRequestProperties());
			splitOut();
		}

		httpconnection.setDoInput(true);
		httpconnection.setDoOutput(true);
		httpconnection.setUseCaches(false);

		// Send Post Data
		// Change to datastreamwriter
		DataOutputStream dos = new DataOutputStream(httpconnection.getOutputStream());
		dos.writeBytes(authString);
		dos.flush();
		dos.close();

		// Read Input
		InputStream is = httpconnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);

		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		String result = sb.toString();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(result);
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
			splitOut();
			System.out.println("Input: " + httpconnection.getDoInput() + "\n" + "Output: " + httpconnection.getDoOutput());
			System.out.println("URL: " + httpconnection.getURL() + "\n" + "Response Message: " + httpconnection.getResponseMessage() + "\n" + "Returned Headers: " + httpconnection.getHeaderFields());
			System.out.println("Error Stream: " + convertStreamToString(httpconnection.getErrorStream()));
		}

		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();
		
		if (debugenabled) {
			for (HttpCookie cookie : cookies) {
				System.out.println("CookieHandler retrieved cookie: " + cookie);
			}
		}
		
		
		//######################################################################################################
		
		// Disconnect
		
		
	}

	public String getUserID() {
		return userID;
	}

	public String convertStreamToString(InputStream is) throws IOException {
		// Copy Pasted Code

		//
		// To convert the InputStream to String we use the
		// Reader.read(char[] buffer) method. We iterate until the
		// Reader return -1 which means there's no more data to
		// read. We use the StringWriter class to produce the string.
		//
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public void splitOut() {
		System.out.println("================================================================================");
	}
}
