package icloud.services.account;

import icloud.services.BaseManager;
import icloud.services.URLConfig;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;
import icloud.services.account.objects.Webservices.Webservice;
import icloud.user.UserSession;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import common.CommonLogic;
import common.ServerConnection;
import common.SystemLogger;
import common.URLBuilder;

public class AccountManager extends BaseManager {

	// TODO: Add all of the data retrieval methods to AccountManager
	//TODO: Add safety checks to the getters and setters. ie dont return null instead throw an exeception

	public AccountManager() {
		this.isInitialized = true;
	}

	public AccountManager(SystemLogger logger) {
		this();
		this.logger = logger;
	}

	public void login(UserSession user) throws Exception {

		if (announceConnections) {
			System.out.println("Connecting to: Login Server");
		}

		Map<String, String> headersMap = new HashMap<String, String>();
		URLBuilder newUrl = new URLBuilder();

		newUrl.setPath(URLConfig.account_url_login)
				.setPort(URLConfig.default_port)
				.setProtocol(URLConfig.default_protocol)
				.setHost(URLConfig.account_url_default_host);

		newUrl.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		newUrl.addQueryString(URLConfig.query_arg_clientId, user.getUuid());

		URL httpUrl = newUrl.buildURL();

		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection();
		conn.setServerUrl(httpUrl).setRequestMethod(URLConfig.POST)
				.setRequestHeaders(headersMap).setPayload(generateQuery(user));
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, AccountJson.class));

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

		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection().setLogger(logger);

		String accountServer = user.getServerUrl("account");

		URLBuilder newUrl = new URLBuilder();
		newUrl.setPath(URLConfig.account_url_logout)
				.setHost(accountServer)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(
						URLConfig.query_arg_dsid,
						user.getUserConfig().getUserProperties()
								.getProperty("dsid"));

		newUrl.addQueryString(URLConfig.query_arg_token, valCookie);
		URL httpUrl = newUrl.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod(URLConfig.POST);
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
				.setPath(URLConfig.account_url_validate)
				.setPort(URLConfig.default_port)
				.setProtocol(URLConfig.default_protocol)
				.setHost(URLConfig.account_url_default_host)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection().setLogger(logger)
				.setRequestMethod(URLConfig.POST).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}

	public void getDevices(UserSession user) throws Exception {

		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(URLConfig.account_url_getDevices)
				.setPort(URLConfig.default_port)
				.setProtocol(URLConfig.default_protocol)
				.setHost(URLConfig.account_url_default_host)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(
						"dsid",
						user.getUserData().getAccountData().getDsInfo()
								.getDsid()).buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection().setLogger(logger)
				.setRequestMethod(URLConfig.GET).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

	}

	public void getFamilyDetails(UserSession user) throws Exception {

		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(URLConfig.account_url_getFamilyDetails)
				.setPort(URLConfig.default_port)
				.setProtocol(URLConfig.default_protocol)
				.setHost(URLConfig.account_url_default_host)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(
						"dsid",
						user.getUserData().getAccountData().getDsInfo()
								.getDsid()).buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection().setLogger(logger)
				.setRequestMethod(URLConfig.GET).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

	}

	public void getStorageUsageInfo(UserSession user) throws Exception {

		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(URLConfig.account_url_getStorageUsageInfo)
				.setPort(URLConfig.default_port)
				.setProtocol(URLConfig.default_protocol)
				.setHost(URLConfig.account_url_default_host)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(
						"dsid",
						user.getUserData().getAccountData().getDsInfo()
								.getDsid()).buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", URLConfig.default_header_origin);
		headersMap.put("User-Agent", URLConfig.default_header_userAgent);

		ServerConnection conn = new ServerConnection().setLogger(logger)
				.setRequestMethod(URLConfig.POST).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

	}

	private void parseResponse(UserSession user, AccountJson aJson) {
		AccountConfig aConf = user.getUserConfig().getAccountConfig();
		AccountData aData = user.getUserData().getAccountData();
		
		if(aJson.isExtendedLogin() != null){
			aConf.setExtendedLogin(aJson.isExtendedLogin());
		}
		
		if(aJson.isPcsServiceIdentitiesIncluded() != null){
			aConf.setPcsServiceIdentitiesIncluded(aJson.isPcsServiceIdentitiesIncluded());
		}
		
		if(aJson.isHasMinimumDeviceForPhotosWeb() != null){
			aConf.setHasMinimumDeviceForPhotosWeb(aJson.isHasMinimumDeviceForPhotosWeb());
		}
		
		if(aJson.isMemberOfFamily() != null){
			aConf.setMemberOfFamily(aJson.isMemberOfFamily());
		}
		
		if(aJson.isPcsEnabled() != null){
			aConf.setPcsEnabled(aJson.isPcsEnabled());
		}
		
		if(aJson.webservices != null && aJson.webservices.getWebservices() != null){
			for (Webservice webService : aJson.webservices.getWebservices()) {
				user.addServerUrl(webService.getName(), webService.getUrl());
				aConf.addWebservice(webService);
			}
		}
		
		if(aJson.isSuccess() != null){
			if (aJson.isSuccess()) {
				// Do something
			} else {
				// Do something else
			}
		}
		
		if(aJson.getDevices() != null){
			for (Device device : aJson.getDevices()) {
				aData.addUserDevice(device.getUdid(), device);
			}
		}
		
		if(aJson.getStorageUsageByMedia() != null){
			for (StorageBlockInfo storeBlock : aJson.getStorageUsageByMedia()) {
				aData.addStorageBlock(storeBlock.getDisplayLabel(), storeBlock);
			}
		}
		
		if(aJson.dsInfo != null){
			aData.setDsInfo(aJson.dsInfo);
		}
		
		if(aJson.requestInfo != null){
			aData.setRequestInfo(aJson.requestInfo);
		}
		
		if(aJson.storageUsageInfo != null){
			aData.setStorageTotals(aJson.storageUsageInfo);
		}
		
		if(aJson.quotaStatus != null){
			aData.setQuotaStatus(aJson.quotaStatus);
		}
	}

	private String generateQuery(UserSession user) {
		return "{\"apple_id\":" + "\"" + user.getUsername() + "\""
				+ ",\"password\":" + "\"" + user.getPassword() + "\""
				+ ",\"extended_login\":" + user.isExtendedLogin() + "}";
	}
	
	@SuppressWarnings("unused")
	private void oldParseResponse(){
		
		// aData.setRequestInfo(aJson.requestInfo);

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// JsonParser jp = new JsonParser();
		// JsonElement je = jp.parse(gson.toJson(aJson));
		//
		// JsonObject userData = je.getAsJsonObject();
		// JsonElement webServices = userData.get("webservices");
		// JsonObject webServices2 = webServices.getAsJsonObject();
		//
		// // TODO: Make the code not assume that things will exist
		// String[] webServiceStrings = { "reminders", "mail", "drivews",
		// "settings", "keyvalue", "push", "contacts", "findme", "photos",
		// "ubiquity", "iwmb", "ckdatabasews", "docws", "account",
		// "streams", "notes", "calendar" };
		// // String[] memberStrings = { "status", "url", "pcsRequired" };
		// String[] memberStrings = { "url" };
		// for (String str : webServiceStrings) {
		// JsonElement obj = webServices2.get(str);
		// JsonObject jelement = obj.getAsJsonObject();
		// // Map<String, String> map1 = new HashMap<String, String>();
		// for (String mstr : memberStrings) {
		// if (jelement.has(mstr)) {
		// JsonElement json = jelement.get(mstr);
		// user.addServerUrl(str, json.getAsString());
		// // map1.put(mstr, json.getAsString());
		// }
		// }
		// }
		//
		// JsonElement jselement = userData.get("dsInfo");
		// JsonObject dsInfo = jselement.getAsJsonObject();
		// String[] dsInfoStrings = { "lastName", "appleId", "dsid",
		// "languageCode", "fullName", "firstName" };
		//
		// for (String str : dsInfoStrings) {
		// if (dsInfo.has(str)) {
		// JsonElement var = dsInfo.get(str);
		// user.getUserConfig().getUserProperties()
		// .put(str, var.getAsString());
		//
		// }
		// }
		
	}

	public boolean isPrimaryEmailVerified(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isPrimaryEmailVerified();
	}

	public String getiCloudAppleIdAlias(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getiCloudAppleIdAlias();
	}

	public String getLastName(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getLastName();
	}

	public String getAppleIdAlias(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getAppleIdAlias();
	}

	public String getLocale(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getLocale();
	}

	public boolean hasICloudQualifyingDevice(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isHasICloudQualifyingDevice();
	}

	public boolean isPaidDeveloper(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isPaidDeveloper();
	}

	public String getAppleId(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getAppleId();
	}

	public boolean isGilligan_invited(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isGilligan_invited();
	}

	public boolean isGilligan_enabled(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isGilligan_enabled();
	}

	public String getDsid(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getDsid();
	}

	public String getPrimaryEmail(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getPrimaryEmail();
	}

	public int getStatusCode(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getStatusCode();
	}

	public boolean isBrMigrated(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isBrMigrated();
	}

	public String getLanguageCode(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getLanguageCode();
	}

	public String getaDsID(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getaDsID();
	}

	public boolean isLocked(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().isLocked();
	}

	public String getFullName(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getFullName();
	}

	public String getFirstName(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getFirstName();
	}

	public String[] getAppleIdAliases(UserSession user) {
		return user.getUserData().getAccountData().getDsInfo().getAppleIdAliases();
	}

	public Set<String> getDevicesList(UserSession user) {
		return user.getUserData().getAccountData().getDevices().keySet();
	}

	public Device getDevice(UserSession user, String deviceId) {
		return user.getUserData().getAccountData().getDevices().get(deviceId);
	}

	public Set<String> getStorageBlockList(UserSession user) {
		return user.getUserData().getAccountData().getStorageBlocks().keySet();
	}
	
	public StorageBlockInfo getStorageBlock(UserSession user, String key) {
		return user.getUserData().getAccountData().getStorageBlocks().get(key);
	}

	public StorageUsageInfo getStorageUsage(UserSession user) {
		return user.getUserData().getAccountData().getStorageTotals();
	}

	public QuotaStatus getStorageQuotaStatus(UserSession user) {
		return user.getUserData().getAccountData().getQuotaStatus();
	}

	public RequestInfo getUserLocale(UserSession user) {
		return user.getUserData().getAccountData().getRequestInfo();
	}



}
