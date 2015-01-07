package icloud.notes;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Note {

	private String dateModified;
	private String noteID;
	private String folderName;
	private String size;
	private String subject;
	private String content;
	private String uuid;

	public Note(String dateModified, String noteID, String folderName, String size, String subject, String content) {
		this.dateModified = dateModified;
		this.noteID = noteID;
		this.folderName = folderName;
		this.size = size;
		this.subject = subject;
		this.content = content;
	}

	public Note(Map<String, String> noteInfoMap) {

		String UNDEF = "Not Set";

		this.dateModified = UNDEF;
		this.noteID = UNDEF;
		this.folderName = UNDEF;
		this.size = UNDEF;
		this.subject = UNDEF;
		this.content = UNDEF;
		this.uuid = UNDEF;

		Set<String> keySet = noteInfoMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();

			if (noteInfoMap.containsKey(key)) {
				String value = noteInfoMap.get(key);
				value = (value == null ? UNDEF : value.trim() );

				switch (key) {
				case "dateModified":
					this.dateModified = value;
					break;

				case "noteId":
					this.noteID = value;
					break;

				case "folderName":
					this.folderName = value;
					break;

				case "size":
					// TODO: Make size an int
					// Integer.parseInt(value);
					// (but don't forget to handle NumberFormatException ...)
					this.size = value;
					break;

				case "subject":
					this.subject = value;
					break;

				case "content":
					this.content = value;
					break;
					
				case "uuid":
					this.uuid = value;
					break;

				default:
					break;
				}
			}
		}
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getNoteID() {
		return noteID;
	}

	public void setNoteID(String noteID) {
		this.noteID = noteID;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String toString() {
		Document test = Jsoup.parse(getContent());
		return "\n" + "Date Modified: " + getDateModified() + "\n"
				+ "Note ID: " + getNoteID() + "\n" 
				+ "Note Access Token: " + getUuid() + "\n" 
				+ "Note Folder: " + getFolderName() + "\n" 
				+ "Note Size: " + getSize() + "\n"
				+ "Note Subject: " + getSubject() + "\n" 
				+ "Raw Note Content: " + getContent() + "\n" 
				+ "Clean Note Content: {" + "\n" + test.toString() + "}\n";
	}
}
