package icloud.user;

import old.icloud.services.BaseData;
import old.icloud.services.account.AccountData;
import old.icloud.services.contacts.ContactData;
import old.icloud.services.notes.NoteData;

public class UserData extends BaseData{

	private AccountData accountData = new AccountData();
	private NoteData noteData = new NoteData();
	private ContactData contactData = new ContactData();

	// we put things like notes and contacts here
	public AccountData getAccountData() {
		return accountData;
	}

	public NoteData getNoteData() {
		return noteData;
	}
	
	public String toString() {
		return "UserData [accountData=" + accountData + ", noteData=" + noteData + "]";
	}

	public ContactData getContactData() {
		return contactData;
	}
}
