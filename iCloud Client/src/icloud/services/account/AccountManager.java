package icloud.services.account;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.CommonLogic;
import common.ServerConnection;
import common.URLBuilder;
import icloud.services.BaseManager;
import icloud.services.ManagerInterface;
import icloud.services.ServerStrings;
import icloud.user.UserSession;

public class AccountManager extends BaseManager implements ManagerInterface {

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
		// TODO: Make all magic values be generated or read from a config file

		Map<String, String> headersMap = new HashMap<String, String>();
		// TODO: make this be handled by generateQuery()
		String authString = "{\"apple_id\":" + "\"" + user.getUsername() + "\"" + ",\"password\":"
				+ "\"" + user.getPassword() + "\"" + ",\"extended_login\":"
				+ user.isExtendedLogin() + "}";

		/*
		 * URL httpUrl = new URL(initialURL +
		 * ServerStrings.getConfig().getProperty("account.url.login") +
		 * ServerStrings.getConfig().getProperty("query.arg.clientBN") + "=" +
		 * user.getClientBuildNumber() + "&" +
		 * ServerStrings.getConfig().getProperty("query.arg.clientId") + "="
		 * +user.getUuid());
		 */
		URLBuilder newUrl = new URLBuilder();
		newUrl.setPath(ServerStrings.getConfig().getProperty("account.url.login"));
		newUrl.setPort(Integer.parseInt(ServerStrings.getConfig().getProperty(
				"account.url.default.port")));
		newUrl.setProtocol(ServerStrings.getConfig().getProperty("account.url.default.protocol"));
		newUrl.setUrl(ServerStrings.getConfig().getProperty("account.url.default.host"));
		newUrl.addQueryString(ServerStrings.getConfig().getProperty("query.arg.clientBN"),
				user.getClientBuildNumber());
		newUrl.addQueryString(ServerStrings.getConfig().getProperty("query.arg.clientId"),
				user.getUuid());
		URL httpUrl = newUrl.buildURL();

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod(ServerStrings.getConfig().getProperty(
				"account.url.default.requestMethod"));
		conn.setRequestHeaders(headersMap);
		conn.setPayload(authString);
		conn.connect();

		parseResponse(user, conn.getResponseData());

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		// Debug Output
		if (debugEnabled) {
			System.out.println("Authentication String: " + authString);
			System.out.println("UUID: " + user.getUuid());
			CommonLogic.splitOut();
		}

		if (debugEnabled) {
			CommonLogic.printJson(conn.getResponseData());
		}

	}

	public void logout(UserSession user) throws Exception {
		if (announceConnections) {
			System.out.println("Connecting to: Logout Server");
		}
		// TODO: Make all magic values be generated or read from a config file

		Map<String, String> headersMap = new HashMap<String, String>();
		String valCookie = user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE");

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		Map<String, String> accountServer = user.getUserConfig().getServersList().get("account");
		URL httpUrl = new URL(accountServer.get("url") + "/setup/ws/1/logout?"
				+ "clientBuildNumber=" + user.getClientBuildNumber() + "&clientId="
				+ user.getUuid() + "&token=" + valCookie + "&dsid="
				+ user.getUserConfig().getUserProperties().getProperty("dsid") + "&proxyDest="
				+ "p30-setup");
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload("{}");
		conn.connect();

		if (debugEnabled) {
			CommonLogic.printJson(conn.getResponseData());
		}

	}

	public void validate(UserSession user) throws Exception {

		if (announceConnections) {
			System.out.println("Connecting to: Validate Server");
		}
		// TODO: Make all magic values be generated or read from a config file

		URLBuilder urlBuilder = new URLBuilder()
				.setPath(ServerStrings.getConfig().getProperty("account.url.validate"))
				.setPort(
						Integer.parseInt(ServerStrings.getConfig().getProperty(
								"account.url.default.port")))
				.setProtocol(ServerStrings.getConfig().getProperty("account.url.default.protocol"))
				.setUrl(ServerStrings.getConfig().getProperty("account.url.default.host"))
				.addQueryString(ServerStrings.getConfig().getProperty("query.arg.clientBN"),
						user.getClientBuildNumber())
				.addQueryString(ServerStrings.getConfig().getProperty("query.arg.clientId"),
						user.getUuid());

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		URL url = urlBuilder.buildURL();
		ServerConnection conn = new ServerConnection(debugEnabled).setRequestMethod("POST").setServerUrl(url)
				.setPayload("{}").setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		parseResponse(user, conn.getResponseData());
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(conn.getResponseData());
		}
	}

	private void parseResponse(UserSession user, String string) {
		/*
		 * CommonLogic.printJson(string); CommonLogic.splitOut();
		 * CommonLogic.splitOut(); CommonLogic.splitOut();
		 */
		// AccountJson accj = new AccountJson();
		// Gson gsonParse = new Gson();
		// accj = gsonParse.fromJson(string, AccountJson.class);
		// CommonLogic.printJson(gsonParse.toJson(accj));
		// CommonLogic.splitOut();

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(string);

		JsonObject userData = je.getAsJsonObject();
		JsonElement webServices = userData.get("webservices");
		JsonObject webServices2 = webServices.getAsJsonObject();

		// TODO: Make the code not assume that things will exist
		String[] webServiceStrings = { "reminders", "mail", "drivews", "settings", "keyvalue",
				"push", "contacts", "findme", "photos", "ubiquity", "iwmb", "ckdatabasews",
				"docws", "account", "streams", "notes", "calendar" };
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
			user.getUserConfig().getServersList().put(str, map1);
		}

		JsonElement jselement = userData.get("dsInfo");
		JsonObject dsInfo = jselement.getAsJsonObject();
		String[] dsInfoStrings = { "lastName", "appleId", "dsid", "languageCode", "fullName",
				"firstName" };

		for (String str : dsInfoStrings) {
			if (dsInfo.has(str)) {
				JsonElement var = dsInfo.get(str);
				user.getUserConfig().getUserProperties().put(str, var.getAsString());

			}
		}

	}

	private void generateQuery() {

	}
}
