package old.icloud.services.contacts;

import java.util.ArrayList;

import old.icloud.services.BaseJson;
import old.icloud.services.contacts.objects.AddressBook;
import old.icloud.services.contacts.objects.Contact;
import old.icloud.services.contacts.objects.ContactCollection;
import old.icloud.services.contacts.objects.Deletes;
import old.icloud.services.contacts.objects.HeaderPositions;

public class ContactJson extends BaseJson{

	private String meCardId;
	private String prefToken;
	private String syncToken;
	private HeaderPositions headerPositions;
	private ArrayList<String> contactsOrder;
	private ArrayList<AddressBook> groups;
	private Deletes deletes;
	private ArrayList<ContactCollection> collections;
	private ArrayList<Contact> contacts;

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

	public ArrayList<AddressBook> getGroups() {
		return groups;
	}

	public ArrayList<ContactCollection> getCollections() {
		return collections;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public Deletes getDeletes() {
		return deletes;
	}

	public void setMeCardId(String meCardId) {
		this.meCardId = meCardId;
	}

	public void setPrefToken(String prefToken) {
		this.prefToken = prefToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public void setHeaderPositions(HeaderPositions headerPositions) {
		this.headerPositions = headerPositions;
	}

	public void setContactsOrder(ArrayList<String> contactsOrder) {
		this.contactsOrder = contactsOrder;
	}

	public void setGroups(ArrayList<AddressBook> groups) {
		this.groups = groups;
	}

	public void setDeletes(Deletes deletes) {
		this.deletes = deletes;
	}

	public void setCollections(ArrayList<ContactCollection> collections) {
		this.collections = collections;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}

}
