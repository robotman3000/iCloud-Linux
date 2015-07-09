package old.icloud.services.contacts.objects;

import java.util.ArrayList;

public class Contact {

	private String middleName = "";
	private String lastName = "";
	private String contactId = "";
	private String etag = "";
	private String prefix = "";
	private String notes = "";
	private String firstName = "";
	private String suffix = "";
	private boolean isCompany;
	private String birthday = "";
	private String department = "";
	private String companyName = "";
	private String phoneticFirstName = "";
	private String nickName = "";
	private String phoneticLastName = "";
	private String jobTitle = "";
	private transient String UUID = "";

	private ArrayList<KeyValuePair> urls = new ArrayList<KeyValuePair>();
	private ArrayList<KeyValuePair> relatedNames = new ArrayList<KeyValuePair>();
	private ArrayList<KeyValuePair> phones = new ArrayList<KeyValuePair>();
	private ArrayList<Address> streetAddresses = new ArrayList<Address>();
	private ArrayList<KeyValuePair> emailAdresses = new ArrayList<KeyValuePair>();
	private ArrayList<IMKeyValuePair> IMs = new ArrayList<IMKeyValuePair>();
	private ArrayList<KeyValuePair> dates = new ArrayList<KeyValuePair>();
	private ArrayList<ProfileKeyValuePair> profiles = new ArrayList<ProfileKeyValuePair>();
	private Photo photo = new Photo();

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getContactId() {
		return contactId;
	}

	public String getEtag() {
		return etag;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNotes() {
		return notes;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean isCompany() {
		return isCompany;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getDepartment() {
		return department;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPhoneticFirstName() {
		return phoneticFirstName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPhoneticLastName() {
		return phoneticLastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public ArrayList<KeyValuePair> getUrls() {
		return urls;
	}

	public ArrayList<KeyValuePair> getRelatedNames() {
		return relatedNames;
	}

	public ArrayList<KeyValuePair> getPhones() {
		return phones;
	}

	public ArrayList<Address> getStreetAddresses() {
		return streetAddresses;
	}

	public ArrayList<KeyValuePair> getEmailAdresses() {
		return emailAdresses;
	}

	public ArrayList<IMKeyValuePair> getIMs() {
		return IMs;
	}

	public ArrayList<KeyValuePair> getDates() {
		return dates;
	}

	public ArrayList<ProfileKeyValuePair> getProfiles() {
		return profiles;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setCompany(boolean isCompany) {
		this.isCompany = isCompany;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPhoneticFirstName(String phoneticFirstName) {
		this.phoneticFirstName = phoneticFirstName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPhoneticLastName(String phoneticLastName) {
		this.phoneticLastName = phoneticLastName;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public void setUrls(ArrayList<KeyValuePair> urls) {
		this.urls = urls;
	}

	public void setRelatedNames(ArrayList<KeyValuePair> relatedNames) {
		this.relatedNames = relatedNames;
	}

	public void setPhones(ArrayList<KeyValuePair> phones) {
		this.phones = phones;
	}

	public void setStreetAddresses(ArrayList<Address> streetAddresses) {
		this.streetAddresses = streetAddresses;
	}

	public void setEmailAdresses(ArrayList<KeyValuePair> emailAdresses) {
		this.emailAdresses = emailAdresses;
	}

	public void setIMs(ArrayList<IMKeyValuePair> iMs) {
		IMs = iMs;
	}

	public void setDates(ArrayList<KeyValuePair> dates) {
		this.dates = dates;
	}

	public void setProfiles(ArrayList<ProfileKeyValuePair> profiles) {
		this.profiles = profiles;
	}

	public void updateUUID() {
		this.UUID = Integer.toString(this.contactId.hashCode());
	}

	public String getUUID() {
		return UUID;
	}

}
