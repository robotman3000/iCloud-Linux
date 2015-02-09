package icloud.services.contacts;

public class AddressBook {

	private String addressBookName;
	private String UID;
	private String revision;
	private String period;
	private String version;
	private Contact[] members;

	public AddressBook(String addressBookName, String UID, String revision, String period, String version, Contact[] members) {

		addressBookName = this.addressBookName;
		UID = this.UID;
		revision = this.revision;
		period = this.period;
		version = this.version;
		members = this.members;

	}

	public Contact[] getMembers() {
		return members;
	}

	public void setMembers(Contact[] members) {
		this.members = members;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getAddressBookName() {
		return addressBookName;
	}

	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}

}
