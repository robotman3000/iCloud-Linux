package icloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URL;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserSessionInstance {
	private String username;
	private String password;
	private boolean extended_login;

	private Map<String, Map<String, String>> serversList = new HashMap<String, Map<String, String>>();
	private Map<String, String> userProperties = new HashMap<String, String>();

	public final String UUID;
	public final String clientBnum = "14H40";

	private UserTokenManager userTokens;
	private boolean debugenabled;
	private boolean announceConnections;

	public UserSessionInstance(String username, String password, boolean extended_login, boolean debugenabled, boolean announceConnections) {
		this.username = username;
		this.password = password;
		this.extended_login = extended_login;
		this.debugenabled = debugenabled;
		this.announceConnections = announceConnections;
		this.UUID = CommonLogic.generateUUID();
	}

	public void connect() throws Exception {
		
		if (announceConnections){
			System.out.println("Connecting to: Login Server");
		}
		// TODO: Make all magic values be generated or read from a config file

		Map<String, String> headersMap = new HashMap<String, String>();

		String authString = "{\"apple_id\":" + "\"" + username + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"extended_login\":" + extended_login + "}";
		// Debug Output
		if (debugenabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + UUID);
			CommonLogic.splitOut();
		}

		URL httpUrl = new URL("https://setup.icloud.com:443/setup/ws/1/login?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID);

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugenabled);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setPayload(authString);
		conn.connect();

		parseUserProps(conn.getResponseData());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		userTokens = new UserTokenManager(UUID, conn.getResponseCookies());

	}

	public void disconnect() throws Exception {
		if (announceConnections){
			System.out.println("Connecting to: Logout Server");
		}
		// TODO: Make all magic values be generated or read from a config file

		Map<String, String> headersMap = new HashMap<String, String>();
		String valCookie = "";
		String var = "X-APPLE-WEBAUTH-VALIDATE";

		Iterator<HttpCookie> iterator = userTokens.getUserTokens().iterator();
		while (iterator.hasNext()) {
			HttpCookie key = iterator.next();
			String name = key.getName();
			int number = name.compareTo(var);
			if (number == 0) {
				valCookie = key.getValue();
				// System.out.println(valCookie);

			}
		}

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugenabled);

		Map<String, String> accountServer = serversList.get("account");
		URL httpUrl = new URL(accountServer.get("url") + "/setup/ws/1/logout?" + "clientBuildNumber=" + clientBnum + "&clientId=" + UUID + "&token=" + valCookie + "&dsid=" + getUserID() + "&proxyDest=" + "p30-setup");
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(userTokens.getUserTokens());
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

	private void parseUserProps(String string) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(string);

		JsonObject userData = je.getAsJsonObject();
		JsonElement webServices = userData.get("webservices");
		JsonObject webServices2 = webServices.getAsJsonObject();

		String[] webServiceStrings = { "reminders", "mail", "drivews", "settings", "keyvalue", "push", "contacts", "findme", "photos", "ubiquity", "iwmb", "ckdatabasews", "docws", "account", "streams", "notes", "calendar" };
		String[] memberStrings = { "status", "url", "pcsRequired" };
		for (String str : webServiceStrings) {
			JsonElement obj = webServices2.get(str);
			JsonObject jelement = obj.getAsJsonObject();
			Map<String, String> map1 = new HashMap<String, String>();
			for (String mstr : memberStrings) {
				if (jelement.has(mstr)) {
					JsonElement json = jelement.get(mstr);
					map1.put(mstr, json.getAsString());
				}
			}
			serversList.put(str, map1);
		}

		JsonElement jselement = userData.get("dsInfo");
		JsonObject dsInfo = jselement.getAsJsonObject();
		String[] dsInfoStrings = { "lastName", "appleId", "dsid", "languageCode", "fullName", "firstName" };

		for (String str : dsInfoStrings) {
			if (dsInfo.has(str)) {
				JsonElement var = dsInfo.get(str);
				userProperties.put(str, var.getAsString());

			}
		}

	}

	public UserTokenManager getTokenManager() {
		return userTokens;
	}

	public String getServerUrl(String url){
		if (serversList.containsKey(url)){
			return serversList.get(url).get("url");
		}
		return CommonLogic.LOCALHOST;
	}
	
	public String getUserID() {
		return userProperties.get("dsid");
	}

	public String getAppleID() {
		return userProperties.get("appleId");
	}

	public String getLocale() {
		return userProperties.get("languageCode");
	}

	public String getFirstName() {
		return userProperties.get("firstName");
	}

	public String getLastName() {
		return userProperties.get("lastName");
	}

	public String getFullName() {
		return userProperties.get("fullName");
	}

	@SuppressWarnings("unused")
	private void abc123() throws Exception {
		URL httpUrl = new URL("https://p30-contactsws.icloud.com/co/startup?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID + "&dsid=" + "8084583249" + "&locale=" + "en_US" + "&order=" + "last,first" + "&clientVersion" + "2.1");

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");

		ServerConnection conn = new ServerConnection(debugenabled);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(userTokens.getUserTokens());
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

		userTokens.updateUserTokens(conn.getResponseCookies(), UUID);

		if (debugenabled) {
			for (HttpCookie cookie : userTokens.getUserTokens()) {
				System.out.println("CookieHandler retrieved cookie: " + cookie);
			}
		}
	}

}
