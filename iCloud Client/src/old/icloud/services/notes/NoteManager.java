package old.icloud.services.notes;

import icloud.UserSession;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import old.icloud.services.BaseManager;
import old.icloud.services.notes.objects.Note;
import old.icloud.services.notes.objects.NoteBook;

import com.google.gson.Gson;

import common.http.ServerConnection;
import common.http.URLBuilder;
import common.http.URLConfig;

public class NoteManager extends BaseManager {

	// TODO: make each of the server call methods have several sets of arguments
	public static final String notes_url_default_host = "notesws.icloud.com";
	public static final String notes_url_startup = "/no/startup?";
	public static final String notes_url_createnotes = "/no/createNotes?";
	public static final String notes_url_updatenotes = "/no/updateNotes?";
	public static final String notes_url_deletenotes = "/no/deleteNotes?";
	public static final String notes_url_retriveAttachment = "/no/retrieveAttachment?";
	public static final String notes_url_retrieveNotes = "/no/retrieveNotes?";
	public static final String notes_url_changeset = "/no/changeset?";

	private static final String mainNotebook = "main";

	private static NoteManager self = new NoteManager();
	
	public NoteManager() {
		super();
	}

	public void startup(UserSession user) throws Exception {
		
		URL startupURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_startup)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.buildURL();
		
		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(startupURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();
		
		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		changeset(user);
	}
	
	public void retrieveNotes(UserSession user, List<Note> retrieveNotes) throws Exception {

		URL retriveNotesURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_retrieveNotes)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getNoteConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(retriveNotesURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload(self.gson.toJson(generateBody(user.getUserConfig().getNoteConfig().getSyncToken(), retrieveNotes, null)))
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		changeset(user);
	}

