import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import common.CommonLogic;
import icloud.services.account.AccountManager;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;
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
		main.interactiveUserPrint();
		main.interactiveNotes();
		main.signOut();
	}

	private void interactiveUserPrint() {
		if (queryUserBoolean("Do you want to fetch & print the user config?")) {
			//loudMouth.log("Fetching user data", this.getClass().getName(), SystemLogger.LoggingVerbosity.INFO);
			//loudMouth.log("Hi", getClass().getCanonicalName(), LoggingVerbosity.ERROR);
			try {
				accountManager.getDevices(user);
				accountManager.getFamilyDetails(user);
				accountManager.getStorageUsageInfo(user);
				log("Fetched user config; Printing config now");
				//log(" : ");
				log("First Name: " + accountManager.getFirstName(user));
				log("Last Name: " + accountManager.getLastName(user));
				log("Full Name: " + accountManager.getFullName(user));				
				log("aDsID: " + accountManager.getaDsID(user));
				log("DSID: " + accountManager.getDsid(user));				
				log("Primary Email: " + accountManager.getPrimaryEmail(user));
				log("Apple ID: " + accountManager.getAppleId(user));
				log("Apple ID Alias: " + accountManager.getAppleIdAlias(user));
				log("iCloud Apple ID Alias: " + accountManager.getiCloudAppleIdAlias(user));				
				log("Language Code: " + accountManager.getLanguageCode(user));
				log("Locale: " + accountManager.getLocale(user));
				log("Status Code: " + accountManager.getStatusCode(user));
				log("Has iCloud Supported Device: " + accountManager.hasICloudQualifyingDevice(user));
				log("Br Migrated: " + accountManager.isBrMigrated(user));
				log("Gilligan Enabled: " + accountManager.isGilligan_enabled(user));
				log("Gilligan Invited: " + accountManager.isGilligan_invited(user));
				log("Is Account Locked: " + accountManager.isLocked(user));
				log("Is Paid Developer: " + accountManager.isPaidDeveloper(user));
				log("Is Primary Email Verified: " + accountManager.isPrimaryEmailVerified(user));	
				System.out.println();
				for (String deviceKey : accountManager.getDevicesList(user)){
					log("Has Device: " + deviceKey);
					Device theDevice = accountManager.getDevice(user, deviceKey);
					
					tab("", "IMEI: " + theDevice.getImei());
					tab("", "Model: " + theDevice.getModel());
					tab("", "Display Name: " + theDevice.getModelDisplayName());
					tab("", "Name: " + theDevice.getName());
					tab("", "Serial Number: " + theDevice.getSerialNumber());
					tab("", "UDID: " + theDevice.getUdid());
					//log(": " + theDevice.getModelLargePhotoURL1x());
					//log(": " + theDevice.getModelLargePhotoURL2x());
					//log(": " + theDevice.getModelSmallPhotoURL1x());
					//log(": " + theDevice.getModelSmallPhotoURL2x());
				}
				System.out.println();
				StorageUsageInfo storeInfo = accountManager.getStorageUsage(user);
				log("Storage Information");
				tab("", "Commerce Storage: " + storeInfo.getCommerceStorageInBytes());
				tab("", "Comp Storage: " + storeInfo.getCompStorageInBytes());
				tab("", "Total Storage: " + storeInfo.getTotalStorageInBytes());
				tab("", "Used Storage: " + storeInfo.getUsedStorageInBytes());
				
				log("Storage Blocks");
				for(String storeBlock : accountManager.getStorageBlockList(user)){
					StorageBlockInfo block = accountManager.getStorageBlock(user, storeBlock);
					tab("", "Display Color: " + block.getDisplayColor());
					tab("", "Display Label: " + block.getDisplayLabel());
					tab("", "Media Key: " + block.getMediaKey());
					tab("", "Usage in Bytes: " + Integer.toString(block.getUsageInBytes()));
					System.out.println("");
				}

				QuotaStatus quota = accountManager.getStorageQuotaStatus(user);
				log("Has Max Quota Tier: " + quota.hasMaxQuotaTier());
				log("Is Over Quota: " + quota.isOverQuota());
				log("Is Paid Quota: " + quota.isPaidQuota());
				
				RequestInfo userLocale = accountManager.getUserLocale(user);
				log("User Country: " + userLocale.getCountry());
				log("User Region: " + userLocale.getRegion());
				log("User Time Zone: " + userLocale.getTimeZone());
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
				Calendar currCalendar = Calendar.getInstance();
				Date currDate = currCalendar.getTime();
				String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(currDate);
				
				theNote.setContent("This demo note was created by iCloud-Linux at: " + date);
				theNote.setDateModified(date);
				theNote.setFolderName("/");
				theNote.setSize("0"); // May be changed to updateSize();
				theNote.setSubject("iCloud-Linux Demo Note");
				theNote.setNoteID(CommonLogic.generateUUID());
				ArrayList<Note> notes = new ArrayList<Note>();
				notes.add(theNote);
				noteManager.createNotes(user, notes);
				log("Created a demo note");
				log("Please check an official Apple iCloud supported device to verify the note was created");
			} else {
				log("Skipped creating a demo note");
			}
			
			if (queryUserBoolean("Do you want to delete a note?")){
				for(String noteBookStr : noteManager.getNotebookList(user)){
					System.out.println("In notebook:" + noteBookStr);
					for(String noteStr : noteManager.getNotesList(user, noteBookStr)){
						Note theNote = noteManager.getNote(user, noteStr, noteBookStr);
						System.out.println("Note UUID: " + theNote.getUuid() + " Note Subject: " + theNote.getSubject());		
					}
				}
				String response = queryUserString("Which note would you like to delete?");
				for(String noteBookStr : noteManager.getNotebookList(user)){
					if(noteManager.hasNote(user, response, noteBookStr)){
						log("Deleting selected note");
						ArrayList<Note> notes = new ArrayList<Note>();
						notes.add(noteManager.getNote(user, response, noteBookStr));
						noteManager.deleteNotes(user, notes);
						log("Note deleted");
						log("Please check an official Apple iCloud supported device to verify the note was deleted");
					} else {
						log("Selected note doesn't seem to exist; Moving on");
					}
				}
			} else {
				log("Skipped deleting a note");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void signIn(String username, String password) {
		try {
			//loudMouth.log("Attempting to authenticate user", this.getClass().getName(), SystemLogger.LoggingVerbosity.INFO);
			//loudMouth.log("Using username: " + username, this.getClass().getName(), SystemLogger.LoggingVerbosity.INFO);
			//loudMouth.log("Using password: " + password, this.getClass().getName(), SystemLogger.LoggingVerbosity.INFO);
			user = new UserSession(username, password);
			//SystemLogger log = user.getLogger(); 
			//log = loudMouth;
			//loudMouth.setSystemLogLevel(SystemLogger.LoggingVerbosity.DEVELOPER);
			accountManager.login(user);
			//loudMouth.log("Login succeeded; User is now signed in", this.getClass().getName(), SystemLogger.LoggingVerbosity.INFO);
		} catch (Exception e) {
			// TODO Catch Invalid Credintals Exeption
			//loudMouth.log("Login failed; User is not signed in", this.getClass().getName(), SystemLogger.LoggingVerbosity.ERROR);
			//loudMouth.log("Invalid login info provided", this.getClass().getName(), SystemLogger.LoggingVerbosity.ERROR);
			e.printStackTrace();
		}
	}

	private void signOut() {
		try {
			log("Attempting to deauthenticate user");
			//accountManager.logout(user);
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
		@SuppressWarnings("resource")
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
