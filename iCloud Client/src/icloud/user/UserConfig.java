package icloud.user;

import icloud.services.BaseConfig;
import icloud.services.account.AccountConfig;
import icloud.services.contacts.ContactConfig;
import icloud.services.notes.NoteConfig;

import java.util.Properties;

public class UserConfig extends BaseConfig{

	private Properties userProperties = new Properties();
	private AccountConfig accountConfig = new AccountConfig();
	private NoteConfig noteConfig = new NoteConfig();
	private ContactConfig contactConfig = new ContactConfig();
	
	public Properties getUserProperties() {
		return userProperties;
	}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public NoteConfig getNoteConfig() {
		return noteConfig;
	}

	public ContactConfig getContactConfig() {
		return contactConfig;
	}
}
