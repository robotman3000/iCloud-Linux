package icloud.services.contacts.objects;

import java.util.ArrayList;

public class ContactCollection {
	private ArrayList<String> groupsOrder = new ArrayList<>();
	private String etag;
	private String collectionId;
	
	public ArrayList<String> getGroupsOrder() {
		return groupsOrder;
	}

	public String getEtag() {
		return etag;
	}

	public String getCollectionId() {
		return collectionId;
	}
}
