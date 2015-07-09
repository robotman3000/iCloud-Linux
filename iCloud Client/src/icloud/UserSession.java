package icloud;

import icloud.json.BuildInfo;
import icloud.user.UserConfig;
import icloud.user.UserData;
import icloud.user.UserTokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import common.CommonLogic;

public class UserSession {

	private UserData userData = new UserData();
	private UserTokens userTokens = new UserTokens();
	private UserConfig userConfig = new UserConfig();

	private String clientBuildNumber = "14H40"; // Has a default value as a failsafe
	private final String UUID = CommonLogic.generateUUID();
	private Map<String, String> sessionConfig = new HashMap<String, String>();
	private Credentials authInfo;

	private boolean isAuthenticated = false;

	protected UserSession(Credentials authInfo) {
		this.authInfo = authInfo;
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

	public boolean isUserAutenticated() {
		return isAuthenticated;
	}

	/**
	 * Only for use by AccoutManager
	 */
	protected void setAuthenticated(boolean newValue) {
		this.isAuthenticated = newValue;
	}
	
	
	protected void parseBuildInfo(BuildInfo buildInfo) throws CloudException {
		//TODO: Add logic to parse all the buildinfo
		if (buildInfo.buildNumber == null) {
			throw new CloudException("Error obtaining Build Number");
		} else {
			this.clientBuildNumber = buildInfo.buildNumber;
		}
	}
	
	protected Map<String, String> getSessionConfig(){
		return sessionConfig;
	}
	
	public String getClientBuildNumber() {
		return clientBuildNumber;
	}
	
	public String getSessionID() {
		return UUID;
	}
	
	public boolean isExtendedLogin() {
		return this.authInfo.isExtendedLogin();
	}

	public String getPassword() {
		return this.authInfo.getPassword();
	}

	public String getUsername() {
		return this.authInfo.getUsername();
	}

}
