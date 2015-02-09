package icloud.services.notes;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;

import common.CommonLogic;

public class Note {
	@SuppressWarnings("unused")
	private static final transient String UNDEF = "Not Set";

	private String dateModified;
	private String subject;
	private String folderName;
	private String noteId;
	private String size;

	private Details detail;
	private List<Attachments> attachments;

	private transient String uuid;

	/*
	 * public Note(String dateModified, String noteID, String folderName, String
	 * size, String subject, Details noteContent) { this.dateModified =
	 * dateModified; this.noteId = noteID; this.folderName = folderName;
	 * this.size = size; this.subject = subject; this.uuid =
	 * Integer.toString(noteID.hashCode()); this.detail = noteContent; }
	 * 
	 * public Note(String dateModified, String noteID, String folderName, String
	 * size, String subject, String content, Details noteContent,
	 * List<Attachments> attachments) { this.dateModified = dateModified;
	 * this.noteId = noteID; this.folderName = folderName; this.size = size;
	 * this.subject = subject; this.uuid = Integer.toString(noteID.hashCode());
	 * this.detail = noteContent; this.attachments = attachments; }
	 * 
	 * public Note(Map<String, String> noteInfoMap) {
	 * 
	 * this.dateModified = UNDEF; this.noteId = UNDEF; this.folderName = UNDEF;
	 * this.size = UNDEF; this.subject = UNDEF; this.uuid = UNDEF;
	 * 
	 * Set<String> keySet = noteInfoMap.keySet(); Iterator<String> iterator =
	 * keySet.iterator(); while (iterator.hasNext()) { String key =
	 * iterator.next();
	 * 
	 * if (noteInfoMap.containsKey(key)) { String value = noteInfoMap.get(key);
	 * value = (value == null ? UNDEF : value.trim() );
	 * 
	 * switch (key) { case "dateModified": this.dateModified = value; break;
	 * 
	 * case "noteId": this.noteId = value; break;
	 * 
	 * case "folderName": this.folderName = value; break;
	 * 
	 * case "size": // TODO: Make size an int // Integer.parseInt(value); //
	 * (but don't forget to handle NumberFormatException ...) this.size = value;
	 * break;
	 * 
	 * case "subject": this.subject = value; break;
	 * 
	 * case "uuid": this.uuid = value; break;
	 * 
	 * default: break; } } } }
	 */
	public Note() {

	}

	public class Details {
		private String content;

		// public Details(){

		// }

		public Details() {
			//String tempStr = Jsoup.parse(content).toString();
			//this.content = tempStr;
		}

		public String getContent() {
			return Jsoup.parse(content).toString();
		}

		public Details setContent(String content) {
			// this.content = content;
			this.content = Jsoup.parse(content).toString();
			return this;
		}

		public String toString() {
			OutputSettings outSet = new OutputSettings().prettyPrint(true);
			Document doc = Jsoup.parse(content).outputSettings(outSet);
			return "Details [\n\tcontent=" + /* content */doc.toString() + "]";
		}
	}

	public class Attachments {
		private String attachmentId;
		private String contentType;
		private String contentId;
		private int size;

		public Attachments() {
			this.attachmentId = CommonLogic.generateUUID();
			this.contentId = null;
			this.contentType = null;
			this.size = 0;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public String getContentId() {
			return contentId;
		}

		public void setContentId(String contentId) {
			this.contentId = contentId;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getAttachmentId() {
			return attachmentId;
		}

		public void setAttachmentId(String attachmentId) {
			this.attachmentId = attachmentId;
		}

		public String toString() {
			return "Attachments [\n\tattachmentId=" + attachmentId + "\n\t" + "contentType="
					+ contentType + "\n\t" + "contentId=" + contentId + "\n\t" + "size=" + size
					+ "]";
		}
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getNoteID() {
		return noteId;
	}

	public void setNoteID(String noteID) {
		this.noteId = noteID;
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

	public String getUuid() {
		return Integer.toString(noteId.hashCode());
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Details getDetail() {
		return detail;
	}

	public void setDetail(Details detail) {
		this.detail = detail;
	}

	public List<Attachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachments> attachments) {
		this.attachments = attachments;
	}

	public String toString() {
		return "\nNote: " + "\n" + "dateModified=" + dateModified + "\n" + "subject=" + subject
				+ "\n" + "folderName=" + folderName + "\n" + "noteId=" + noteId + "\n" + "size="
				+ size + "\n" + "detail=" + detail.toString() + "\n" + "attachments=" + attachments
				+ "\n" + "uuid=" + uuid;
	}

	@Override
	public boolean equals(Object obj) {
	    if((obj == null) || (getClass() != obj.getClass())){
	        return false;
	    } else {
	        Note otherNote = (Note) obj;
	        if (otherNote.detail.getContent().equalsIgnoreCase(this.getDetail().getContent())){
	        	return true;
	        }
	    }

	    return false;
	}
}
