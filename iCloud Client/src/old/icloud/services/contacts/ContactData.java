package old.icloud.services.contacts;

import java.util.HashMap;
import java.util.Map;

import old.icloud.services.BaseData;
import old.icloud.services.contacts.objects.AddressBook;
import old.icloud.services.contacts.objects.Contact;

public class ContactData extends BaseData {
	
	private String meCardId = "";
	private HashMap<String, Contact> userContacts = new HashMap<String, Contact>();
	private HashMap<String, AddressBook> userGroups = new HashMap<String, AddressBook>();

	public ContactData(){
		
	}
	
	public Map<String, Contact> getUserContacts() {
		return userContacts;
	}

	public String getMeCardId() {
		return meCardId;
	}

	public void setMeCardId(String meCardId) {
		this.meCardId = meCardId;
	}

	public HashMap<String, AddressBook> getUserGroups() {
		return userGroups;
	}
}
