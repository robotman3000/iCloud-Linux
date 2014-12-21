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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserSessionInstance {
	private String username;
	private String password;
	private boolean extended_login;

	private String userID;
	private String UUID;
	private static final String clientBnum = "14H40";
	private List<HttpCookie> cookies;

	public UserSessionInstance(String username, String password, boolean extended_login) {
		this.username = username;
		this.password = password;
		this.extended_login = extended_login;
	}

	public void connect(boolean test) throws Exception {
		boolean debugenabled = true;

		// Generate Auth String
		String authString = "{\"apple_id\":" + "\"" + username + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"extended_login\":" + extended_login + "}";

		// Debug Output
		if (debugenabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + (UUID = CommonLogic.generateUUID()));
			UUID = UUID.toUpperCase();
			CommonLogic.splitOut();
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
			CommonLogic.splitOut();
		}

		// Set Headers
		// TODO: Remove Magic Values
		httpconnection.setRequestMethod("POST");
		httpconnection.setRequestProperty("Host", "setup.icloud.com");
		httpconnection.setRequestProperty("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		httpconnection.setRequestProperty("Accept", "text/json");
		httpconnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		// httpconnection.setRequestProperty("Accept-Encoding", "deflate");
		httpconnection.setRequestProperty("Referer", "https://www.icloud.com/");
		httpconnection.setRequestProperty("Content-Type", "text/json; charset=UTF-8");
		httpconnection.setRequestProperty("Origin", "https://www.icloud.com"); // //
		httpconnection.setRequestProperty("Connection", "keep-alive");
		httpconnection.setRequestProperty("Cache-Control", "no-cache"); // HTTP
		httpconnection.setRequestProperty("Pragma", "no-cache"); // HTTP 1.0.

		if (debugenabled) {
			System.out.println(httpconnection.getRequestMethod());
			System.out.println(httpconnection.getRequestProperties());
			CommonLogic.splitOut();
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

		if (debugenabled) {
			System.out.println("Input: " + httpconnection.getDoInput() + "\n" + "Output: " + httpconnection.getDoOutput());
			System.out.println("URL: " + httpconnection.getURL() + "\n" + "Response Message: " + httpconnection.getResponseMessage() + "\n" + "Returned Headers: " + httpconnection.getHeaderFields());
			//System.out.println("Error Stream: " + convertStreamToString(httpconnection.getErrorStream()));
			CommonLogic.splitOut();

		}

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
		}

		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();

		if (debugenabled) {
			for (HttpCookie cookie : cookies) {
				System.out.println("CookieHandler retrieved cookie: " + cookie);
			}
		}

	}

	public void connect() throws Exception {

		Map<String, String> headersMap = new HashMap<String, String>();

		String authString = "{\"apple_id\":" + "\"" + username + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"extended_login\":" + extended_login + "}";
		boolean debugenabled = true;
		// Debug Output
		if (debugenabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + (UUID = CommonLogic.generateUUID()));
			UUID = UUID.toUpperCase();
			CommonLogic.splitOut();
		}

		URL httpUrl = new URL("https://setup.icloud.com:443/setup/ws/1/login?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID);

		headersMap.put("Origin", "https://www.icloud.com");

		ServerConnection conn = new ServerConnection(true);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setPayload(authString);
		conn.connect();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		cookies = conn.getResponseCookies();

		if (debugenabled) {
			for (HttpCookie cookie : cookies) {
				System.out.println("CookieHandler retrieved cookie: " + cookie);
			}
		}

	}

	public void disconnect() throws Exception {
		boolean debugenabled = true;
		
		Map<String, String> headersMap = new HashMap<String, String>();
		String valCookie = "";
		String var = "X-APPLE-WEBAUTH-VALIDATE";

		Iterator<HttpCookie> iterator = cookies.iterator();
		while (iterator.hasNext()) {
			HttpCookie key = iterator.next();
			String name = key.getName();
			String value = key.getValue();
			//headersMap.put("Cookie: " + name, value);
			int number = name.compareTo(var);
			if (number == 0) {
				valCookie = key.getValue();
				//System.out.println(valCookie);

			}
		}

		headersMap.put("Origin", "https://www.icloud.com");
		
		ServerConnection conn = new ServerConnection(true);

		URL httpUrl = new URL("https://setup.icloud.com:443/setup/ws/1/logout?" + "clientBuildNumber=" + clientBnum + "&clientId=" + UUID + "&token=" + valCookie + "&dsid=" + "8084583249");
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(cookies);
		conn.setPayload("{}");
		conn.connect();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}
		
	}

	//public Map<> getUserProps() {

	//	return null;
	//}

	public String getUserID() {
		return userID;
	}

	// add other gets
	// add private manager creators

	public Map<String, BaseManager> getAllManagers() {

		return null;
	}

	public BaseManager getContactManager() {

		return null;
	}

	public BaseManager getCalendarManager() {

		return null;
	}

	public BaseManager getMailManager() {

		return null;
	}

	public BaseManager getNotesManager() {

		return null;
	}

	public BaseManager getReminderManager() {

		return null;
	}

}
