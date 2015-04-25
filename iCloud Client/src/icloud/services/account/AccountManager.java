package icloud.services.account;

import icloud.services.BaseManager;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;
import icloud.services.account.objects.Webservices.Webservice;
import icloud.services.notes.NoteManager;
import icloud.user.UserSession;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import common.http.ServerConnection;
import common.http.URLBuilder;
import common.http.URLConfig;

public class AccountManager extends BaseManager{

	private static final String account_url_default_host = "setup.icloud.com";
	private static final String account_url_login = "/setup/ws/1/login?";
	private static final String account_url_logout = "/setup/ws/1/logout?";
	private static final String account_url_validate = "/setup/ws/1/validate?";
	private static final String account_url_getDevices = "/setup/web/device/getDevices?";
	private static final String account_url_getFamilyDetails = "/setup/web/family/getFamilyDetails?";
	private static final String account_url_getStorageUsageInfo = "/setup/ws/1/storageUsageInfo?";
	
	private static AccountManager self = new AccountManager();
	// TODO: Add all of the data retrieval methods to AccountManager
	// TODO: Add safety checks to the getters and setters. ie dont return null instead throw an exeception

	public AccountManager() {
		super();
	}

	public void login(UserSession user) throws Exception {
		
		URL httpUrl = new URLBuilder()
				.setProtocol(URLConfig.default_protocol)
				.setHost(AccountManager.account_url_default_host)
				.setPort(URLConfig.default_port)
				.setPath(AccountManager.account_url_login)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.buildURL();
		
		ServerConnection conn = new ServerConnection()
				.setServerUrl(httpUrl)
				.setRequestMethod(URLConfig.POST)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload(generateQuery(user));
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		//System.out.println(responseData);
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		//if (debugEnabled) {
		//	CommonLogic.printJson(responseData);
		//}

	}

	public void logout(UserSession user) throws Exception {
		// TODO: Make all magic values be generated or read from a config file
		String valCookie = user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE");
		
		URL httpUrl = new URLBuilder(self.rootURL)
				.setPath(AccountManager.account_url_logout)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserConfig().getUserProperties().getProperty("dsid"))
				.addQueryString(URLConfig.query_arg_token, valCookie)
				.buildURL();
		
		ServerConnection conn = new ServerConnection()
				.setServerUrl(httpUrl)
				.setRequestMethod(URLConfig.POST)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setRequestCookies(user.getUserTokens().getTokens())
				.setPayload("{}");
		conn.connect();
		

