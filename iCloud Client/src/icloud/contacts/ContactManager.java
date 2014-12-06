package icloud.contacts;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import javax.script.*;

import icloud.BaseManager;
import icloud.UserSessionInstance;
import icloud.ServerConnection;

public class ContactManager extends BaseManager {

	private static final String XML_GETCONTACTS = "\r\n<card:addressbook-query xmlns:d=\"DAV:\" xmlns:card=\"urn:ietf:params:xml:ns:carddav\">\r\n    <d:prop>\r\n        <d:getetag />\r\n        <card:address-data />\r\n    </d:prop>\r\n</card:addressbook-query>\r\n";

	private String contacturl;
	private ServerConnection serverconnection = new ServerConnection();

	public ContactManager(UserSessionInstance login) throws Exception {
		setContacturl("/" +login.getUserID() + getCarddavhome());
		ServerConnection serverconnection = new ServerConnection();

		Contact[] usercontacts = getAllContacts(login.getAuthKey());

		// now sort usercontacts into address books and distory the vCards that are actually address books

	}

	private String getContacturl() {
		return contacturl;
	}

	private void setContacturl(String contacturl) {
		this.contacturl = contacturl;
	}

	private Contact[] getAllContacts(String authKey) throws Exception {

		//StringEntity entity = new StringEntity(XML_GETCONTACTS);
		StringEntity entity = new StringEntity("");

		
		HttpUriRequest httpmethod = RequestBuilder.create("GET").build();
		//httpmethod.addHeader("Accept", "text/xml");
		//httpmethod.addHeader("Authorization", "Basic " + authKey);
		httpmethod.addHeader("Test", "test");
		Header[] headers = httpmethod.getAllHeaders();
		
		String[] urlpath = new String[1];
		urlpath[0] = contacturl;

		//serverconnection.connect("REPORT", "https", "contacts.icloud.com", 443, urlpath, entity, headers);
		serverconnection.connect("GET", "https", "www.icloud.com", 443, entity, headers);

		String contactsToParse = serverconnection.getResponseBody();
		
		System.out.println(contactsToParse);

		ScriptEngineManager testew = new ScriptEngineManager();
		ScriptEngine abc = testew.getEngineByName("javascript");
		System.out.println(abc.eval(contactsToParse));
		
		
		
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

	public void moveContact(AddressBook addrbook, AddressBook newaddrbook) {

	}

	public Contact editContact(AddressBook addrbook, Contact editContact) {

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

	public void renameAddressBook(AddressBook addrbook) {

	}

	public void deleteAddressBook() {

	}
}
