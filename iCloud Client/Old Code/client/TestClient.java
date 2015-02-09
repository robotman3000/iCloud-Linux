package client;

import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

import common.CommonLogic;
import icloud.services.ServerStrings;
import icloud.services.UserSessionInstance;
import icloud.services.account.AccountManager;
import icloud.services.notes.Note;
import icloud.services.notes.Note.Details;
import icloud.services.notes.NoteManager;
import icloud.user.UserData;
import icloud.user.UserSession;
import icloud.user.UserTokens;

public class TestClient {

	UserSessionInstance user1;
	UserTokens user1Tokens;
	NoteManager noteMgr;
	protected boolean connected = false;
	protected boolean hasNoteMan = false;

	public static void main(String[] args) throws Exception {
		UserSession userA = new UserSession();
		// UserSession userB = new UserSession();
		//UserSession userA = new UserSession();
		
		AccountManager accountsManager = new AccountManager(true, true);
		NoteManager noteManager = new NoteManager(true, true);

		//Note test = new Note("1", "2", "3", "4", "5", "6");
		//Gson test2 = new Gson();
		//CommonLogic.printJson(test2.toJson(test));
		
		accountsManager.login(userA);
		System.out.println("User A: " + userA.getUserConfig().getUserProperties().toString());

		noteManager.startup(userA);
		Iterator<String> it = noteManager.getNotesList(userA, "main").iterator();
		while(it.hasNext()){
			String str = it.next();
			System.out.println(noteManager.getNote(userA, str, "main").toString());
		}
		Scanner abc = new Scanner(System.in);
		System.out.println("Waiting for enter to be pressed...");
		abc.next();
		noteManager.changeset(userA);
		Iterator<String> it2 = noteManager.getNotesList(userA, "main").iterator();
		while(it2.hasNext()){
			String str = it2.next();
			System.out.println(noteManager.getNote(userA, str, "main").toString());
		}
		//Set<String> list = noteManager.getNotebookList(userA);
		//String var = list.iterator().next();
		//noteManager.getNotesList(userA, var);

		// userB.getUserTokens().updateTokens(userA.getUserTokens().getTokens());
		// accountsManager.validate(userB);
		// System.out.println("User B: " +
		// userB.getUserConfig().getUserProperties().toString());

		//Note tesa = new Note();
		//Details nDetails = tesa.new Details();
		//nDetails.setContent("the notes content \n line 2");
		//noteManager.createNotes(userA, new Note("2015-01-09T08:18:33-05:00", CommonLogic.generateUUID(), "size", "/", "A new note", nDetails));
		System.out.println("Exiting");
		accountsManager.logout(userA);
		// accountsManager.logout(userB);
	}

	private void oldMain() {
		/*
		 * TestClient var = new TestClient();
		 * 
		 * boolean debugEnabled = true; boolean announceConnections = true;
		 * 
		 * if (args.length != 2) { System.exit(1); } String username = args[0];
		 * String password = args[1];
		 * 
		 * if (debugEnabled) { System.out.println("Username: " + username + "\n"
		 * + "Password: " + password); }
		 * 
		 * if (debugEnabled) { var.printUserProps(); }
		 */

		// NoteManager user1Notes = new NoteManager(user1, user1Tokens,
		// debugEnabled);

		// var.printNotes(user1Notes);

		// Note newNote = new Note("2014-12-31T10:11:01-05:00",
		// CommonLogic.generateUUID(), "/", "18", "abc123", "test content");
		// user1Notes.createNote(newNote, newNote.getNoteID());

		/*
		 * Scanner reader = new Scanner(System.in);
		 * System.out.println("Enter the Note to delete"); // get user input for
		 * a String a = reader.next(); System.out.println(a); reader.close();
		 * 
		 * user1Notes.deleteNote(a);
		 * 
		 * var.printNotes(user1Notes);
		 */

		/*
		 * String username; String password; boolean extendedlogin; User icUser
		 * = new User(username, password, extendedlogin); //or Properties
		 * properties = new Properties(); User icUser2 = new User(properties);
		 * 
		 * AccountManager accounts = new AccountManager(icUser);
		 */

	}

	public boolean isConnected() {
		return connected;
	}

	public void openConnection(String username, String password, boolean extendedLogin,
			boolean debugEnabled, boolean announceConnections) throws Exception {
		if (connected) {
			return;
		}
		user1 = new UserSessionInstance(username, password, extendedLogin, debugEnabled,
				announceConnections);
		user1.connect();
		System.out.println("Connected");
		connected = true;
		user1Tokens = user1.getTokenManager();
	}

	public void closeConnection() throws Exception {
		if (connected != true) {
			return;
		}
		user1.disconnect();
		System.out.println("Disconnected");
		connected = false;
		hasNoteMan = false;
		user1 = null;
		user1Tokens = null;
	}

	public void printUserProps() {
		if (connected != true) {
			return;
		}
		System.out.println("The current users ID is: " + user1.getUserID());
		System.out.println("The current users Apple ID is: " + user1.getAppleID());
		System.out.println("The current users First Name is: " + user1.getFirstName());
		System.out.println("The current users Last Name is: " + user1.getLastName());
		System.out.println("The current users Full Name is: " + user1.getFullName());
		System.out.println("The current users Locale is: " + user1.getLocale());
	}

	public void createNoteManager() throws Exception {
		if (connected != true) {
			return;
		}
		noteMgr = new NoteManager(user1, user1Tokens, false);
		hasNoteMan = true;

	}

	public void printNotes() {
		if (connected == true && hasNoteMan == true) {
			// TODO Auto-generated method stub
			Set<String> notebookList = noteMgr.getNotebookList();
			Iterator<String> iterator = notebookList.iterator();

			CommonLogic.splitOut();
			System.out.println("Begin Note Echo");
			CommonLogic.splitOut();
			while (iterator.hasNext()) {
				String nextNotebook = iterator.next();
				Set<String> notesList = noteMgr.getNotesList(nextNotebook);
				Iterator<String> iter = notesList.iterator();

				System.out.println("Note Manager \"toString\"" + "\n" + noteMgr.toString());

				while (iter.hasNext()) {

					String nextNote = iter.next();
					Note tempNote = noteMgr.getNote(nextNote, nextNotebook);

					Document noteContent = Jsoup.parse(tempNote.getContent());

					System.out.println("Current Note ID: " + tempNote.getNoteID());
					System.out.println("Current Note Key: " + tempNote.getUuid());
					System.out.println("Current Note Folder: " + tempNote.getFolderName());
					System.out.println("Current Note Date Modified: " + tempNote.getDateModified());
					System.out.println("Current Note Size: " + tempNote.getSize());
					System.out.println("Current Note Subject: " + tempNote.getSubject());
					System.out.println("Current Note Content: " + noteContent.toString());

				}
			}
			CommonLogic.splitOut();
			System.out.println("End Note Echo");
			CommonLogic.splitOut();
		}
	}

	public void deleteNote() throws Exception {
		if (connected == true && hasNoteMan == true) {
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter the Note to delete");
			// get user input for a
			String a = reader.next();
			System.out.println(a);
			reader.close();

			noteMgr.deleteNote(a);
		}
	}

	public void createNote() {
		// TODO Auto-generated method stub
		System.out.println("Unimplemented");

	}

	public void manualCallNoteChangeset() throws Exception {
		noteMgr.getChanges();
	}
}
