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
import icloud.services.ManagerInterface;
import icloud.services.notes.objects.Note;
import icloud.services.notes.objects.NoteBook;
import icloud.user.UserSession;

public class NoteManager extends BaseManager implements ManagerInterface {

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
				user.getSessionConfigOptValue("notes.url.startup"));
		urlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		urlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"),
				user.getUuid());
		urlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
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

	public void createNotes(UserSession user, Note newNote) throws Exception {

		/*
		 * JsonObject innerObject = new JsonObject();
		 * innerObject.addProperty("dateModified", newNote.getDateModified());
		 * innerObject.addProperty("folderName", newNote.getFolderName());
		 * innerObject.addProperty("noteId", newNote.getNoteID());
		 * innerObject.addProperty("subject", newNote.getSubject());
		 * 
		 * JsonObject contentObject = new JsonObject();
		 * contentObject.addProperty("content", newNote.getContent());
		 * 
		 * JsonObject detailsObject = new JsonObject();
		 * detailsObject.add("detail", contentObject);
		 * 
		 * innerObject.add("detail", detailsObject);
		 * 
		 * JsonObject jsonObject = new JsonObject(); jsonObject.add("notes",
		 * innerObject);
		 */
		ArrayList<Note> noteList = new ArrayList<Note>();
		noteList.add(newNote);

		Gson gson = new Gson();
		NoteJson nJson = new NoteJson();
		nJson.setNotes(noteList);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent", "Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(debugEnabled);

		/*
		 * URL httpUrl = new URL("https://" +
		 * user.getUserConfig().getServersList().get("notes").get("url") +
		 * "/no/createNotes?" + "clientBuildNumber=" +
		 * user.getClientBuildNumber() + "&clientId=" + user.getUuid() +
		 * "&dsid=" +
		 * user.getUserConfig().getUserProperties().getProperty("dsid"));
		 */

		URLBuilder httpurlBuilder = new URLBuilder().setUrl(user.getServerUrl("notes")).setPath(
				user.getSessionConfigOptValue("notes.url.createnotes"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"),
				user.getUuid());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.syncToken"), user
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
				user.getSessionConfigOptValue("notes.url.updatenotes"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"),
				user.getUuid());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.syncToken"), user
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
				user.getSessionConfigOptValue("notes.url.retriveAttachment"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"),
				user.getUuid());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.validateToken"),
				user.getUserTokens().getTokenValue("X-APPLE-WEBAUTH-VALIDATE"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.attachmentId"),
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
				user.getSessionConfigOptValue("notes.url.changeset"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"),
				user.getUuid());
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
		httpurlBuilder.addQueryString(user.getSessionConfigOptValue("query.arg.syncToken"), user
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
		urlBuild.setPath(user.getSessionConfigOptValue("notes.url.deletenotes"));
		urlBuild.addQueryString(user.getSessionConfigOptValue("query.arg.clientBN"),
				user.getClientBuildNumber());
		urlBuild.addQueryString(user.getSessionConfigOptValue("query.arg.clientId"), user.getUuid());
		urlBuild.addQueryString(user.getSessionConfigOptValue("query.arg.dsid"), user
				.getUserConfig().getUserProperties().getProperty("dsid"));
		urlBuild.addQueryString(user.getSessionConfigOptValue("query.arg.syncToken"), user
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
		// TODO: add better error returning
		return null;
	}

	public Set<String> getNotesList(UserSession user, String noteBookID) {
		if (user.getUserData().getNoteData().getUserNotes().containsKey(noteBookID)) {
			NoteBook noteVar = user.getUserData().getNoteData().getUserNotes().get(noteBookID);
			return noteVar.getAllNotes().keySet();
		}
		// TODO: add better error returning
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
