package icloud.notes;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Note {

	private String dateModified;
	private String noteID;
	private String folder;
	private String size;
	private String subject;
	private String content;

	public Note(String dateModified, String noteID, String folder, String size, String subject, String content) {
		this.dateModified = dateModified;
		this.noteID = noteID;
		this.folder = folder;
		this.size = size;
		this.subject = subject;
		this.content = content;
	}

	public Note(Map<String, String> map1) {

		String UNDEF = "Not Set";
		this.dateModified = UNDEF;
		this.noteID = UNDEF;
		this.folder = UNDEF;
		this.size = UNDEF;
		this.subject = UNDEF;
		this.content = UNDEF;

		Set<String> keySet = map1.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();

			Switch: switch (key) {
			case "dateModified":
				if (map1.containsKey(key)) {
					this.dateModified = map1.get(key);
					break Switch;
				}

			case "noteId":
				if (map1.containsKey(key)) {
					this.noteID = map1.get(key);
					break Switch;
				}

			case "folderName":
				if (map1.containsKey(key)) {
					this.folder = map1.get(key);
					break Switch;
				}

			case "size":
				if (map1.containsKey(key)) {
					// TODO: Make size an int
					this.size = map1.get(key);
					break Switch;
				}

			case "subject":
				if (map1.containsKey(key)) {
					this.subject = map1.get(key);
					break Switch;
				}

			case "content":
				if (map1.containsKey(key)) {
					this.content = map1.get(key);
					break Switch;
				}

			default:

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

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
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

	public String toString() {
		Document test = Jsoup.parse(getContent());
		return "\n" + "Date Modified: " + getDateModified() + "\n"
				+ "Note ID: " + getNoteID() + "\n" + "Note Folder: "
				+ getFolder() + "\n" + "Note Size: " + getSize() + "\n"
				+ "Note Subject: " + getSubject() + "\n" + "Raw Note Content: "
				+ getContent() + "\n" + "Clean Note Content: {" + "\n" + test.toString() + "}\n";
	}
}
