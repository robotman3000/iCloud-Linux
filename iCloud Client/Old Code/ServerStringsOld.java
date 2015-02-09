package icloud.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerStringsOld {

	// default headers
	Map<String, String> defaultHeaders = new HashMap<String, String>();

	// function strings
	private static Properties programConfig = new Properties();
	static {
		try {
			FileInputStream in = new FileInputStream("defaultConfig");
			programConfig.load(in);
			System.err.println("Default Function Strings Loaded Sucsessfully");
		} catch (IOException e) {
			System.err.println("Error loading Default Function Strings. Loading hardcoded values instead");
			e.printStackTrace();
			
			// TODO: add hardcoded values
			//programConfig.put(key, value);
			//programConfig.put("", "");
			programConfig.put("account.url.login", "/setup/ws/1/login?");
			programConfig.put("account.url.logout", "/setup/ws/1/logout?");
			programConfig.put("account.url.validate", "/setup/ws/1/validate?");
			programConfig.put("account.url.default.host", "setup.icloud.com");
			programConfig.put("notes.url.startup", "/no/startup?");
			programConfig.put("notes.url.createnotes", "/no/createNotes?");
			programConfig.put("notes.url.updatenotes", "/no/updateNotes?");
			programConfig.put("notes.url.deletenotes", "/no/deleteNotes?");
			programConfig.put("notes.url.retriveAttachment", "");
			programConfig.put("notes.url.changeset", "/no/changeset?");
			programConfig.put("notes.url.default.host", "notesws.icloud.com");
			programConfig.put("default.protocol", "https://");
			programConfig.put("default.port", "443");
			programConfig.put("default.requestMethod", "POST");
			programConfig.put("query.arg.clientBN", "clientBuildNumber");
			programConfig.put("query.arg.clientId", "clientId");
			programConfig.put("query.arg.dsid", "dsid");
			programConfig.put("query.arg.proxyDest", "proxyDest");
			programConfig.put("query.arg.token", "token");
			programConfig.put("query.arg.syncToken", "syncToken");
			
		} finally {

		}
	}

	// query strings
	public static Properties getConfig(){
		return programConfig;
	}
}
