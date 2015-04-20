package icloud.services.notes;

import icloud.services.BaseManager;
import icloud.services.URLConfig;
import icloud.services.notes.objects.Note;
import icloud.services.notes.objects.NoteBook;
import icloud.user.UserSession;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import common.ServerConnection;
import common.SystemLogger;
import common.URLBuilder;

public class NoteManager extends BaseManager{

	//TODO: test if you can create/update/delete multipule notes at once like retrieveNotes
	
	private static final String mainNotebook = "main";

	public NoteManager() {
		this.isInitialized = true;

	}

	public NoteManager(SystemLogger logger) {
		this();
		setLogger(logger);
	}

	public void startup(UserSession user) throws Exception {
		URLBuilder urlBuilder = new URLBuilder().setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_startup).setPort(443);
		urlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		urlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		urlBuilder.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 LoginManager/1.0");
		URL url = urlBuilder.buildURL();
		ServerConnection conn = new ServerConnection().setLogger(getLogger())
				.setRequestMethod("GET")
				.setServerUrl(url).setPayload("{}")
				.setRequestCookies(user.getUserTokens().getTokens()).setRequestHeaders(headersMap);
		conn.connect();

		String responseData = conn.getResponseDataAsString();
		parseResponse(user, responseData);
		user.getUserTokens().updateTokens(conn.getResponseCookies());

		/*if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/
		changeset(user);
	}

	public void retrieveNotes(UserSession user, ArrayList<Note> retrieveNotes) throws Exception{
		
		ArrayList<Note> sortedNotes = new ArrayList<Note>();
		
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

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		URLBuilder httpurlBuilder = new URLBuilder().setPort(443).setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_retrieveNotes);
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_syncToken, user
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

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

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

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		URLBuilder httpurlBuilder = new URLBuilder().setPort(443).setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_createnotes);
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_syncToken, user
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

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

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

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		URLBuilder httpurlBuilder = new URLBuilder().setPort(443).setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_updatenotes);
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_dsid, user
		.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_syncToken, user
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

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

		changeset(user);
	}

	public BufferedImage retriveAttachment(UserSession user, String attachmentId) throws Exception {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		URLBuilder httpurlBuilder = new URLBuilder().setPort(443).setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_retriveAttachment);
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_validateToken,
				user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE"));
		httpurlBuilder.addQueryString(URLConfig.query_arg_attachmentId,
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

/*		if (debugEnabled) {
			// CommonLogic.printJson(responseData);
		}*/

		changeset(user);

		return ir;
	}

	public void changeset(UserSession user) throws Exception {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		/*
		 * URL httpUrl = new URL(user.getServerUrl("notes") + "/no/changeset?" +
		 * "clientBuildNumber=" + user.getClientBuildNumber() + "&clientId=" +
		 * user.getUuid() + "&dsid=" +
		 * user.getUserConfig().getUserProperties().getProperty("dsid") +
		 * "&syncToken=" + user.getUserConfig().getNoteConfig().getSyncToken());
		 */

		URLBuilder httpurlBuilder = new URLBuilder().setPort(443).setHost(user.getServerUrl("notes")).setPath(
				URLConfig.notes_url_changeset);
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(URLConfig.query_arg_clientId,
				user.getUuid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		httpurlBuilder.addQueryString(URLConfig.query_arg_syncToken, user
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

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

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

		ServerConnection conn = new ServerConnection().setLogger(getLogger());

		URLBuilder urlBuild = new URLBuilder();
		urlBuild.setPort(443).setHost(user.getServerUrl("notes"));
		urlBuild.setPath(URLConfig.notes_url_deletenotes);
		urlBuild.addQueryString(URLConfig.query_arg_clientBN,
				user.getClientBuildNumber());
		urlBuild.addQueryString(URLConfig.query_arg_clientId, user.getUuid());
		urlBuild.addQueryString(URLConfig.query_arg_dsid, user
				.getUserData().getAccountData().getDsInfo().getDsid());
		urlBuild.addQueryString(URLConfig.query_arg_syncToken, user
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

/*		if (debugEnabled) {
			CommonLogic.printJson(responseData);
		}*/

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

	public boolean hasNoteBook(UserSession user, String noteBookId){
		if(user.getUserData().getNoteData().getUserNotes().containsKey(noteBookId)){
			return true;
		}
		return false;
	}
	
	public boolean hasNote(UserSession user, String noteId, String noteBookId){
		if(hasNoteBook(user, noteBookId)){
			if(user.getUserData().getNoteData().getUserNotes().get(noteBookId).hasNote(noteId)){
				return true;
			}
		}
		return false;
	}
	
	private String noteIDtoUUID(String convertID) {
		return Integer.toString(convertID.hashCode());
	}
}
