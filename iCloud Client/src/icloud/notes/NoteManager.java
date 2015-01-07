package icloud.notes;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import icloud.BaseManager;
import icloud.CommonLogic;
import icloud.ServerConnection;
import icloud.UserSessionInstance;
import icloud.UserTokenManager;

public class NoteManager extends BaseManager {

	private Map<String, NoteBook> userNotes = new HashMap<String, NoteBook>();
	private String syncToken;
	private boolean debugenabled = true;
	private UserSessionInstance user;
	private UserTokenManager userTokens;
	private static final String mainNotebook = "main";

	private enum idTypes {
		Note, NoteBook;
	}

	public NoteManager(UserSessionInstance user, UserTokenManager userTokens,
			boolean debugenabled) throws Exception {

		NoteBook noteBook = new NoteBook();
		userNotes.put(mainNotebook, noteBook);

		this.debugenabled = debugenabled;

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent",
				"Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(true);

		URL httpUrl = new URL(user.getServerUrl("notes") + "/no/startup?"
				+ "clientBuildNumber=" + user.clientBnum + "&clientId="
				+ user.UUID + "&dsid=" + user.getUserID());
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getTokenManager().getUserTokens());
		conn.connect();

		// Remove this
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		userTokens.updateUserTokens(conn.getResponseCookies(), user.UUID);
		String responseData = conn.getResponseData();
		parseJson(responseData);

		this.userTokens = userTokens;
		this.user = user;

