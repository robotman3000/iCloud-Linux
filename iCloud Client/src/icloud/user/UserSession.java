package icloud.user;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import common.CommonLogic;

public class UserSession {

	private UserData userData = new UserData();
	private UserTokens userTokens = new UserTokens();
	private UserConfig userConfig = new UserConfig();

	private Properties urlConfig = new Properties();

	private static final String clientBuildNumber = "14H40";
	private final String UUID = CommonLogic.generateUUID();

	private String username;
	private String password;
	private boolean extendedLogin = false;

	public UserSession() {
		try {
			FileInputStream in = new FileInputStream("defaultConfig");
			urlConfig.load(in);
			System.err.println("Default Function Strings Loaded Sucsessfully");
		} catch (IOException e) {
			System.err
					.println("Error loading Default Function Strings. Loading hardcoded values instead");
			e.printStackTrace();

			// TODO: add hardcoded values;
			// TODO: Fix conf file path
			// urlConfig.put(key, value);
			// urlConfig.put("", "");
			urlConfig.put("account.url.login", "/setup/ws/1/login?");
			urlConfig.put("account.url.logout", "/setup/ws/1/logout?");
			urlConfig.put("account.url.validate", "/setup/ws/1/validate?");
			urlConfig.put("account.url.default.host", "setup.icloud.com");
			urlConfig.put("notes.url.startup", "/no/startup?");
			urlConfig.put("notes.url.createnotes", "/no/createNotes?");
			urlConfig.put("notes.url.updatenotes", "/no/updateNotes?");
			urlConfig.put("notes.url.deletenotes", "/no/deleteNotes?");
			urlConfig.put("notes.url.retriveAttachment", "/no/retrieveAttachment?");
			urlConfig.put("notes.url.changeset", "/no/changeset?");
			urlConfig.put("notes.url.default.host", "notesws.icloud.com");
			urlConfig.put("default.protocol", "https://");
			urlConfig.put("default.port", "443");
			urlConfig.put("default.requestMethod", "POST");
			urlConfig.put("query.arg.clientBN", "clientBuildNumber");
			urlConfig.put("query.arg.clientId", "clientId");
			urlConfig.put("query.arg.dsid", "dsid");
			urlConfig.put("query.arg.proxyDest", "proxyDest");
			urlConfig.put("query.arg.token", "token");
			urlConfig.put("query.arg.syncToken", "syncToken");
			urlConfig.put("query.arg.validateToken", "validateToken");
			urlConfig.put("query.arg.attachmentId", "attachmentId");

			/*
			 * try { FileOutputStream out = new
			 * FileOutputStream("defaultConfig"); urlConfig.store(out,
			 * "The url Config"); } catch (FileNotFoundException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); } catch
			 * (IOException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); }
			 */
		} finally {

		}
	}

	public UserSession(String username, String password) {
		this();
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

	@Deprecated
	public Properties getSessionConfig() {
		return urlConfig;
	}

	public void addSessionConfigOpt(String key, String value) {
		this.urlConfig.put(key, value);
	}

	public String getSessionConfigOptValue(String key) {
		return (String) this.urlConfig.get(key);
	}

	public String getServerUrl(String key) {
		return urlConfig.getProperty(key);
	}

	public void addServerUrl(String key, String value) {
		urlConfig.put(key, value);
	}

	public void setAuth(String username, String password, boolean extendedLogin) {
		if (username != null && password != null && username.length() > 0 && password.length() > 0) {
			this.username = username;
			this.password = password;
			this.extendedLogin = extendedLogin;
			// TODO: throw a invalid credentials exeption
		}
	}

	public String toString() {
		return "UserSession [userData=" + userData + ", userTokens=" + userTokens + ", userConfig="
				+ userConfig + ", urlConfig=" + urlConfig + ", UUID=" + UUID + ", username="
				+ username + ", password=" + password + ", extendedLogin=" + extendedLogin + "]";
	}

}
