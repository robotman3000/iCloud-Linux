package icloud.services.notes.objects;

import icloud.services.notes.objects.Attachment;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Note {
    private static final transient String UNDEF = "Not Set";
    private String dateModified;
    private String subject;
    private String folderName;
    private String noteId;
    private String size;
    private Details detail;
    private List<Attachment> attachments;
    private transient String uuid;

    public Note() {
        this.detail = new Details();
    }

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Details getDetail() {
        return this.detail;
    }

    public void setDetail(Details detail) {
        this.detail = detail;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public String getContent() {
        return Jsoup.parse((String)this.detail.content).toString();
    }

    public void setContent(String content) {
        this.detail.content = Jsoup.parse((String)content).toString();
    }

    public String toString() {
        return "\nNote: \ndateModified=" + this.dateModified + "\n" + "subject=" + this.subject + "\n" + "folderName=" + this.folderName + "\n" + "noteId=" + this.noteId + "\n" + "size=" + this.size + "\n" + "detail=" + this.detail.toString() + "\n" + "attachments=" + this.attachments + "\n" + "uuid=" + this.uuid;
    }

    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Note otherNote = (Note)obj;
        if (otherNote.detail.content.equalsIgnoreCase(this.getContent())) {
            return true;
        }
        return false;
    }

    public class Details {
        private String content;

        public Details() {
            this.content = "";
        }

        public String toString() {
            Document.OutputSettings outSet = new Document.OutputSettings().prettyPrint(true);
            Document doc = Jsoup.parse((String)this.content).outputSettings(outSet);
            return "Details [\n\tcontent=" + doc.toString() + "]";
        }
    }
}

