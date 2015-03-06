package icloud.user;

import java.util.Properties;

import common.CommonLogic;

public class UserSession {

	private UserData userData = new UserData();
	private UserTokens userTokens = new UserTokens();
	private UserConfig userConfig = new UserConfig();

	private Properties urlList = new Properties();
	
	private static final String clientBuildNumber = "14H40";
	private final String UUID = CommonLogic.generateUUID();

	private String username;
	private String password;
	private boolean extendedLogin = false;
	
	public static final String account_url_login = "/setup/ws/1/login?";
	public static final String account_url_logout = "/setup/ws/1/logout?";
	public static final String account_url_validate = "/setup/ws/1/validate?";
	public static final String account_url_default_host = "setup.icloud.com";
	
	public static final String notes_url_startup = "/no/startup?";
	public static final String notes_url_createnotes = "/no/createNotes?";
	public static final String notes_url_updatenotes = "/no/updateNotes?";
	public static final String notes_url_deletenotes = "/no/deleteNotes?";
	public static final String notes_url_retriveAttachment = "/no/retrieveAttachment?";
	public static final String notes_url_changeset = "/no/changeset?";
	public static final String notes_url_default_host = "notesws.icloud.com";
	
	public static final String CONTACTS_URL_DEFAULT_HOST = "contactsws.icloud.com";
	
	public static final String default_protocol = "https://";
	public static final int default_port = 443;
	public static final String default_header_origin = "https://www.icloud.com";
	public static final String default_header_userAgent = "Mozilla/5.0 Java_iCloud/1.0";
	
	public static final String POST = "POST";
	public static final String GET = "GET";
	
	public static final String query_arg_clientBN = "clientBuildNumber";
	public static final String query_arg_clientId = "clientId";
	public static final String query_arg_dsid = "dsid";
	public static final String query_arg_proxyDest = "proxyDest";
	public static final String query_arg_token = "token";
	public static final String query_arg_syncToken = "syncToken";
	public static final String query_arg_validateToken = "validateToken";
	public static final String query_arg_attachmentId = "attachmentId";

	public UserSession(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserSession(String username, String password, boolean extendedLogin) {
		this(username, password);
		this.extendedLogin = extendedLogin;
	}

	public UserTokens getUserTokens() {
		return userTokens;
	}

	public UserData getUserData() {
		return userData;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public String getClientBuildNumber() {
		return clientBuildNumber;
	}

	public String getUuid() {
		return UUID;
	}

	public boolean isExtendedLogin() {
		return extendedLogin;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getServerUrl(String string) {
		return urlList.getProperty(string);
	}
	
	public void addServerUrl(String key, String value){
		urlList.put(key, value);
	}



}