		callChangeset();
	}

	private void callChangeset() throws Exception {
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent",
				"Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(true);

		URL httpUrl = new URL(user.getServerUrl("notes") + "/no/changeset?"
				+ "clientBuildNumber=" + user.clientBnum + "&clientId="
				+ user.UUID + "&dsid=" + user.getUserID() + "&syncToken="
				+ syncToken);
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("GET");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(userTokens.getUserTokens());
		conn.connect();

		// Remove this
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		// JsonObject userData =
		// CommonLogic.parseJsonData(conn.getResponseData());
		String responseData = conn.getResponseData();
		parseJson(responseData);

		userTokens.updateUserTokens(conn.getResponseCookies(), user.UUID);

		// TODO: Parse returned data
	}

	private void parseJson(String responseData) {

		JsonObject userData = CommonLogic.parseJsonData(responseData);

		String[] primitiveStrings = { "syncToken" };
		for (String string : primitiveStrings) {
			if (userData.has(string)) {
				switch (string) {

				case "syncToken":
					syncToken = userData.get("syncToken").getAsString();
					break;

				default:
					break;

				}
			}
		}

		String[] collectionStrings = { "notes", "deleted" };
		for (String string : collectionStrings) {
			if (userData.has(string)) {
				JsonElement element = userData.get(string);
				JsonArray dataArray = element.getAsJsonArray();

				if (debugenabled) {
					System.out.println(dataArray.toString());
				}

				switch (string) {

				case "notes":
					parseNotes(dataArray);
					break;

				case "deleted":
					parseDeleted(dataArray);
					break;

				default:
					break;
				}
			}
		}
	}

	private void parseNotes(JsonArray dataArray) {
		String[] noteStrings = { "dateModified", "size", "noteId",
				"folderName", "subject", "detail" };

		for (JsonElement noteElem : dataArray) {
			JsonObject noteObj = noteElem.getAsJsonObject();
			// System.out.println(noteObj.toString());
			Map<String, String> map1 = new HashMap<String, String>();
			for (String currentString : noteStrings) {
				if (currentString.compareTo("detail") != 0) {
					if (noteObj.has(currentString)) {
						JsonElement json = noteObj.get(currentString);
						map1.put(currentString, json.getAsString());
					}
				} else {
					if (noteObj.has(currentString)) {
						JsonElement var = noteObj.get("detail");
						JsonObject var2 = var.getAsJsonObject();
						if (var2.has("content")) {
							JsonElement var3 = var2.get("content");
							map1.put("content", var3.getAsString());
						}
					}
				}
			}
			String tempUUID = CommonLogic.generateUUID();
			map1.put("uuid", tempUUID);
			Note newNote = new Note(map1);

			userNotes.get(mainNotebook).addNote(tempUUID,
					newNote);
			// System.out.println("Note Object: " +
			// noteObj.toString());
			// System.out.flush();
		}
	}

	private void parseDeleted(JsonArray dataArray) {
		String[] deletedStrings = { "noteId" };

		for (JsonElement noteElem : dataArray) {
			JsonObject noteObj = noteElem.getAsJsonObject();

			// System.out.println(noteObj.toString());

			for (String currentString : deletedStrings) {
				if (currentString.compareTo("noteId") != 0) {
					// Currently do nothing
					// this is here for future proofing
				} else {
					if (noteObj.has(currentString)) {
						// delete note with matching id

						String var = noteObj.get("noteId").toString();
						Iterator<String> it = userNotes.get(mainNotebook)
								.getNoteKeys().iterator();

						while (it.hasNext()) {
							String uKey = it.next();
							Note note = userNotes.get(mainNotebook).getNote(uKey);
							System.out.println("parseDeleted() pre print");
							
							String noteIDVar = "\"" +  note.getNoteID() + "\"";
							
							if (noteIDVar.equalsIgnoreCase(var)) {
								userNotes.get(mainNotebook).deleteNote(uKey);
								System.out.println("parseDeleted() print");
							}
						}

						/*
						 * NoteBook notesabc = userNotes.get(mainNotebook);
						 * String var = noteObj.get("noteId").toString();
						 * System.out.println(var); notesabc.deleteNote(var);
						 * userNotes.put(mainNotebook, notesabc);
						 */
					}
				}
			}
			// System.out.println("Note Object: " +
			// noteObj.toString());
			// System.out.flush();
		}
	}

	public void getChanges() throws Exception {
		callChangeset();
	}

	private boolean validateID(idTypes id, String ID) {
		// TODO: Finish method or remove
		switch (id) {

		case Note:

		case NoteBook:

		}

		return false;
	}

	public void createNote(Note newNote, String noteBookID) throws Exception {
		JsonObject innerObject = new JsonObject();
		innerObject.addProperty("dateModified", newNote.getDateModified());
		innerObject.addProperty("folderName", newNote.getFolderName());
		innerObject.addProperty("noteId", newNote.getNoteID());
		innerObject.addProperty("subject", newNote.getSubject());

		JsonObject contentObject = new JsonObject();
		contentObject.addProperty("content", newNote.getContent());

		JsonObject detailsObject = new JsonObject();
		detailsObject.add("detail", contentObject);

		innerObject.add("detail", detailsObject);

		JsonObject jsonObject = new JsonObject();
		jsonObject.add("notes", innerObject);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent",
				"Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(true);

		URL httpUrl = new URL(user.getServerUrl("notes") + "/no/createNotes?"
				+ "clientBuildNumber=" + user.clientBnum + "&clientId="
				+ user.UUID + "&dsid=" + user.getUserID());
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getTokenManager().getUserTokens());
		conn.setPayload(jsonObject.toString());
		conn.connect();

		// Remove this
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		userTokens.updateUserTokens(conn.getResponseCookies(), user.UUID);

		String responseData = conn.getResponseData();
		parseJson(responseData);
		callChangeset();
	}

	public void deleteNote(String noteId) throws Exception {

		JsonObject noteIdObject = new JsonObject();
		
		Iterator<String> it = userNotes.get(mainNotebook).getNoteKeys().iterator();

		while (it.hasNext()) {
			String uKey = it.next();
			Note note = userNotes.get(mainNotebook).getNote(uKey);
			if (note.getUuid().equalsIgnoreCase(noteId)) {
				noteIdObject.addProperty("noteId", note.getNoteID());
				System.out.println("deleteNote() print");
			}
		}

		JsonArray innerArray = new JsonArray();
		innerArray.add(noteIdObject);

		JsonObject jsonObject = new JsonObject();
		jsonObject.add("notes", innerArray);

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Origin", "https://www.icloud.com");
		headersMap.put("User-Agent",
				"Mozilla/5.0 Java_iCloud/1.0 NoteManager/1.0");

		ServerConnection conn = new ServerConnection(true);

		URL httpUrl = new URL(user.getServerUrl("notes") + "/no/deleteNotes?"
				+ "clientBuildNumber=" + user.clientBnum + "&clientId="
				+ user.UUID + "&dsid=" + user.getUserID());
		conn.setServerUrl(httpUrl);
		conn.setRequestMethod("POST");
		conn.setRequestHeaders(headersMap);
		conn.setRequestCookies(user.getTokenManager().getUserTokens());
		conn.setPayload(jsonObject.toString());
		conn.connect();

		// Remove this
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(conn.getResponseData());
		String result2 = gson.toJson(je);

		if (debugenabled) {
			System.out.println("*** BEGIN ***");
			System.out.println(result2);
			System.out.println("*** END ***");
		}

		userTokens.updateUserTokens(conn.getResponseCookies(), user.UUID);

		String responseData = conn.getResponseData();
		parseJson(responseData);

		callChangeset();
	}

	public Note getNote(String noteID, String noteBookID) {
		if (userNotes.containsKey(noteBookID)) {
			NoteBook noteVar = userNotes.get(noteBookID);
			Map<String, Note> noteList = noteVar.getAllNotes();
			if (noteList.containsKey(noteID)) {
				return noteList.get(noteID);
			}
		}
		// TODO: add better error returning
		return null;
	}

	public Set<String> getNotesList(String noteBookID) {
		if (userNotes.containsKey(noteBookID)) {
			NoteBook noteVar = userNotes.get(noteBookID);
			return noteVar.getAllNotes().keySet();
		}
		// TODO: add better error returning
		return null;
	}

	public Set<String> getNotebookList() {
		return userNotes.keySet();
	}

	public String toString() {
		return "Sync Token: " + syncToken;
	}
}
