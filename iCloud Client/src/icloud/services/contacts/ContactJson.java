package icloud.services.contacts;

import icloud.services.contacts.objects.Contact;
import icloud.services.contacts.objects.ContactCollection;
import icloud.services.contacts.objects.ContactGroup;
import icloud.services.contacts.objects.HeaderPositions;

import java.util.ArrayList;

public class ContactJson {

	private final String meCardId = "";
	private final String prefToken = "";
	private final String syncToken = "";
	private final HeaderPositions headerPositions = new HeaderPositions();
	private final ArrayList<String> contactsOrder = new ArrayList<>();
	private final ArrayList<ContactGroup> groups = new ArrayList<>();
	private final ArrayList<ContactGroup> deletes = new ArrayList<>();
	private final ArrayList<ContactCollection> collections = new ArrayList<>();
	private final ArrayList<Contact> contacts = new ArrayList<>();

	public String getMeCardId() {
		return meCardId;
	}

	public String getPrefToken() {
		return prefToken;
	}

	public String getSyncToken() {
		return syncToken;
	}

	public HeaderPositions getHeaderPositions() {
		return headerPositions;
	}

	public ArrayList<String> getContactsOrder() {
		return contactsOrder;
	}

	public ArrayList<ContactGroup> getGroups() {
		return groups;
	}

	public ArrayList<ContactCollection> getCollections() {
		return collections;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public ArrayList<ContactGroup> getDeletes() {
		return deletes;
	}

}
