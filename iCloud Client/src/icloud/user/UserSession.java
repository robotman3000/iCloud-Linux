package icloud.user;

import java.util.Properties;

import common.CommonLogic;
import common.SystemLogger;
import common.SystemLogger.LoggingVerbosity;

public class UserSession {

	private UserData userData = new UserData();
	private UserTokens userTokens = new UserTokens();
	private UserConfig userConfig = new UserConfig();
	
	private SystemLogger loudMouth = new SystemLogger();
	
	private Properties urlList = new Properties();

	private static final String clientBuildNumber = "14H40";
	private final String UUID = CommonLogic.generateUUID();

	private String username;
	private String password;
	private boolean extendedLogin = false;
	private boolean isAuthenticated = false;



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

	public void addServerUrl(String key, String value) {
		urlList.put(key, value);
	}
	
	public boolean isUserAutenticated(){
		return isAuthenticated;
	}
	
	/**
	 * Only for use by AccoutManager
	 */
	protected void setAuthenticated(boolean newValue){
		this.isAuthenticated = newValue;
	}

	public SystemLogger getLogger() {
		return loudMouth;
	}
	
	public void setLoggingLevel(LoggingVerbosity logMessages){
		loudMouth.setSystemLogLevel(logMessages);
	}
}
