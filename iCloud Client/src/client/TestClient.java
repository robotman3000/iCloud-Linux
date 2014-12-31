package client;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import icloud.CommonLogic;
import icloud.UserSessionInstance;
import icloud.UserTokenManager;
import icloud.notes.Note;
import icloud.notes.NoteManager;

public class TestClient {

	public static void main(String[] args) throws Exception {
		
		TestClient var = new TestClient();
		
		boolean debugEnabled = true;
		boolean announceConnections = true;
		
		if (args.length != 2) {
			System.exit(1);
		}
		String username = args[0];
		String password = args[1];

		if (debugEnabled) {
			System.out.println("Username: " + username + "\n" + "Password: " + password);
		}

		UserSessionInstance user1 = new UserSessionInstance(username, password, false, debugEnabled, announceConnections);
		user1.connect();
		System.out.println("Connected");
		
		if (debugEnabled) {
			System.out.println("The current users ID is: " + user1.getUserID());
			System.out.println("The current users Apple ID is: " + user1.getAppleID());
			System.out.println("The current users First Name is: " + user1.getFirstName());
			System.out.println("The current users Last Name is: " + user1.getLastName());
			System.out.println("The current users Full Name is: " + user1.getFullName());
			System.out.println("The current users Locale is: " + user1.getLocale());
		}
		
		UserTokenManager user1Tokens = user1.getTokenManager();
		
		NoteManager user1Notes = new NoteManager(user1, user1Tokens, debugEnabled);
		
		var.printNotes(user1Notes);
		
		//Note newNote = new Note("2014-12-31T10:11:01-05:00", CommonLogic.generateUUID(), "/", "18", "abc123", "test content");
		//user1Notes.createNote(newNote, newNote.getNoteID());
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the Note to delete");
		//get user input for a
		String a = reader.next();
		System.out.println(a);
		reader.close();
		
		user1Notes.deleteNote(a);
		
		var.printNotes(user1Notes);
		
		user1.disconnect();
		System.out.println("Disconnected");
		
	}

	private void printNotes(NoteManager user1Notes) {
		// TODO Auto-generated method stub
		Set<String> notebookList = user1Notes.getNotebookList();
		Iterator<String> iterator = notebookList.iterator();
		
		CommonLogic.splitOut();
		System.out.println("Begin Note Echo");
		CommonLogic.splitOut();
		while (iterator.hasNext()){
			String nextNotebook = iterator.next();
			Set<String> notesList = user1Notes.getNotesList(nextNotebook);
			Iterator<String> iter = notesList.iterator();
			
			System.out.println("Note Manager \"toString\"" + "\n" + user1Notes.toString());
			
			while(iter.hasNext()){

				String nextNote = iter.next();
				Note tempNote = user1Notes.getNote(nextNote, nextNotebook);
				
				Document noteContent = Jsoup.parse(tempNote.getContent());
				
				System.out.println("Current Note ID: " + tempNote.getNoteID());
				System.out.println("Current Note Folder: " + tempNote.getFolder());
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