		//String accountServer = user.getServerUrl("account");
		
/*		if (debugEnabled) {
			CommonLogic.printJson(conn.getResponseDataAsString());
		}*/
	}

	public void validate(UserSession user) throws Exception {

		URL url = new URLBuilder(self.rootURL)
				.setPath(AccountManager.account_url_validate)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.buildURL();


		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/
	}

	public void getDevices(UserSession user) throws Exception {

		URL url = new URLBuilder(self.rootURL)
				.setPath(AccountManager.account_url_getDevices)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

	}

	public void getFamilyDetails(UserSession user) throws Exception {

/*		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}*/

		URL url = new URLBuilder(self.rootURL)
				.setPath(AccountManager.account_url_getFamilyDetails)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.buildURL();
		
		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

	}

	public void getStorageUsageInfo(UserSession user) throws Exception {

/*		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}*/

		URL url = new URLBuilder(self.rootURL)
				.setPath(AccountManager.account_url_getStorageUsageInfo)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user, new Gson().fromJson(responseData, AccountJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

	}

	private void parseResponse(UserSession user, AccountJson aJson) {
		AccountConfig aConf = user.getUserConfig().getAccountConfig();
		AccountData aData = user.getUserData().getAccountData();

		if (aJson.isExtendedLogin() != null) {
			aConf.setExtendedLogin(aJson.isExtendedLogin());
		}

		if (aJson.isPcsServiceIdentitiesIncluded() != null) {
			aConf.setPcsServiceIdentitiesIncluded(aJson.isPcsServiceIdentitiesIncluded());
		}

		if (aJson.isHasMinimumDeviceForPhotosWeb() != null) {
			aConf.setHasMinimumDeviceForPhotosWeb(aJson.isHasMinimumDeviceForPhotosWeb());
		}

		if (aJson.isMemberOfFamily() != null) {
			aConf.setMemberOfFamily(aJson.isMemberOfFamily());
		}

		if (aJson.isPcsEnabled() != null) {
			aConf.setPcsEnabled(aJson.isPcsEnabled());
		}

		if (aJson.webservices != null && aJson.webservices.getWebservices() != null) {
			configureUrls(aJson.webservices.getWebservices());
			for (Webservice webService : aJson.webservices.getWebservices()) {
				aConf.addWebservice(webService);
			}
		}

		if (aJson.isSuccess() != null) {
			if (aJson.isSuccess()) {
				// Do something
			} else {
				// Do something else
			}
		}

		if (aJson.getDevices() != null) {
			for (Device device : aJson.getDevices()) {
				aData.addUserDevice(device.getUdid(), device);
			}
		}

		if (aJson.getStorageUsageByMedia() != null) {
			for (StorageBlockInfo storeBlock : aJson.getStorageUsageByMedia()) {
				aData.addStorageBlock(storeBlock.getDisplayLabel(), storeBlock);
			}
		}

		if (aJson.dsInfo != null) {
			aData.setDsInfo(aJson.dsInfo);
		}

		if (aJson.requestInfo != null) {
			aData.setRequestInfo(aJson.requestInfo);
		}

		if (aJson.storageUsageInfo != null) {
			aData.setStorageTotals(aJson.storageUsageInfo);
		}

		if (aJson.quotaStatus != null) {
			aData.setQuotaStatus(aJson.quotaStatus);
		}
	}

	private void configureUrls(List<Webservice> webServices) {
		for (Webservice service : webServices) {
			// We use an "if" statement here instead of switch for java 1.6 compatibility
			if (service.getName().equalsIgnoreCase("reminders")) { // iCloud Reminders

			} else if (service.getName().equalsIgnoreCase("mail")) { // iCloud Mail

			} else if (service.getName().equalsIgnoreCase("iworkexportws")) {

			} else if (service.getName().equalsIgnoreCase("drivews")) { // iCloud Drive

			} else if (service.getName().equalsIgnoreCase("settings")) { // iCloud Settings

			} else if (service.getName().equalsIgnoreCase("keyvalue")) { // Keyvalue Services

			} else if (service.getName().equalsIgnoreCase("cksharews")) {

			} else if (service.getName().equalsIgnoreCase("push")) { // iCloud Push Services

			} else if (service.getName().equalsIgnoreCase("contacts")) { // iCloud Contacts

			} else if (service.getName().equalsIgnoreCase("findme")) { // iCloud Find my iPhone

			} else if (service.getName().equalsIgnoreCase("photos")) { // iCloud Photo Library

			} else if (service.getName().equalsIgnoreCase("ubiquity")) {

			} else if (service.getName().equalsIgnoreCase("ckdatabasews")) {

			} else if (service.getName().equalsIgnoreCase("iwmb")) {

			} else if (service.getName().equalsIgnoreCase("docws")) {

			} else if (service.getName().equalsIgnoreCase("iworkthumbnailws")) {

			} else if (service.getName().equalsIgnoreCase("account")) { // iCloud Sign in and out
				new AccountManager().setRootURL(service.getUrl());
			} else if (service.getName().equalsIgnoreCase("streams")) {

			} else if (service.getName().equalsIgnoreCase("notes")) { // iCloud Notes
				new NoteManager().setRootURL(service.getUrl());
			} else if (service.getName().equalsIgnoreCase("calendar")) { // iCloud Calendars

			}
		}
	}

	@Override
	protected void setRootURL(String newUrl){
		try {
			self.rootURL = new URL(newUrl);
		} catch (MalformedURLException e) {
			//TODO: make this use the fallback data
			e.printStackTrace();
		}
	}
	
	private String generateQuery(UserSession user) {
		return "{\"apple_id\":" + "\"" + user.getUsername() + "\"" + ",\"password\":" + "\"" + user.getPassword() + "\"" + ",\"extended_login\":" + user.isExtendedLogin() + "}";
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
