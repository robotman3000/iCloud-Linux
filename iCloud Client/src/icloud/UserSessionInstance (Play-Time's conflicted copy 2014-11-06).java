package icloud;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.HttpCookie;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.net.ssl.HttpsURLConnection;

public class UserSessionInstance {
	private String userID;
	private String UUID;
	private static final String clientBnum = "14G39";

	public UserSessionInstance(String username, String password, boolean extended_login) throws Exception {

		// TODO: Add ssl support
		boolean debugenabled = true;

		// Generate Required Strings
		String authString = "{\"apple_id\":" + "\"" + username + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"extended_login\":" + extended_login + "}";

		if (debugenabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + (UUID = CommonLogic.generateUUID()));
		}
		// Generate URI/URL
		URL httpurl = new URL("https", "setup.icloud.com", 443, "/setup/ws/1/login?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID);

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
		}

		HttpURLConnection httpconnection = (HttpURLConnection) httpurl.openConnection();
		httpconnection.setRequestMethod("POST");

		// TODO: remove magic header values
		httpconnection.setRequestProperty("Host", "setup.icloud.com");
		httpconnection.setRequestProperty("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		httpconnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpconnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		httpconnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		httpconnection.setRequestProperty("Referer", "https://www.icloud.com/");
		// httpconnection.setRequestProperty("Content-Type",
		// "text/plain; charset=UTF-8");
		httpconnection.setRequestProperty("Origin", "https://www.icloud.com");
		httpconnection.setRequestProperty("Connection", "keep-alive");
		// httpconnection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded;charset=" + "UTF-8");

		httpconnection.setDoOutput(true);

		// try (OutputStream output = httpconnection.getOutputStream()) {
		// output.write(authString.getBytes("UTF-8"));
		// }

		OutputStreamWriter httpout = new OutputStreamWriter(httpconnection.getOutputStream());
		httpout.write(authString);
		httpout.close();

		System.out.println(httpconnection.getDoInput());
		System.out.println(httpconnection.getHeaderFields());
		System.out.println(httpconnection.getInputStream());

		System.out.println("Yay");

		// Below is not used
		// httpuri.addParameter("clientBuildNumber", clientBnum);
		// httpuri.addParameter("clientId", UUID);

		// URI httprequest = httpuri.build();

		// Construct the request
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpmethod = new HttpPost();
		// httpmethod.setURI(httprequest);
		// httpmethod.setEntity(userLoginString);

		// TODO: remove magic header values
		httpmethod.addHeader("Host", "setup.icloud.com");
		httpmethod.addHeader("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		httpmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpmethod.addHeader("Accept-Language", "en-US,en;q=0.5");
		httpmethod.addHeader("Accept-Encoding", "gzip, deflate");
		httpmethod.addHeader("Referer", "https://www.icloud.com/");
		httpmethod.addHeader("Content-Type", "text/plain; charset=UTF-8");
		httpmethod.addHeader("Origin", "https://www.icloud.com");
		httpmethod.addHeader("Connection", "keep-alive");
		// Saved-Code: httpmethod.addHeader("Pragma", "no-cache");
		// Saved-Code: httpmethod.addHeader("Cache-Control", "no-cache");

		// Prepare for request execution
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// CloseableHttpResponse response = httpclient.execute(httpmethod);

		// Save & Print returned content
		// Header[] returnedHeaders = response.getAllHeaders();
		// HttpEntity returnedEntity = response.getEntity();
		// StatusLine responseCode = response.getStatusLine();

		/*
		 * if (debugenabled) { for (int index = 0; index <
		 * returnedHeaders.length; index++) {
		 * System.out.println(returnedHeaders[index]); }
		 * System.out.println(returnedEntity); System.out.println(responseCode);
		 * }
		 */

		/*
		 * BROKEN TODO: Fix below code String abc = ""; Gson gson = new
		 * GsonBuilder().setPrettyPrinting().create(); JsonParser jp = new
		 * JsonParser(); JsonElement je = jp.parse(returnedEntity); String
		 * prettyJsonString = gson.toJson(je);
		 * System.out.println(prettyJsonString);
		 */

		// Read returned content into class variables

		// Send cookie headers to cookie manager for safe keeping
		// /* BasicClientCookie[] Cookies =
		// */prepareCookies(response.getHeaders("Set-Cookie"));
		// UserSessionCookieManager userCookieManager = new
		// UserSessionCookieManager(Cookies);

	}

	public String getUserID() {
		return userID;
	}

	private void prepareCookies(Header[] Cookies) {
		List<HttpCookie> parsedCookies = null;
		for (int index = 0; index < Cookies.length; index++) {
			// Here we seperate the cookies values
			// parsedCookies = HttpCookie.parse(Cookies[index]);
		}
		for (int index = 0; index < parsedCookies.size(); index++) {
			System.out.println(parsedCookies.get(index).getName());
			boolean hasAttribute;
			boolean hasComment;
			boolean hasDomain;
			boolean hasExpiryDate;
			boolean hasPath;
			boolean hasSecure;
			boolean hasVersion;

			/*
			 * if (hasAttribute) { newCookies[index].setAttribute(name, value);
			 * } if (hasComment) { newCookies[index].setComment(comment); } if
			 * (hasDomain) { newCookies[index].setDomain(domain); } if
			 * (hasExpiryDate) { newCookies[index].setExpiryDate(expiryDate); }
			 * if (hasPath) { newCookies[index].setPath(path); } if (hasSecure)
			 * { newCookies[index].setSecure(secure); } if (hasVersion) {
			 * newCookies[index].setVersion(version); }
			 */
		}

	}

	public void closeUserSession(Header[] Cookies) throws Exception {

		ServerConnection connection = new ServerConnection();
		StringEntity userLoginString = new StringEntity("{}");

		HttpUriRequest httpmethod = RequestBuilder.create("GET").build();
		// Put headers here like: httpmethod.addHeader("Key", "Value");

		// TODO: remove magic header values
		httpmethod.addHeader("Host", "setup.icloud.com");
		httpmethod.addHeader("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		httpmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpmethod.addHeader("Accept-Language", "en-US,en;q=0.5");
		httpmethod.addHeader("Accept-Encoding", "gzip, deflate");
		httpmethod.addHeader("Referer", "https://www.icloud.com/");
		httpmethod.addHeader("Content-Type", "text/plain; charset=UTF-8");
		httpmethod.addHeader("Origin", "https://www.icloud.com");
		httpmethod.addHeader("Connection", "keep-alive");

		for (int index = 0; index < Cookies.length; index++) {
			httpmethod.addHeader(Cookies[index]);

			Pattern test = Pattern.compile("^X-APPLE-WEBAUTH-TOKEN*");
			Matcher fa = test.matcher(Cookies[index].getValue());

			if (fa.matches()) {
				System.out.println("Match!!");
				System.out.println(Cookies[index].getName() + Cookies[index].getValue());
			}
			System.out.println("No Match");
		}
		// httpmethod.addHeader("Pragma", "no-cache");
		// httpmethod.addHeader("Cache-Control", "no-cache");

		Header[] headerslist = httpmethod.getAllHeaders();

		String[] name = new String[3];
		String[] value = new String[3];

		name[0] = "clientBuildNumber";
		value[0] = clientBnum;
		name[1] = "clientId";
		value[1] = UUID;
		name[2] = "dsid";
		value[2] = "8084583249";

		connection.connect("POST", "https", "www.icloud.com", 443, "/setup/ws/1/logout", name, value, userLoginString, headerslist);

		System.out.println(connection.getResponseCode());
		// Parse returned content for user id. for now we cheat
		String responsetoparse = connection.getResponseBody();
		Header[] returnedHeaders = connection.getHeaders();
		for (int index = 0; index < returnedHeaders.length; index++) {
			System.out.println(returnedHeaders[index]);
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(responsetoparse);
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);

		// Make the compiler happy
		if (responsetoparse != null) {
		}

		System.out.println(connection.getResponseCode());
		UserSessionCookieManager test = new UserSessionCookieManager(null);
	}

	private class UserSessionCookieManager {

		public String test = "trwet";

		public UserSessionCookieManager(BasicClientCookie[] userCookies) {
			// Gather Cookie info from "Instance"
			// or login class uses setters to create the Cookie manager

		}
	}

}
