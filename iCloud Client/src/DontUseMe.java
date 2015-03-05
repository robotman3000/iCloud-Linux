import icloud.services.account.AccountManager;
import icloud.services.contacts.Contact;
import icloud.services.contacts.ContactGroup;
import icloud.services.contacts.ContactManager;
import icloud.services.contacts.Photo;
import icloud.services.notes.NoteManager;
import icloud.services.notes.Attachment;
import icloud.services.notes.Note;
import icloud.services.notes.Note.Details;
import icloud.user.UserSession;

public class DontUseMe {

	public void DontRun() throws Exception {
		String username = "";
		String password = "";
		boolean extendedLogin = false;

		// Declare user
		UserSession userA = new UserSession(); // May be removed
		UserSession userB = new UserSession(username, password);
		UserSession userC = new UserSession(username, password, extendedLogin);

		userA.getPassword();
		userA.getUsername();
		userA.isExtendedLogin();
		userA.setAuth(username, password, extendedLogin); // May be removed

		userA.isPrimaryEmailVerified();
		userA.getLastName();
		userA.getLocale();
		userA.hasICloudQualifyingDevice();
		userA.isPaidDeveloper();
		userA.getAppleId();
		userA.getDsid();
		userA.getPrimaryEmail();
		userA.brMigrated(); // May change to usingICloudDrive();
		userA.getLanguageCode();
		userA.getADsID();
		userA.isLocked();
		userA.getFullName();
		userA.getFirstName();
		userA.getAppleIdAliases();
		userA.getRegion();
		userA.getTimeZone();
		userA.getCountry();

	}

	@SuppressWarnings("unused")
	public void DontRunAccounts(UserSession user) throws Exception {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		// Declare Managers
		AccountManager accountManager = new AccountManager();
		AccountManager accountManager2 = new AccountManager(
				announceConnections, debugEnabled);

		// AccountManager Use
		accountManager.login(user);
		accountManager.logout(user);
		accountManager.validate(user);

		// Getters for any of the user properties
	}

	@SuppressWarnings("unused")
	public void DontRunNotes(UserSession user) throws Exception {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		String content = null;
		String dateModified = null;
		Details detail = null;
		String folderName = null;
		String size = null;
		String subject = null;
		String uuid = null;

		String attachmentId = null;
		String noteBookID = null;
		String noteID = null;

		// Declare Objects
		Note theNote = new Note();
		Attachment theAttachment = new Attachment();

		// Note Use
		theNote.getAttachments(); // May be changed to getAttachmentsList();
		// theNote.getAttachment(attachmentID);
		// theNote.hasAttachment(attachmentID);

		theNote.getContent();
		theNote.getDateModified();
		theNote.getDetail(); // May be removed
		theNote.getFolderName();
		theNote.getNoteID();
		theNote.getSize();
		theNote.getSubject();
		theNote.getUuid();

		theNote.setContent(content);
		theNote.setDateModified(dateModified);
		theNote.setDetail(detail); // May be Removed
		theNote.setFolderName(folderName);
		theNote.setSize(size); // May be changed to updateSize();
		theNote.setSubject(subject);
		theNote.setUuid(uuid); // May be changed to updateUUID();

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

		noteManager.getNotebookList(user);
		noteManager.getNotesList(user, noteBookID);
		noteManager.getNote(user, noteID, noteBookID);

	}

	public void DontRunContacts(UserSession user) {
		boolean debugEnabled = false;
		boolean announceConnections = false;

		String photoBase64 = null;
		String contactId = null;
		String groupName = null;

		// Declare Objects
		Contact theContact = new Contact();
		Photo thePhoto = new Photo(photoBase64);
		ContactGroup theGroup = new ContactGroup();

		// Object Use
		theGroup.setName(groupName);
		theGroup.getName();

		theGroup.addContact(theContact);
		theGroup.removeContact(theContact);
		theGroup.getContactList();

		thePhoto.getPhoto();
		thePhoto.getPhotoAsString();

		theContact.set();/* any contact properties */
		// Declare Managers
		ContactManager contactManager = new ContactManager();
		ContactManager contactManager2 = new ContactManager(announceConnections, debugEnabled);

		// Contact Manager Use
		contactManager.startup(user);
		contactManager.changeset(user);

		contactManager.getMeCard(user);
		contactManager.changeMeCard(user, theContact);

		contactManager.createGroups(user, theGroup);
		contactManager.updateGroups(user, theGroup);
		contactManager.deleteGroups(user, theGroup);

		contactManager.createContacts(user, theContact);
		contactManager.updateContacts(user, theContact);
		contactManager.deleteContacts(user, theContact);

		contactManager.importContact(user, null/* the .vcf contact */);
		contactManager.exportContact(user, theContact);

		contactManager.getContactPhoto(theContact);

		contactManager.getGroupsList(user);
		contactManager.getContactsList(user, theGroup);
		contactManager.getContact(user, contactId);
		contactManager.getAllContacts(user);

	}
}
