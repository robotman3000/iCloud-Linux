package icloud.user;

import java.util.Properties;

import old.icloud.services.BaseConfig;
import old.icloud.services.account.AccountConfig;
import old.icloud.services.contacts.ContactConfig;
import old.icloud.services.notes.NoteConfig;

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
