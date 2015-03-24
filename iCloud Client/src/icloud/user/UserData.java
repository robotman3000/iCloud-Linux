package icloud.user;

import icloud.services.account.AccountData;
import icloud.services.contacts.ContactData;
import icloud.services.notes.NoteData;

public class UserData {

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
