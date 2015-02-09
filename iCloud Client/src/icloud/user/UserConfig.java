package icloud.user;

import icloud.services.account.AccountConfig;
import icloud.services.notes.NoteConfig;

import java.util.Properties;

public class UserConfig {

	private Properties userProperties = new Properties();
	//private Map<String, Map<String, String>> serversList = new HashMap<String, Map<String, String>>();
	
	private AccountConfig accountConfig = new AccountConfig();
	private NoteConfig noteConfig = new NoteConfig();
	
	public Properties getUserProperties() {
		return userProperties;
	}

	//public Map<String, Map<String, String>> getServersList() {
		//return serversList;
	//}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public NoteConfig getNoteConfig() {
		return noteConfig;
	}
}
