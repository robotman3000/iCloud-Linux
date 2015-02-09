package icloud.services.account;

import icloud.services.BaseManager;
import icloud.services.ManagerInterface;
import icloud.user.UserSession;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.CommonLogic;
import common.ServerConnection;
import common.URLBuilder;

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
		URLBuilder newUrl = new URLBuilder();

		newUrl.setPath(user.getSessionConfigOptValue("account.url.login"));

		newUrl.setPort(Integer.parseInt(user.getSessionConfigOptValue("default.port")));

		newUrl.setProtocol(user.getSessionConfigOptValue("default.protocol"));

		newUrl.setUrl(user.getSessionConfigOptValue("account.url.default.host"));

		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());

		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"), user.getUuid());

		URL httpUrl = newUrl.buildURL();

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod(user.getSessionConfigOptValue("default.requestMethod"));
		conn.setRequestHeaders(headersMap);
		conn.setPayload(generateQuery(user));
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

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
		String valCookie = user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE");

		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		String accountServer = user.getServerUrl("account");
		//String accountServer = "https://www.icloud.com";
		/*
		 * URL httpUrl = new URL(accountServer.get("url") +
		 * "/setup/ws/1/logout?" + "clientBuildNumber=" +
		 * user.getClientBuildNumber() + "&clientId=" + user.getUuid() +
		 * "&token=" + valCookie + "&dsid=" +
		 * user.getUserConfig().getUserProperties().getProperty("dsid") +
		 * "&proxyDest=" + "p30-setup");
		 */
		URLBuilder newUrl = new URLBuilder();
		newUrl.setPath(user.getSessionConfigOptValue("account.url.logout"));
		//newUrl.setProtocol(user.getSessionConfigOptValue("default.protocol"));
		newUrl.setUrl(accountServer);
		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"), user.getUuid());
		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user.getUserConfig()
				.getUserProperties().getProperty("dsid"));
		//newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.proxyDest"), "p30-setup");
		newUrl.addQueryString(user.getSessionConfigOptValue("query.arg.token"), valCookie);
		URL httpUrl = newUrl.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
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
		// TODO: Make all magic values be generated or read from a config file

		URL url = new URLBuilder()
				.setPath(user.getSessionConfigOptValue("account.url.validate"))
				.setPort(Integer.parseInt(user.getSessionConfigOptValue("default.port")))
				.setProtocol(user.getSessionConfigOptValue("default.protocol"))
				.setUrl(user.getSessionConfigOptValue("account.url.default.host"))
				.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
						user.getClientBuildNumber())
				.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"), user.getUuid())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(user.getSessionConfigOptValue("default.requestMethod"))
				.setServerUrl(url).setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens()).setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
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
		//String[] memberStrings = { "status", "url", "pcsRequired" };
		String[] memberStrings = {"url"};
		for (String str : webServiceStrings) {
			JsonElement obj = webServices2.get(str);
			JsonObject jelement = obj.getAsJsonObject();
			//Map<String, String> map1 = new HashMap<String, String>();
			for (String mstr : memberStrings) {
				if (jelement.has(mstr)) {
					JsonElement json = jelement.get(mstr);
					user.addServerUrl(str, json.getAsString());
					//map1.put(mstr, json.getAsString());
				}
			}
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

	private String generateQuery(UserSession user) {
		return "{\"apple_id\":" + "\"" + user.getUsername() + "\"" + ",\"password\":" + "\""
				+ user.getPassword() + "\"" + ",\"extended_login\":" + user.isExtendedLogin() + "}";
	}
}
