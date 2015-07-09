package old.icloud.services.contacts.objects;

import java.util.ArrayList;

public class Deletes {
	private ArrayList<String> groupIds;
	private ArrayList<String> contactIds;

	public ArrayList<String> getGroupIds() {
		return groupIds;
	}

	public ArrayList<String> getContactIds() {
		return contactIds;
	}

	public void setGroupIds(ArrayList<String> groupIds) {
		this.groupIds = groupIds;
	}

	public void setContactIds(ArrayList<String> contactIds) {
		this.contactIds = contactIds;
	}
}
