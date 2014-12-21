package client;

import icloud.UserSessionInstance;
import icloud.contacts.ContactManager;

public class TestClient {

	public static void main(String[] args) throws Exception {
		boolean debugEnabled = true;

		String username = args[0];
		String password = args[1];
		
		if (debugEnabled) {
			System.out.println("Username: " + username + "\n" + "Password: " + password);
		}

		UserSessionInstance user1 = new UserSessionInstance(username, password, false);
		user1.connect();
		System.out.println("Connected");
		//ContactManager user1Contacts = user1.getContactManager();
		user1.abc123();
		

		if (debugEnabled) {
			System.out.println("The current users ID is: " + user1.getUserID());
		}
		user1.disconnect();
		System.out.println("Disconnected");
		user1.abc123();
	}

}
