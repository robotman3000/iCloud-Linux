package icloud.services.notes.objects;

import icloud.services.notes.objects.Attachment;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Note {
	private static final transient String UNDEF = "Not Set";
	private String dateModified;
	private String subject;
	private String folderName;
	private String noteId;
	private String size;
	private Details detail = new Details();
	private ArrayList<Attachment> attachments;
	private transient String uuid = UNDEF;

	public String getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getNoteID() {
		return this.noteId;
	}

	public void setNoteID(String noteID) {
		this.noteId = noteID;
	}

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUuid() {
		return Integer.toString(this.noteId.hashCode());
	}

	public void updateUUID() {
		this.uuid = Integer.toString(noteId.hashCode());
	}

	public ArrayList<Attachment> getAttachments() {
		return this.attachments;
	}
	
	public String getContent() {
		if (this.detail.content != null) {
			return Jsoup.parse(this.detail.content).toString();
		}
		return null;
	}

	public void setContent(String content) {
		this.detail.content = Jsoup.parse((String) content).toString();
	}

	public String toString() {
		return "\nNote: \ndateModified=" + this.dateModified + "\n"
				+ "subject=" + this.subject + "\n" + "folderName="
				+ this.folderName + "\n" + "noteId=" + this.noteId + "\n"
				+ "size=" + this.size + "\n" + "detail="
				+ this.detail.toString() + "\n" + "attachments="
				+ this.attachments + "\n" + "uuid=" + this.uuid;
	}

	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Note otherNote = (Note) obj;
		if (otherNote.detail.content.equalsIgnoreCase(this.getContent())) {
			return true;
		}
		return false;
	}

	private class Details {
		private String content;

		public Details() {
			this.content = "";
		}

		public String toString() {
			Document.OutputSettings outSet = new Document.OutputSettings()
					.prettyPrint(true);
			Document doc = Jsoup.parse((String) this.content).outputSettings(
					outSet);
			return "Details [\n\tcontent=" + doc.toString() + "]";
		}
	}
}
