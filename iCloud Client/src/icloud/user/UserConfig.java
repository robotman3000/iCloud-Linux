package icloud.user;

import icloud.services.account.AccountConfig;
import icloud.services.notes.NoteConfig;

import java.util.Properties;

public class UserConfig {

	private Properties userProperties = new Properties();
	private AccountConfig accountConfig = new AccountConfig();
	private NoteConfig noteConfig = new NoteConfig();
	
	public Properties getUserProperties() {
		return userProperties;
	}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public NoteConfig getNoteConfig() {
		return noteConfig;
	}
}
