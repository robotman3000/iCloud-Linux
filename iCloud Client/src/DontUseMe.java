import java.util.ArrayList;

import old.icloud.services.account.AccountManager;
import old.icloud.services.account.objects.Device;
import old.icloud.services.account.objects.QuotaStatus;
import old.icloud.services.account.objects.RequestInfo;
import old.icloud.services.account.objects.StorageBlockInfo;
import old.icloud.services.contacts.ContactManager;
import old.icloud.services.contacts.objects.AddressBook;
import old.icloud.services.contacts.objects.Contact;
import old.icloud.services.contacts.objects.Photo;
import old.icloud.services.notes.NoteManager;
import old.icloud.services.notes.objects.Attachment;
import old.icloud.services.notes.objects.Note;
import icloud.UserSession;

public class DontUseMe {

	@SuppressWarnings("unused")
	public void DontRun() throws Exception {
		String username = "";
		String password = "";
		boolean extendedLogin = false;

		// Declare user
		UserSession userA = new UserSession(username, password);
		UserSession userB = new UserSession(username, password, extendedLogin);

		userA.getPassword();
		userA.getUsername();
		userA.isExtendedLogin(); //May remove
		userA.getUserTokens(); // Used to save the cookies for the validate call
		userA.addServerUrl(key, value);
		userA.getClientBuildNumber();
		userA.getServerUrl(string);
		userA.getUserConfig();
		userA.getUserData();
		userA.getSessionID();
		userA.isUserAutenticated();
		userA.setLoggingLevel(logMessages);
	}

	@SuppressWarnings({ "unused", "null" })
	public void DontRunAccounts(UserSession user) throws Exception {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		Device theDevice = null;
		StorageBlockInfo storageBlock = null;
		QuotaStatus quotaState = null;
		RequestInfo userLocale = null;
		
		String deviceId = null;
		String storageId = null;
		
		// Declare Managers
		AccountManager accountManager = new AccountManager();
		AccountManager accountManager2 = new AccountManager(announceConnections, debugEnabled);

		// AccountManager Use
		accountManager.login(user);
		accountManager.logout(user);
		accountManager.validate(user);
		accountManager.getDevices(user);
		accountManager.getFamilyDetails(user);
		accountManager.getStorageUsageInfo(user);
		
		// Getters for any of the user properties
		accountManager.getDevicesList(user);
		accountManager.getDevice(user, deviceId);
		
		accountManager.getStorageBlockList(user);
		accountManager.getStorageBlock(user, storageId);
		accountManager.getStorageUsage(user);
		accountManager.getStorageQuotaStatus(user);
		
		accountManager.getUserLocale(user);
		
		accountManager.getaDsID(user);
		accountManager.getAppleId(user);
		accountManager.getAppleIdAlias(user);
		accountManager.getDsid(user);
		accountManager.getFirstName(user);
		accountManager.getFullName(user);
		accountManager.getiCloudAppleIdAlias(user);
		accountManager.getLanguageCode(user);
		accountManager.getLastName(user);
		accountManager.getLocale(user);
		accountManager.getPrimaryEmail(user);
		accountManager.getStatusCode(user);
		accountManager.hasICloudQualifyingDevice(user);
		accountManager.isBrMigrated(user);
		accountManager.isGilligan_enabled(user);
		accountManager.isGilligan_invited(user);
		accountManager.isLocked(user);
		accountManager.isPaidDeveloper(user);
		accountManager.isPrimaryEmailVerified(user);	
		
		// Object Use
		theDevice.getImei();
		theDevice.getModel();
		theDevice.getModelDisplayName();
		theDevice.getModelLargePhotoURL1x();
		theDevice.getModelLargePhotoURL2x();
		theDevice.getModelSmallPhotoURL1x();
		theDevice.getModelSmallPhotoURL2x();
		theDevice.getName();
		theDevice.getSerialNumber();
		theDevice.getUdid();
		
		storageBlock.getDisplayColor();
		storageBlock.getDisplayLabel();
		storageBlock.getMediaKey();
		storageBlock.getUsageInBytes();
		
		quotaState.hasMaxQuotaTier();
		quotaState.isOverQuota();
		quotaState.isPaidQuota();
		
		userLocale.getCountry();
		userLocale.getRegion();
		userLocale.getTimeZone();
	}

