import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import icloud.services.account.AccountManager;
import icloud.services.notes.NoteManager;
import icloud.services.notes.objects.Attachment;
import icloud.services.notes.objects.Note;
import icloud.user.UserSession;

public class RunMe {

	UserSession user;
	AccountManager accountManager = new AccountManager();

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out
					.println("Usage: (launch command) theUser@icloud.com theUsersPassword");
			System.exit(1);
		}
		RunMe main = new RunMe();
		main.signIn(args[0], args[1]);
		// main.interactiveUserPrint();
		main.interactiveNotes();
		main.signOut();
	}

	private void interactiveUserPrint() {
		String input = readIn("Do you want to fetch & print the user config? (yes/no)");
		if (input.equalsIgnoreCase("yes")) {
			log("Fetching user data");
			try {
				accountManager.getDevices(user);
				accountManager.getFamilyDetails(user);
				accountManager.getStorageUsageInfo(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			log("User data fetched");
			log("Printing user config to stdout");
		} else {
			log("Skiping user config print");
		}
	}

	private void interactiveNotes() {
		NoteManager noteManager = new NoteManager();
		try {
			noteManager.startup(user);
			if(queryUserBoolean("Do you want to print the users notes?")){
				log("Will print users notes");
				boolean fetchAttachments = false;
				if(queryUserBoolean("Do you want to fetch the Attachments contained in the Notes?")){
					fetchAttachments = true;
					log("Will fetch attachments contained in the notes");
				} else {
					log("Will not fetch attachments contained in the notes");
				}
				
				Set<String> noteBooks = noteManager.getNotebookList(user);
				for (String noteBookStr : noteBooks){
					System.out.println("Notebook " + noteBookStr);
					Set<String> notesList = noteManager.getNotesList(user, noteBookStr);
					for(String noteStr : notesList){
						System.out.println("Contains note " + noteStr);
						Note theNote = noteManager.getNote(user, noteStr, noteBookStr);
						tab("", "Date Modified: " + theNote.getDateModified());
						tab("", "Folder Name: " + theNote.getFolderName());
						tab("", "Note ID: " + theNote.getNoteID());
						tab("", "Note Size: " + theNote.getSize());
						tab("", "Note UUID: " + theNote.getUuid());
						tab("", "Note Subject: " + theNote.getSubject());
						if(theNote.getContent() == null){
							System.out.println("Downloading notes content...");
							ArrayList<Note> notesListArray = new ArrayList<Note>();
							notesListArray.add(theNote);
							noteManager.retrieveNotes(user, notesListArray);
							Note theNote2 = noteManager.getNote(user, noteStr, noteBookStr);
							tab("", "Content: \n" + theNote2.getContent());
						} else {
							tab("", "Content: \n" + theNote.getContent());
						}
						
						if (theNote.getAttachments() != null && !theNote.getAttachments().isEmpty()){
							tab("", "Has " + theNote.getAttachments().size() +  " Attachments");
							ArrayList<Attachment> attachments = theNote.getAttachments();
							for (Attachment attach : attachments){
								tab("", "Attachment:");
								tab("\t", "Attachment ID: " + attach.getAttachmentId());
								tab("\t", "Attachment Size: " + attach.getSize());
								tab("\t", "Content ID: " + attach.getContentId());
								tab("\t", "Content Type: " + attach.getContentType());
								
								if(fetchAttachments){
									tab("\t", "Attachment Content: ");
									BufferedImage attachImage = noteManager.retriveAttachment(user, attach.getAttachmentId());
									tab("\t", attachImage.toString());
								}
							}
						}
					}
				}
			} else {
				log("Skipped printing the users notes");				
			}
			
			if (queryUserBoolean("Do you want to create a demo note?")){
				log("Creating a demo note");
				Note theNote = new Note();
				noteManager.createNotes(user, theNote);
			} else {
				log("Skipped creating a demo note");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void signIn(String username, String password) {
		try {
			log("Attempting to authenticate user");
			log("Using username: " + username);
			log("Using password: " + password);
			user = new UserSession(username, password);
			accountManager.login(user);
			log("Login succeeded; User is now signed in");
		} catch (Exception e) {
			// TODO Catch Invalid Credintals Exeption
			log("Login failed; User is not signed in");
			err("Invalid login info provided");
			e.printStackTrace();
		}
	}

	private void signOut() {
		try {
			log("Attempting to deauthenticate user");
			accountManager.logout(user);
			log("Logout succeeded; User is now signed out");
		} catch (Exception e) {
			// TODO Catch any execptions thrown
			log("Logout failed; User is not signed out");
			e.printStackTrace();
		}
	}

	private void log(String message) {
		System.out.println(">>> " + message);
	}

	private void err(String message) {
		System.err.println("!!! " + message);
	}

	private void tab(String prefix, String message){
		System.out.println(prefix + "\t" + message);
	}
	
	private String readIn(String message) {
		Scanner in = new Scanner(System.in);
		log(message);
		System.out.print("<<< ");
		String resp = in.nextLine();
		//in.close();
		return resp;
	}

	private boolean queryUserBoolean(String message){
		String answer = queryUserString(message + " (yes/no)");
		if (answer.equalsIgnoreCase("yes")){
			return true;
		}
		return false;
	}

	private String queryUserString(String message) {
		String input = readIn(message);
		return input;
	}
}
