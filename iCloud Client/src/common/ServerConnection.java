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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerConnection {

	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	// TODO: implement constructor stubs
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
	private SystemLogger logger = new SystemLogger(SystemLogger.LoggingVerboseity.NONE);

	
	public ServerConnection(URL connectionUrl, String requestMethod, List<HttpCookie> requestCookies, Map<String, String> requestHeaders){
		this();
	}
	
	public ServerConnection(URL connectionUrl, String requestMethod, List<HttpCookie> requestCookies){
		this();
	}
	
	public ServerConnection(URL connectionUrl, String requestMethod, Map<String, String> requestHeaders){
		this();
	}
	
	public ServerConnection(URL connectionUrl, String requestMethod){
		this();
	}
	
	public ServerConnection() {

	}
	
	public ServerConnection setLogger(SystemLogger logger){
		// Don't make a new copy so that logger settings stay in sync by using the same object reference
		//TODO: fix a null pointer exeception that could occur if the logger is made null by external sources
		if (logger != null){
			this.logger = logger;
		}
		return this;
	}

	public int connect() throws Exception {
		isExpended = true;
		boolean usePayload = false;

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		if (logger != null) {
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Start connection details", this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
		}

		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
		CookieStore cookieJar = manager.getCookieStore();

		URL httpurl;
		HttpURLConnection httpconnection;

		if (serverUrl == null) {
			//TODO: throw new InvalidHostException()
			System.err.println("Server URL can't be null" + "\n"
					+ "Set with setServerUrl(URL url);");
			return -1;
		} else {
			httpurl = getServerUrl();
			httpconnection = (HttpURLConnection) httpurl.openConnection();

			// Debug Output
			if (logger != null) {
				logger.log("URL is: " + httpurl, this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("URL Details: {", this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	Protocol: " + httpurl.getProtocol(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	Host: " + httpurl.getHost(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	Port: " + httpurl.getPort(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	Path: " + httpurl.getPath(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	File: " + httpurl.getFile(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("	Query: " + httpurl.getQuery(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				logger.log("}", this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
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
			if (logger != null) {
				logger.log("Request Method: " + httpconnection.getRequestMethod(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			}
		}

		if (requestHeaders != null) {
			Set<String> headers = requestHeaders.keySet();
			Iterator<String> iterator = headers.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				httpconnection.setRequestProperty(key, requestHeaders.get(key));
			}
			if (logger != null) {
				logger.log("Headers: " + httpconnection.getRequestProperties(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			}
		}
		if (requestCookies != null) {
			Iterator<HttpCookie> iterator = requestCookies.iterator();
			while (iterator.hasNext()) {
				HttpCookie key = iterator.next();
				cookieJar.add(httpurl.toURI(), key);
				if (logger != null) {
					logger.log("Added connection cookie: " + key, this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
				}
			}

		}

		if (usePayload) {
			if (payload == null) {
				//TODO: throw new InvalidPayloadException()
				System.err.println("Post Data can't be null" + "\n" + "Set with setPayload();");
				return -1;
			} else {
				setPayload(payload);
				if (logger != null) {
					logger.log("Payload: " + payload, this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
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

		if (logger != null) {
			logger.log("Response Details:", this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Input: " + httpconnection.getDoInput(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Output: " + httpconnection.getDoOutput(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("URL: " + httpconnection.getURL(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Response Message: " + httpconnection.getResponseMessage(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Returned Headers: " + httpconnection.getHeaderFields(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("Error Stream: "
					+ CommonLogic.convertStreamToString(httpconnection.getErrorStream()), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);

			for (HttpCookie cookie : cookieJar.getCookies()) {
				logger.log("CookieHandler retrieved cookie: " + cookie, this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			}
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log("End connection details", this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.DEVELOPER);
		}

		InputStream is = null;
		try {
			is = httpconnection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//TODO: finish this
			e.printStackTrace();
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.ERROR);
			System.err.println("Reported Error Message: "
					+ CommonLogic.convertStreamToString(httpconnection.getErrorStream()));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.log(logger.getSeperator(), this.getClass().getName(), SystemLogger.LoggingVerboseity.ERROR);
			System.err.println("ServerConnection Object\n" + gson.toJson(this));
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

	
	public String getPayload() {
		return payload;
	}

	public List<HttpCookie> getRequestCookies() {
		return requestCookies;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public URL getServerUrl() {
		return serverUrl;
	}


	public ServerConnection setPayload(String payload) {
		this.payload = payload;
		return this;
	}

	public ServerConnection setRequestCookies(List<HttpCookie> requestCookies) {
		this.requestCookies = requestCookies;
		return this;
	}

	public ServerConnection setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
		return this;
	}

	public ServerConnection setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	public ServerConnection setServerUrl(URL serverUrl) {
		if (isExpended) {

		}
		this.serverUrl = serverUrl;
		return this;
	}
	
	
	private void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	private void setResponseCookies(List<HttpCookie> responseCookies) {
		this.responseCookies = responseCookies;
	}

	private void setResponseData(InputStream responseData) {
		this.responseData = responseData;
	}

	private void setResponseErrorStream(String responseErrorStream) {
		this.responseErrorStream = responseErrorStream;
	}

	private void setResponseHeaders(Map<String, List<String>> map) {
		this.responseHeaders = map;
	}

	private void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	
	public int getResponseCode() {
		return responseCode;
	}

	public List<HttpCookie> getResponseCookies() {
		return responseCookies;
	}

	public InputStream getResponseData() {
		return this.responseData;
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

	public String getResponseErrorStream() {
		return responseErrorStream;
	}

	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

}
