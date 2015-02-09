package common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerConnection {

	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	private boolean isExpended = false;

	private URL serverUrl = null;
	private Map<String, String> requestHeaders = null;
	private String payload;
	private String requestMethod = null;
	private List<HttpCookie> requestCookies = null;

	private String responseMessage = null;
	private Map<String, List<String>> responseHeaders = null;
	private List<HttpCookie> responseCookies = null;
	private int responseCode = -1;
	private InputStream responseData = null;
	private String responseErrorStream = null;

	private boolean debugEnabled = false;

	public ServerConnection() {

	}

	public ServerConnection(boolean enableDebugLogging) {
		setDebugenabled(enableDebugLogging);
	}

	public int connect() throws Exception /* throws Exception */{
		isExpended = true;
		boolean usePayload = false;

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		if (debugEnabled) {
			CommonLogic.splitOut();
			System.out.println("Start connection details");
			CommonLogic.splitOut();
		}

		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
		CookieStore cookieJar = manager.getCookieStore();

		URL httpurl;
		HttpURLConnection httpconnection;

		if (serverUrl == null) {
			System.err.println("Server URL can't be null" + "\n"
					+ "Set with setServerUrl(URL url);");
			return -1;
		} else {
			httpurl = getServerUrl();
			httpconnection = (HttpURLConnection) httpurl.openConnection();

			// Debug Output
			if (debugEnabled) {
				System.out.println("URL is: " + httpurl);
				System.out.println("URL Details: {");
				System.out.println("	Protocol: " + httpurl.getProtocol());
				System.out.println("	Host: " + httpurl.getHost());
				System.out.println("	Port: " + httpurl.getPort());
				System.out.println("	Path: " + httpurl.getPath());
				System.out.println("	File: " + httpurl.getFile());
				System.out.println("	Query: " + httpurl.getQuery());
				System.out.println("}");
			}
		}

		if (requestMethod == null) {
			System.err.println("Request Method can't be null" + "\n"
					+ "Set with setRequestMethod(String requestMethod);");
			return -1;
		} else {
			httpconnection.setRequestMethod(getRequestMethod());
			httpconnection.setDoInput(true);
			if (requestMethod.equalsIgnoreCase("POST")) {
				httpconnection.setDoOutput(true);
				usePayload = true;
			} else {
				httpconnection.setDoOutput(false);
				usePayload = false;
			}
			if (debugEnabled) {
				System.out.println("Request Method: " + httpconnection.getRequestMethod());
			}
		}

		if (requestHeaders != null) {
			Set<String> headers = requestHeaders.keySet();
			Iterator<String> iterator = headers.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				httpconnection.setRequestProperty(key, requestHeaders.get(key));
			}
			if (debugEnabled) {
				System.out.println("Headers: " + httpconnection.getRequestProperties());
			}
		}
		if (requestCookies != null) {
			Iterator<HttpCookie> iterator = requestCookies.iterator();
			while (iterator.hasNext()) {
				HttpCookie key = iterator.next();
				cookieJar.add(httpurl.toURI(), key);
				if (debugEnabled) {
					System.out.println("Added connection cookie: " + key);
				}
			}

		}

		if (usePayload) {
			if (payload == null) {
				System.err.println("Post Data can't be null" + "\n" + "Set with setPayload();");
				return -1;
			} else {
				setPayload(payload);
				if (debugEnabled) {
					System.out.println("Payload: " + payload);
				}
			}
		}

		if (httpconnection.getDoOutput()) {
			OutputStream dos = new DataOutputStream(httpconnection.getOutputStream());
			dos.write(getPayload().getBytes());
			dos.flush();
			dos.close();
		} else {
			httpconnection.connect();
		}

		if (debugEnabled) {
			System.out.println("Response Details:");
			System.out.println("Input: " + httpconnection.getDoInput() + "\n" + "Output: "
					+ httpconnection.getDoOutput());
			System.out.println("URL: " + httpconnection.getURL() + "\n" + "Response Message: "
					+ httpconnection.getResponseMessage() + "\n" + "Returned Headers: "
					+ httpconnection.getHeaderFields());
			System.out.println("Error Stream: "
					+ CommonLogic.convertStreamToString(httpconnection.getErrorStream()));
			for (HttpCookie cookie : cookieJar.getCookies()) {
				System.out.println("CookieHandler retrieved cookie: " + cookie);
			}
			CommonLogic.splitOut();
			System.out.println("End Connection Deails");
			CommonLogic.splitOut();
		}

		InputStream is = null;
		try {
			is = httpconnection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Reported Error Message: "
					+ CommonLogic.convertStreamToString(httpconnection.getErrorStream()));
			// System.err.println(httpconnection.getRequestProperties());
			// System.err.println(httpconnection.getURL());
		}
		List<HttpCookie> cookies = cookieJar.getCookies();

		setResponseMessage(httpconnection.getResponseMessage());
		setResponseData(is);
		setResponseHeaders(httpconnection.getHeaderFields());
		setResponseCookies(cookies);
		setResponseCode(httpconnection.getResponseCode());
		setResponseErrorStream(CommonLogic.convertStreamToString(httpconnection.getErrorStream()));

		return getResponseCode();
	}

	public URL getServerUrl() {
		return serverUrl;
	}

	public ServerConnection setServerUrl(URL serverUrl) {
		if (isExpended) {

		}
		this.serverUrl = serverUrl;
		return this;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public ServerConnection setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
		return this;
	}

	public String getPayload() {
		return payload;
	}

	public ServerConnection setPayload(String payload) {
		this.payload = payload;
		return this;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public ServerConnection setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	public List<HttpCookie> getRequestCookies() {
		return requestCookies;
	}

	public ServerConnection setRequestCookies(List<HttpCookie> requestCookies) {
		this.requestCookies = requestCookies;
		return this;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	private void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}

	private void setResponseHeaders(Map<String, List<String>> map) {
		this.responseHeaders = map;
	}

	public List<HttpCookie> getResponseCookies() {
		return responseCookies;
	}

	private void setResponseCookies(List<HttpCookie> responseCookies) {
		this.responseCookies = responseCookies;
	}

	public int getResponseCode() {
		return responseCode;
	}

	private void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDataAsString() {
		InputStreamReader isr = new InputStreamReader(this.responseData);
		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public InputStream getResponseData() {
		return this.responseData;
	}

	private void setResponseData(InputStream responseData) {
		this.responseData = responseData;
	}

	public boolean isDebugenabled() {
		return debugEnabled;
	}

	private void setDebugenabled(boolean debugenabled) {
		this.debugEnabled = debugenabled;
	}

	public String getResponseErrorStream() {
		return responseErrorStream;
	}

	private void setResponseErrorStream(String responseErrorStream) {
		this.responseErrorStream = responseErrorStream;
	}
}
