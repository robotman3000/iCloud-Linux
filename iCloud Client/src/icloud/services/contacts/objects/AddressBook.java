package icloud.services.contacts.objects;

import java.util.ArrayList;

public class AddressBook {
	private String groupId;
	private ArrayList<String> contactIds = new ArrayList<String>();
	private String etag;
	private String name;
	private HeaderPositions headerPositions = new HeaderPositions();
	private transient String UUID = "";

	public String getGroupId() {
		return groupId;
	}

	public ArrayList<String> getContactIds() {
		return contactIds;
	}

	public String getEtag() {
		return etag;
	}

	public String getName() {
		return name;
	}

	public HeaderPositions getHeaderPositions() {
		return headerPositions;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setContactIds(ArrayList<String> contactIds) {
		this.contactIds = contactIds;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHeaderPositions(HeaderPositions headerPositions) {
		this.headerPositions = headerPositions;
	}

	public void addContact(Contact theContact) {
		// TODO Auto-generated method stub

	}

	public void removeContact(Contact theContact) {
		// TODO Auto-generated method stub

	}

	public void getContactList() {
		// TODO Auto-generated method stub

	}

	public void updateUUID() {
		this.UUID = Integer.toString(this.groupId.hashCode());
	}

	public String getUUID() {
		return UUID;
	}
}
