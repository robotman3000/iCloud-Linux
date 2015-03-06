package icloud.services.account;

import icloud.services.BaseManager;
import icloud.user.UserSession;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.CommonLogic;
import common.ServerConnection;
import common.URLBuilder;

public class AccountManager extends BaseManager {

	public AccountManager() {
		this.isInitialized = true;
	}

	public AccountManager(boolean announceConnections, boolean debugEnabled) {
		this();
		this.announceConnections = announceConnections;
		this.debugEnabled = debugEnabled;
	}

	public void login(UserSession user) throws Exception {

		if (announceConnections) {
			System.out.println("Connecting to: Login Server");
		}

		Map<String, String> headersMap = new HashMap<String, String>();
		URLBuilder newUrl = new URLBuilder();

		newUrl.setPath(UserSession.account_url_login)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setUrl(UserSession.account_url_default_host);

		newUrl.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber());
		newUrl.addQueryString(UserSession.query_arg_clientId, user.getUuid());

		URL httpUrl = newUrl.buildURL();

		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled);
		conn.setServerUrl(httpUrl)
			.setRequestMethod(UserSession.POST)
			.setRequestHeaders(headersMap)
			.setPayload(generateQuery(user));
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		// Debug Output
		if (debugEnabled) {
			System.out.println("Authentication String: " + generateQuery(user));
			System.out.println("UUID: " + user.getUuid());
			CommonLogic.splitOut();
		}

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

	}

	public void logout(UserSession user) throws Exception {
		if (announceConnections) {
			System.out.println("Connecting to: Logout Server");
		}
		// TODO: Make all magic values be generated or read from a config file

		Map<String, String> headersMap = new HashMap<String, String>();
		String valCookie = user.getUserTokens().getTokenValue(
				"X-APPLE-WEBAUTH-VALIDATE");

		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled);

		String accountServer = user.getServerUrl("account");

		URLBuilder newUrl = new URLBuilder();
		newUrl.setPath(UserSession.account_url_logout)
			  .setUrl(accountServer)
			  .addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
			  .addQueryString(UserSession.query_arg_clientId, user.getUuid())
			  .addQueryString(UserSession.query_arg_dsid, user.getUserConfig()
			  .getUserProperties()
			  .getProperty("dsid"));
		
		newUrl.addQueryString(UserSession.query_arg_token, valCookie);
		URL httpUrl = newUrl.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod(UserSession.POST);
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload("{}");
		conn.connect();

		if (debugEnabled) {
			CommonLogic.printJson(conn.getResponseDataAsString());
		}
	}

	public void validate(UserSession user) throws Exception {

		if (announceConnections) {
			System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(UserSession.account_url_validate)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setUrl(UserSession.account_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}

	private void parseResponse(UserSession user, AccountJson aJson) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("Deseriialized");
		CommonLogic.printJson(gson.toJson(aJson));
		
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(gson.toJson(aJson));

		JsonObject userData = je.getAsJsonObject();
		JsonElement webServices = userData.get("webservices");
		JsonObject webServices2 = webServices.getAsJsonObject();

		// TODO: Make the code not assume that things will exist
		String[] webServiceStrings = { "reminders", "mail", "drivews",
				"settings", "keyvalue", "push", "contacts", "findme", "photos",
				"ubiquity", "iwmb", "ckdatabasews", "docws", "account",
				"streams", "notes", "calendar" };
		// String[] memberStrings = { "status", "url", "pcsRequired" };
		String[] memberStrings = { "url" };
		for (String str : webServiceStrings) {
			JsonElement obj = webServices2.get(str);
			JsonObject jelement = obj.getAsJsonObject();
			// Map<String, String> map1 = new HashMap<String, String>();
			for (String mstr : memberStrings) {
				if (jelement.has(mstr)) {
					JsonElement json = jelement.get(mstr);
					user.addServerUrl(str, json.getAsString());
					// map1.put(mstr, json.getAsString());
				}
			}
		}

		JsonElement jselement = userData.get("dsInfo");
		JsonObject dsInfo = jselement.getAsJsonObject();
		String[] dsInfoStrings = { "lastName", "appleId", "dsid",
				"languageCode", "fullName", "firstName" };

		for (String str : dsInfoStrings) {
			if (dsInfo.has(str)) {
				JsonElement var = dsInfo.get(str);
				user.getUserConfig().getUserProperties()
						.put(str, var.getAsString());

			}
		}

	}

	private String generateQuery(UserSession user) {
		return "{\"apple_id\":" + "\"" + user.getUsername() + "\""
				+ ",\"password\":" + "\"" + user.getPassword() + "\""
				+ ",\"extended_login\":" + user.isExtendedLogin() + "}";
	}
}
