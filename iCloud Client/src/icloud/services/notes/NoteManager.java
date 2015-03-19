package icloud.services.notes;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import common.CommonLogic;
import common.ServerConnection;
import common.URLBuilder;
import icloud.services.BaseManager;
import icloud.services.notes.objects.Note;
import icloud.services.notes.objects.NoteBook;
import icloud.user.UserSession;

public class NoteManager extends BaseManager{

	private static final String mainNotebook = "main";

	public NoteManager() {
		this.isInitialized = true;

	}

	public NoteManager(boolean announceConnections, boolean debugenabled) {
		this();
		this.announceConnections = announceConnections;
		this.debugEnabled = debugenabled;
	}

	public void startup(UserSession user) throws Exception {
		URLBuilder urlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_startup);
		urlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		urlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		urlBuilder.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		URL url = urlBuilder.buildURL();
		ServerConnection conn = new ServerConnection(debugEnabled).setRequestMethod("GET")
				.setServerUrl(url).setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens()).setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}
		changeset(user);
	}

	public void retrieveNotes(UserSession user, ArrayList<Note> retrieveNotes) throws Exception{
		
		ArrayList<Note> sortedNotes = new ArrayList<>();
		
		for (Note note : retrieveNotes){
			Note newNote = new Note();
			newNote.setNoteID(note.getNoteID());
			sortedNotes.add(newNote);
		}
		
		Gson gson = new Gson();
		NoteJson nJson = new NoteJson();
		nJson.setNotes(sortedNotes);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_retrieveNotes);
		httpurlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(UserSession.query_arg_syncToken, user
				.getUserConfig().getNoteConfig().getSyncToken());

		URL httpUrl = httpurlBuilder.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload(gson.toJson(nJson));
		conn.connect();

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

		changeset(user);
		
	}
	
	public void createNotes(UserSession user, Note newNote) throws Exception {

		ArrayList<Note> noteList = new ArrayList<Note>();
		noteList.add(newNote);

		Gson gson = new Gson();
		NoteJson nJson = new NoteJson();
		nJson.setNotes(noteList);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_createnotes);
		httpurlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(UserSession.query_arg_syncToken, user
				.getUserConfig().getNoteConfig().getSyncToken());

		URL httpUrl = httpurlBuilder.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload(gson.toJson(nJson));
		conn.connect();

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

		changeset(user);
	}

	public void updateNotes(UserSession user, Note updateNote) throws Exception {
		ArrayList<Note> noteList = new ArrayList<Note>();
		noteList.add(updateNote);

		Gson gson = new Gson();
		NoteJson nJson = new NoteJson();
		nJson.setNotes(noteList);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_updatenotes);
		httpurlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(UserSession.query_arg_dsid, user
		.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(UserSession.query_arg_syncToken, user
				.getUserConfig().getNoteConfig().getSyncToken());
		URL httpUrl = httpurlBuilder.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload(gson.toJson(nJson));
		conn.connect();

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

		changeset(user);
	}

	public BufferedImage retriveAttachment(UserSession user, String attachmentId) throws Exception {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_retriveAttachment);
		httpurlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(UserSession.query_arg_validateToken,
				user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE"));
		httpurlBuilder.addQueryString(UserSession.query_arg_attachmentId,
				attachmentId);

		URL httpUrl = httpurlBuilder.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		BufferedImage ir = ImageIO.read(conn.getResponseData());

		user.getUserTokens().updateTokens(conn.getResponseCookies());
		// parseResponse(user, responseData);

		if (debugEnabled) {
			// CommonLogic.printJson(responseData);
		}

		changeset(user);

		return ir;
	}

	public void changeset(UserSession user) throws Exception {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		/*
		 * URL httpUrl = new URL(user.getServerUrl("notes") + "/no/changeset?" +
		 * "clientBuildNumber=" + user.getClientBuildNumber() + "&clientId=" +
		 * user.getUuid() + "&dsid=" +
		 * user.getUserConfig().getUserProperties().getProperty("dsid") +
		 * "&syncToken=" + user.getUserConfig().getNoteConfig().getSyncToken());
		 */

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				UserSession.notes_url_changeset);
		httpurlBuilder.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(UserSession.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(UserSession.query_arg_syncToken, user
				.getUserConfig().getNoteConfig().getSyncToken());

		URL httpUrl = httpurlBuilder.buildURL();

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.connect();

		// JsonObject userData =
		// CommonLogic.parseJsonData(conn.getResponseData());
		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

		user.getUserTokens().updateTokens(conn.getResponseCookies());
	}

	public void deleteNotes(UserSession user, Note noteToDelete) throws Exception {

		ArrayList<Note> noteList = new ArrayList<Note>();
		noteList.add(noteToDelete);

		Gson gson = new Gson();
		NoteJson nJson = new NoteJson();
		nJson.setNotes(noteList);

		/*
		 * Iterator<String> it =
		 * user.getUserData().getNoteData().getUserNotes().get(mainNotebook)
		 * .getNoteKeys().iterator();
		 * 
		 * while (it.hasNext()) { String uKey = it.next(); Note note =
		 * user.getUserData().getNoteData().getUserNotes().get(mainNotebook)
		 * .getNote(uKey); if (note.getUuid().equalsIgnoreCase(noteId)) {
		 * noteIdObject.addProperty("noteId", note.getNoteID());
		 * System.out.println("deleteNote() print"); } }
		 * 
		 * JsonArray innerArray = new JsonArray(); innerArray.add(noteIdObject);
		 * 
		 * JsonObject jsonObject = new JsonObject(); jsonObject.add("notes",
		 * innerArray);
		 */

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		URLBuilder urlBuild = new URLBuilder();
		urlBuild.setUrl(user.getServerUrl("notes"));
		urlBuild.setPath(UserSession.notes_url_deletenotes);
		urlBuild.addQueryString(UserSession.query_arg_clientBN,
				user.getClientBuildNumber());
		urlBuild.addQueryString(UserSession.query_arg_clientId, user.getUuid());
		urlBuild.addQueryString(UserSession.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		urlBuild.addQueryString(UserSession.query_arg_syncToken, user
				.getUserConfig().getNoteConfig().getSyncToken());
		URL httpUrl = urlBuild.buildURL();

		/*
		 * URL httpUrl = new URL("https://" +
		 * user.getUserConfig().getServersList().get("notes").get("url") +
		 * "/no/deleteNotes?" + "clientBuildNumber=" +
		 * user.getClientBuildNumber() + "&clientId=" + user.getUuid() +
		 * "&dsid=" +
		 * user.getUserConfig().getUserProperties().getProperty("dsid"));
		 */

		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getUserTokens().getTokens());
		conn.setPayload(gson.toJson(nJson));
		conn.connect();

		user.getUserTokens().updateTokens(conn.getResponseCookies());

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);

		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}

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
			note.setUuid(uuid);
			user.getUserData().getNoteData().getUserNotes().get(mainNotebook).addNote(uuid, note);
		}
	}

	private void parseDeleted(UserSession user, NoteJson nJson) {

		Iterator<Note> it = nJson.getDeleted().iterator();
		while (it.hasNext()) {
			Note itNote = it.next();
			String itHashStr = Integer.toString(itNote.getNoteID().hashCode());
			Iterator<String> noteSet = user.getUserData().getNoteData().getUserNotes()
					.get(mainNotebook).getNoteKeys().iterator();
			while (noteSet.hasNext()) {
				String compareA = noteSet.next();
				if (compareA.contentEquals(itHashStr)) {
					noteSet.remove();
				}
			}
		}
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

	private String noteIDtoUUID(String convertID) {
		return Integer.toString(convertID.hashCode());
	}

	public String toString() {
		return "NoteManager [isInitialized=" + isInitialized + ", announceConnections="
				+ announceConnections + ", debugEnabled=" + debugEnabled + "]";
	}

}