	@SuppressWarnings("unused")
	public void DontRunNotes(UserSession user) throws Exception {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		String content = null;
		String dateModified = null;
		String folderName = null;
		String size = null;
		String subject = null;
		String uuid = null;

		String attachmentId = null;
		String noteBookID = null;
		String noteID = null;
		ArrayList<Note> retrieveNotes = null;
		
		// Declare Objects
		Note theNote = new Note();
		Attachment theAttachment = new Attachment();

		// Note Use
		theNote.getAttachments();
		theNote.getAttachmentsList();
		theNote.getAttachment(attachmentId);
		theNote.hasAttachment(attachmentId);

		theNote.getContent();
		theNote.getDateModified();
		theNote.getFolderName();
		theNote.getNoteID();
		theNote.getSize();
		theNote.getSubject();
		theNote.getUuid();

		theNote.setContent(content);
		theNote.setDateModified(dateModified);
		theNote.setFolderName(folderName);
		theNote.setSize(size); // May be changed to updateSize();
		theNote.setSubject(subject);
		theNote.updateUUID(); // May be changed to updateUUID();

		// Attachment Use
		theAttachment.getAttachmentId();
		theAttachment.getContentId();
		theAttachment.getContentType();
		theAttachment.getSize();

		// Declare NoteManager
		NoteManager noteManager = new NoteManager();
		NoteManager noteManager2 = new NoteManager(announceConnections,
				debugEnabled);

		// NoteManager Use
		noteManager.startup(user);//
		noteManager.changeset(user);//
		noteManager.createNotes(user, theNote);//
		noteManager.deleteNotes(user, theNote);//
		noteManager.updateNotes(user, theNote);//
		noteManager.retriveAttachment(user, attachmentId);//
		noteManager.retrieveNotes(user, retrieveNotes);

		noteManager.getNotebookList(user);
		noteManager.getNotesList(user, noteBookID);
		noteManager.getNote(user, noteID, noteBookID);
		
		noteManager.hasNoteBook(user, noteBookID);
		noteManager.hasNote(user, noteID, noteBookID);
	}


	@SuppressWarnings("unused")
	public void DontRunContacts(UserSession user) {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		String photoBase64 = null;
		String contactId = null;
		String groupName = null;

		// Declare Objects
		Contact theContact = new Contact();
		Photo thePhoto = new Photo(photoBase64);
		AddressBook theGroup = new AddressBook();

		// Object Use
		theGroup.setName(groupName);
		theGroup.getName();

		theGroup.addContact(theContact);
		theGroup.removeContact(theContact);
		theGroup.getContactList();

		thePhoto.getPhoto();
		thePhoto.getPhotoAsString();

	//	theContact.set();/* any contact properties */
		// Declare Managers
		ContactManager contactManager = new ContactManager();
		ContactManager contactManager2 = new ContactManager(announceConnections, debugEnabled);

		// Contact Manager Use
/*		contactManager.startup(user);
		contactManager.changeset(user);

		contactManager.getMeCard(user);
		contactManager.changeMeCard(user, theContact);

		contactManager.createGroups(user, theGroup);
		contactManager.updateGroups(user, theGroup);
		contactManager.deleteGroups(user, theGroup);

		contactManager.createContacts(user, theContact);
		contactManager.updateContacts(user, theContact);
		contactManager.deleteContacts(user, theContact);

		contactManager.importContact(user, null the .vcf contact );
		contactManager.exportContact(user, theContact);

		contactManager.getContactPhoto(theContact);

		contactManager.getGroupsList(user);
		contactManager.getContactsList(user, theGroup);
		contactManager.getContact(user, contactId);
		contactManager.getAllContacts(user);*/

	}
}
