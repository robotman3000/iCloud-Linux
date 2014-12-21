package client;

import icloud.UserSessionInstance;
//import icloud.contacts.ContactManager;
//import icloud.contacts.AddressBook;
//import icloud.contacts.ContactManager;

public class TestClient {

	public static void main(String[] args) throws Exception {
		boolean debug = true;

		String username = args[0];
		String password = args[1];
		if (debug) {
			System.out.println("Username: " + username + "\n" + "Password: " + password);
		}

		UserSessionInstance user1 = new UserSessionInstance(username, password, false);
		user1.connect();
		System.out.println("Connected");
		user1.disconnect();
		System.out.println("Disconnected");
		
		if (debug) {
			System.out.println("The current users ID is: " + user1.getUserID());
		}

		
		//ContactManager user1ContactsManager = new ContactManager(user1);
		
		/*
		 * System.out.println("Configuring Contacts..."); //ContactManager
		 * contacts = new ContactManager(user1);
		 * 
		 * System.out.println("Getting All Address Books and Contacts...");
		 * //AddressBook[] addrBooks = contacts.getAddressBooks();
		 * 
		 * //addrBooks = contacts.getAddressBooks();
		 * //contacts.getContactsList(addrBooks[0]);
		 */

	}

}
