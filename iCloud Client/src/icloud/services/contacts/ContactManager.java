package icloud.services.contacts;

import icloud.services.BaseManager;
import icloud.services.contacts.objects.AddressBook;
import icloud.services.contacts.objects.Contact;
import icloud.user.UserSession;

import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

import common.CommonLogic;
import common.http.ServerConnection;
import common.http.URLBuilder;
import common.http.URLConfig;

public class ContactManager extends BaseManager {

	public static final String contacts_url_default_host = "contactsws.icloud.com";
	public static final String contacts_url_getMeCard = "/co/mecard/?";
	public static final String contacts_url_setMeCard = "/co/mecardid/card/?";
	public static final String contacts_url_startup = " /co/startup?";
	public static final String contacts_url_changeset = "/co/changeset?";
	public static final String contacts_url_groups = "/co/groups/card/?";
	public static final String contacts_url_contacts = "/co/contacts/card/?";
	
	private static ContactManager self = new ContactManager();
	
	public ContactManager(){
		super();
	}
	
	public void startup(UserSession user) throws Exception{

		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_startup)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_locale, "en_US"/*user.getUserData().getAccountData().getDsInfo().getLocale()*/)
				.addQueryString(URLConfig.query_arg_order, "last,first") // this magic value might be gotten with the keyvalue server // TODO: Show daddy
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(url)
				//.setPayload("")
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestCookie(new HttpCookie("X-APPLE-WEB-ID", CommonLogic.SHA1(user.getUserData().getAccountData().getDsInfo().getDsid()).toUpperCase()));
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void changeset(UserSession user) throws Exception{

		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_changeset)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST).setServerUrl(url)
				.setPayload("")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader("Referer", "https://www.icloud.com/applications/contacts/current/en-us/index.html")
				.addRequestHeader("Host", self.rootURL.toString())
				.addRequestHeader(URLConfig.default_header_userAgent)
				.addRequestCookie(new HttpCookie("X-APPLE-WEB-ID", CommonLogic.SHA1(user.getUserData().getAccountData().getDsInfo().getDsid()).toUpperCase()));
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());
	}
	
	public void getMeCard(UserSession user) throws Exception{
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_getMeCard)
				.addQueryString(URLConfig.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(
						"dsid",
						user.getUserData().getAccountData().getDsInfo()
								.getDsid()).buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		//changeset(user);
		
	}

	public void setMeCard(UserSession user, Contact newMeCard) throws Exception{

		ContactJson cJson = new ContactJson();
		cJson.setMeCardId(newMeCard.getContactId());
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_setMeCard)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(URLConfig.query_arg_method, URLConfig.PUT)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST).setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void createGroup(UserSession user, AddressBook newGroup) throws Exception{
		
		ArrayList<AddressBook> groups = new ArrayList<AddressBook>();
		AddressBook newOne = new AddressBook();
		newOne.setGroupId(newGroup.getGroupId());
		newOne.setName(newGroup.getName());
		groups.add(newOne);
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_groups)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void deleteGroup(UserSession user, AddressBook deleteGroup) throws Exception{

		ArrayList<AddressBook> groups = new ArrayList<AddressBook>();
		AddressBook newOne = new AddressBook();
		newOne.setGroupId(deleteGroup.getGroupId());
		newOne.setEtag(deleteGroup.getEtag());
		groups.add(newOne);
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_groups)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(URLConfig.query_arg_method, URLConfig.DELETE)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void updateGroup(UserSession user, AddressBook updateGroup) throws Exception{

		ArrayList<AddressBook> groups = new ArrayList<AddressBook>();
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_groups)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(URLConfig.query_arg_method, URLConfig.PUT)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void createContact(UserSession user, Contact newContact) throws Exception{
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		ContactJson cJson = new ContactJson();
		contacts.add(newContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_contacts)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();
		
		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}

	public void deleteContact(UserSession user, Contact deleteContact) throws Exception{
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		ContactJson cJson = new ContactJson();
		Contact newContact = new Contact();
		newContact.setEtag(deleteContact.getEtag());
		newContact.setContactId(deleteContact.getContactId());
		contacts.add(newContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_contacts)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(URLConfig.query_arg_method, URLConfig.DELETE)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}
	
	public void updateContact(UserSession user, Contact updateContact) throws Exception{

		ArrayList<Contact> contacts = new ArrayList<Contact>();
		ContactJson cJson = new ContactJson();
		contacts.add(updateContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder(self.rootURL)
				.setPath(contacts_url_contacts)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getUuid())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_clientVersion, "2.1")
				.addQueryString(URLConfig.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(URLConfig.query_arg_method, URLConfig.PUT)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent);
		conn.connect();

		String responseData = conn.getResponseAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
	}

	private void parseResponse(UserSession user, ContactJson contactJson){
		ContactConfig cConfig = user.getUserConfig().getContactConfig();
		ContactData cData = user.getUserData().getContactData();
		
		//TODO: add null checks
		if (contactJson.getMeCardId() != null){
			cData.setMeCardId(contactJson.getMeCardId());
		}
		
		if (contactJson.getPrefToken() != null){
			cConfig.setPrefToken(contactJson.getPrefToken());
		}
		
		if(contactJson.getSyncToken() != null){
			cConfig.setSyncToken(contactJson.getSyncToken());
/*			System.out.println("Before: " + contactJson.getSyncToken());
			String[] syncToken1 = contactJson.getSyncToken().split("@");
			StringBuilder str = new StringBuilder();
			for(String section : syncToken1){
				if(section.startsWith("S=")){
					String str1 = section.replaceFirst("S=", "");
					int int1 = Integer.parseInt(str1);
					int1++;
					str.append("S=" + int1);
				} else {
					str.append(section + "@");
				}
			}
			cConfig.setSyncToken(contactJson.getSyncToken() str.toString());
			System.out.println("After: " + str.toString());*/
		}
		
		if(contactJson.getHeaderPositions() != null){
			cConfig.setHeaderPositions(contactJson.getHeaderPositions());
		}
		
		if (contactJson.getContacts() != null){
			parseContacts(user, contactJson);
		}
		
		if(contactJson.getGroups() != null){
			parseGroups(user, contactJson);
		}
		
		if(contactJson.getDeletes() != null){
			parseDeletes(user, contactJson);
		}
	}
	
	//TODO: make one parse method that uses generics
	private void parseContacts(UserSession user, ContactJson contactJson){
		ContactData cData = user.getUserData().getContactData();
		Iterator<Contact> it = contactJson.getContacts().iterator();
		while(it.hasNext()){
			Contact theContact = it.next();
			theContact.updateUUID();
			cData.getUserContacts().put(theContact.getUUID(), theContact);	
		}
	}
	
	private void parseGroups(UserSession user, ContactJson contactJson){
		ContactData cData = user.getUserData().getContactData();
		Iterator<AddressBook> it = contactJson.getGroups().iterator();
		while(it.hasNext()){
			AddressBook theAddressBook = it.next();
			theAddressBook.updateUUID();
			cData.getUserGroups().put(theAddressBook.getUUID(), theAddressBook);	
		}
	}
	
	private void parseDeletes(UserSession user, ContactJson contactJson){
		ContactData cData = user.getUserData().getContactData();

		Iterator<String> itContact = contactJson.getDeletes().getContactIds().iterator();
		Iterator<String> itGroup = contactJson.getDeletes().getGroupIds().iterator();
		
		while (itContact.hasNext()){
			String theDeleted = itContact.next();
			if (cData.getUserContacts().containsKey(theDeleted)){
				cData.getUserContacts().remove(theDeleted);
			} else {
				System.err.println("Did not remove non existant contact: " + theDeleted);
			}
		}
		
		while (itGroup.hasNext()){
			String theDeleted = itGroup.next();
			if (cData.getUserGroups().containsKey(theDeleted)){
				cData.getUserGroups().remove(theDeleted);
			} else {
				System.err.println("Did not remove non existant group: " + theDeleted);
			}
		}
		
	}

	@Override
	public void setRootURL(String newUrl) {
		try {
			self.rootURL = new URL(newUrl);
		} catch (MalformedURLException e) {
			//TODO: make this use the fallback data
			e.printStackTrace();
		}
	}
	
	// These are server calls 
	// there is also a server call to fetch the contact image if a url was provided
	//public void exportAsVcard(){
	//}
	
	//public void importFromVcard(){
	//}
}
