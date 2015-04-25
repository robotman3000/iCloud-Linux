package icloud.services.contacts;

import icloud.services.BaseData;
import icloud.services.contacts.objects.AddressBook;
import icloud.services.contacts.objects.Contact;

import java.util.HashMap;
import java.util.Map;

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
