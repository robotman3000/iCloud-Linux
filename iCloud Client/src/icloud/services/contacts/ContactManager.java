package icloud.services.contacts;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

import common.CommonLogic;
import common.ServerConnection;
import common.URLBuilder;
import icloud.services.BaseManager;
import icloud.services.contacts.objects.AddressBook;
import icloud.services.contacts.objects.Contact;
import icloud.user.UserSession;

public class ContactManager extends BaseManager {

	public ContactManager(){
		this.isInitialized = true;
	}
	
	public ContactManager(boolean announceConnections, boolean debugEnabled){
		this();
		this.announceConnections = announceConnections;
		this.debugEnabled = debugEnabled;
	}
	
	public void startup(UserSession user) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_startup)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_locale, user.getUserData().getAccountData().getDsInfo().getLocale())
				.addQueryString(UserSession.query_arg_order, "first,last") // this magic value might be gotten with the keyvalue server
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.GET).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void changeset(UserSession user) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_changeset)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void getMeCard(UserSession user) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_getMeCard)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN,
						user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(
						"dsid",
						user.getUserData().getAccountData().getDsInfo()
								.getDsid()).buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.GET).setServerUrl(url)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
		
	}

	public void setMeCard(UserSession user, Contact newMeCard) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ContactJson cJson = new ContactJson();
		cJson.setMeCardId(newMeCard.getContactId());
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_setMeCard)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString("dsid", user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(UserSession.query_arg_method, UserSession.PUT)
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST).setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void createGroup(UserSession user, AddressBook newGroup) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<AddressBook> groups = new ArrayList<>();
		AddressBook newOne = new AddressBook();
		newOne.setGroupId(newGroup.getGroupId());
		newOne.setName(newGroup.getName());
		groups.add(newOne);
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void deleteGroup(UserSession user, AddressBook deleteGroup) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<AddressBook> groups = new ArrayList<>();
		AddressBook newOne = new AddressBook();
		newOne.setGroupId(deleteGroup.getGroupId());
		newOne.setEtag(deleteGroup.getEtag());
		groups.add(newOne);
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(UserSession.query_arg_method, UserSession.DELETE)
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void updateGroup(UserSession user, AddressBook updateGroup) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<AddressBook> groups = new ArrayList<>();
		ContactJson cJson = new ContactJson();
		cJson.setGroups(groups);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(UserSession.query_arg_method, UserSession.PUT)
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void createContact(UserSession user, Contact newContact) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<Contact> contacts = new ArrayList<>();
		ContactJson cJson = new ContactJson();
		contacts.add(newContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}

	public void deleteContact(UserSession user, Contact deleteContact) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<Contact> contacts = new ArrayList<>();
		ContactJson cJson = new ContactJson();
		Contact newContact = new Contact();
		newContact.setEtag(deleteContact.getEtag());
		newContact.setContactId(deleteContact.getContactId());
		contacts.add(newContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(UserSession.query_arg_method, UserSession.DELETE)
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}
	
	public void updateContact(UserSession user, Contact updateContact) throws Exception{
		
		if (announceConnections) {
			// System.out.println("Connecting to: Validate Server");
		}

		ArrayList<Contact> contacts = new ArrayList<>();
		ContactJson cJson = new ContactJson();
		contacts.add(updateContact);
		cJson.setContacts(contacts);
		Gson gson = new Gson();
		
		URL url = new URLBuilder()
				.setPath(UserSession.contacts_url_groups)
				.setPort(UserSession.default_port)
				.setProtocol(UserSession.default_protocol)
				.setHost(UserSession.contacts_url_default_host)
				.addQueryString(UserSession.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(UserSession.query_arg_clientId, user.getUuid())
				.addQueryString(UserSession.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(UserSession.query_arg_clientVersion, "2.1")
				.addQueryString(UserSession.query_arg_prefToken, user.getUserConfig().getContactConfig().getPrefToken())
				.addQueryString(UserSession.query_arg_syncToken, user.getUserConfig().getContactConfig().getSyncToken())
				.addQueryString(UserSession.query_arg_method, UserSession.PUT)
				.buildURL();

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("origin", UserSession.default_header_origin);
		headersMap.put("User-Agent", UserSession.default_header_userAgent);

		ServerConnection conn = new ServerConnection(debugEnabled)
				.setRequestMethod(UserSession.POST)
				.setServerUrl(url)
				.setPayload(gson.toJson(cJson))
				.setRequestCookies(user.getUserTokens().getTokens())
				.setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user,
				new Gson().fromJson(responseData, ContactJson.class));
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		changeset(user);
		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
	}

	private void parseResponse(UserSession user, ContactJson contactJson){
		ContactConfig cConfig = user.getUserConfig().getContactConfig();
		ContactData cData = user.getUserData().getContactData();
		
		//TODO: add null checks
		cData.setMeCardId(contactJson.getMeCardId());
		cConfig.setPrefToken(contactJson.getPrefToken());
		cConfig.setSyncToken(contactJson.getSyncToken());
		cConfig.setHeaderPositions(contactJson.getHeaderPositions());
		
		parseContacts(user, contactJson);
		
		parseGroups(user, contactJson);
		
		parseDeletes(user, contactJson);
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
	
	// These are server calls 
	// there is also a server call to fetch the contact image if a url was provided
	//public void exportAsVcard(){
	//}
	
	//public void importFromVcard(){
	//}
}