	public void createNotes(UserSession user, List<Note> newNotes) throws Exception {

		URL createNotesURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_createnotes)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getNoteConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)		
				.setServerUrl(createNotesURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload(self.gson.toJson(generateBody(null, newNotes, null)))
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		changeset(user);
	}

	public void updateNotes(UserSession user, List<Note> updateNotes) throws Exception {

		URL updateNotesURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_updatenotes)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getNoteConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(updateNotesURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload(self.gson.toJson(generateBody(null, updateNotes, null)))
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		changeset(user);
	}

	public BufferedImage retriveAttachment(UserSession user, String attachmentId) throws Exception {
		// TODO: make this method return the interface for buffered image

		URL retriveAttachmentURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_retriveAttachment)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_validateToken, user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE"))
				.addQueryString(URLConfig.query_arg_attachmentId, attachmentId)
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(retriveAttachmentURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		BufferedImage ir = ImageIO.read(conn.getResponseData());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		// parseResponse(user, responseData);
		changeset(user);
		return ir;
	}

	public void changeset(UserSession user) throws Exception {

		/* This is kept for history
		 * 
		 * URL httpUrl = new URL(user.getServerUrl("notes") + "/no/changeset?" + "clientBuildNumber=" + user.getClientBuildNumber() +
		 * "&clientId=" + user.getUuid() + "&dsid=" + user.getUserConfig().getUserProperties().getProperty("dsid") + "&syncToken=" +
		 * user.getUserConfig().getNoteConfig().getSyncToken());
		 */

		URL changesetURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_changeset)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getNoteConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.GET)
				.setServerUrl(changesetURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
	}

	public void deleteNotes(UserSession user, List<Note> deleteNotes) throws Exception {

		URL deleteNotesURL = new URLBuilder(self.rootURL)
				.setPath(NoteManager.notes_url_deletenotes)
				.addQueryString(URLConfig.query_arg_clientBN, user.getClientBuildNumber())
				.addQueryString(URLConfig.query_arg_clientId, user.getSessionID())
				.addQueryString(URLConfig.query_arg_dsid, user.getUserData().getAccountData().getDsInfo().getDsid())
				.addQueryString(URLConfig.query_arg_syncToken, user.getUserConfig().getNoteConfig().getSyncToken())
				.buildURL();

		ServerConnection conn = new ServerConnection()
				.setRequestMethod(URLConfig.POST)
				.setServerUrl(deleteNotesURL)
				.addRequestHeader(URLConfig.default_header_origin)
				.addRequestHeader(URLConfig.default_header_userAgent)
				.setPayload(self.gson.toJson(generateBody(null, deleteNotes, null)))
				.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		parseResponse(user, conn.getResponseAsString());
		user.getUserTokens().updateTokens(conn.getResponseCookies());
		changeset(user);
	}

	private void parseResponse(UserSession user, String responseData) {
		Gson gson = new Gson();
		NoteJson nJson = gson.fromJson(responseData, NoteJson.class);

		if (nJson.getSyncToken() != null && nJson.getSyncToken().length() > 0) {
			user.getUserConfig().getNoteConfig().setSyncToken(nJson.getSyncToken());
		}

		if (nJson.getDeleted() != null && !(nJson.getDeleted().isEmpty())) {
			parseDeleted(user, nJson);
		}
		if (nJson.getNotes() != null && !(nJson.getNotes().isEmpty())) {
			parseNotes(user, nJson);
		}
	}

	private void parseNotes(UserSession user, NoteJson nJson) {

		Iterator<Note> it = nJson.getNotes().iterator();
		while (it.hasNext()) {
			Note note = it.next();
			String uuid = noteIDtoUUID(note.getNoteID());
			note.updateUUID();
			user.getUserData().getNoteData().getUserNotes().get(mainNotebook).addNote(uuid, note);
		}
	}

	private void parseDeleted(UserSession user, NoteJson nJson) {

		Iterator<Note> it = nJson.getDeleted().iterator();
		while (it.hasNext()) {
			Note itNote = it.next();
			String itHashStr = Integer.toString(itNote.getNoteID().hashCode());
			Iterator<String> noteSet = user.getUserData().getNoteData().getUserNotes().get(mainNotebook).getNoteKeys().iterator();
			while (noteSet.hasNext()) {
				String compareA = noteSet.next();
				if (compareA.contentEquals(itHashStr)) {
					noteSet.remove();
				}
			}
		}
	}

	private NoteJson generateBody(String syncToken, List<Note> notes, List<Note> deletes) {
		NoteJson nJson = new NoteJson();
		if (syncToken != null && !syncToken.isEmpty()) {
			nJson.setSyncToken(syncToken);
		}
		nJson.setNotes(notes);
		nJson.setDeleted((stripNotes(deletes)));
		return nJson;
	}
	
	private List<Note> stripNotes(List<Note> cleanMeNotes){
		ArrayList<Note> sortedNotes = new ArrayList<Note>();

		if (cleanMeNotes != null) {
			if (!cleanMeNotes.isEmpty()) {
				for (Note note : cleanMeNotes) {
					Note newNote = new Note();
					newNote.setNoteID(note.getNoteID());
					sortedNotes.add(newNote);
				}
			}
			return sortedNotes;
		}
		return null;
	}
	
	public Note getNote(UserSession user, String noteID, String noteBookID) {
		if (user.getUserData().getNoteData().getUserNotes().containsKey(noteBookID)) {
			NoteBook noteVar = user.getUserData().getNoteData().getUserNotes().get(noteBookID);
			Map<String, Note> noteList = noteVar.getAllNotes();
			if (noteList.containsKey(noteID)) {
				return noteList.get(noteID);
			}
		}
		return null;
	}

	public Set<String> getNotesList(UserSession user, String noteBookID) {
		if (user.getUserData().getNoteData().getUserNotes().containsKey(noteBookID)) {
			NoteBook noteVar = user.getUserData().getNoteData().getUserNotes().get(noteBookID);
			return noteVar.getAllNotes().keySet();
		}
		return null;
	}

	public Set<String> getNotebookList(UserSession user) {
		return user.getUserData().getNoteData().getUserNotes().keySet();
	}

	public boolean hasNoteBook(UserSession user, String noteBookId) {
		if (user.getUserData().getNoteData().getUserNotes().containsKey(noteBookId)) {
			return true;
		}
		return false;
	}

	public boolean hasNote(UserSession user, String noteId, String noteBookId) {
		if (hasNoteBook(user, noteBookId)) {
			if (user.getUserData().getNoteData().getUserNotes().get(noteBookId).hasNote(noteId)) {
				return true;
			}
		}
		return false;
	}

	private String noteIDtoUUID(String convertID) {
		return Integer.toString(convertID.hashCode());
	}

	public void setRootURL(String string) {
		try {
			self.rootURL = new URL(string);
		} catch (MalformedURLException e) {
			//TODO: make this use the fallback data
			e.printStackTrace();
		}
	}
}
