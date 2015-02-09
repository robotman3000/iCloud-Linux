package icloud.services.contacts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import common.ServerConnection;
import icloud.services.BaseManager;
import icloud.user.UserSession;

public class ContactManager extends BaseManager {

	Map<String, AddressBook> addressBooks = new HashMap<String, AddressBook>();
	
	public ContactManager(UserSession user) throws Exception {
		ServerConnection conn = new ServerConnection();
		
	//	URL httpUrl = new URL(user.getContactServer() + "/co/startup?" + "clientBuildNumber=" + clientBnum + "&" + "clientId=" + UUID + "&dsid=" + "8084583249" + "&locale=" + "en_US" + "&order=" + "last,first" + "&clientVersion" + "2.1");

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");

		//conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		//conn.setRequestCookies(cookies);
		conn.connect();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseDataAsString());
		String result2 = gson.toJson(je);

		boolean debugenabled = true;
		if (debugenabled ) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}
		
		
	}
	
	public ContactManager(String serverUrl, String clientBuildNumber, String clientID, String dsid, String locale, String order, String clientVersion) throws MalformedURLException{
		
		ServerConnection conn = new ServerConnection();
		
		URL httpUrl = new URL(serverUrl + "/co/startup?" + "clientBuildNumber=" + clientBuildNumber + "&" + "clientId=" + clientID + "&dsid=" + dsid + "&locale=" + locale + "&order=" + order + "&clientVersion" + clientVersion);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		//n.setRequestCookies(cookies);
		//conn.connect();
		
	}
	
	@SuppressWarnings("unused")
	private Contact[] getAllContacts(String authKey) throws Exception {

		// get all <href> links out of contactToParse
		// GET all href url content and sort as contacts or group; put all group href urls in a new list
		

		Contact[] cleanContacts = null;

		return cleanContacts;
	}

	public Contact[] getContactsList(AddressBook addrbook) {

		return null;
	}

	public Contact getContact(AddressBook addrbook, Contact contactname) {

		return null;
	}

	public void moveContact(AddressBook addrbook, AddressBook newaddrbook) { // will use delete and save

	}

	public Contact editContact(AddressBook addrbook, Contact editContact) { // will use delete and save

		return null;
	}

	public void deleteContact(AddressBook addrbook, Contact deleteContact) {

	}

	public void saveContact(AddressBook addrbook, Contact saveContact) {

	}

	
	public AddressBook[] getAddressBooks() {

		return null;
	}

	public void saveAddressBook(AddressBook addrbook) {

	}

	public void deleteAddressBook(AddressBook addrbook) {

	}
}
