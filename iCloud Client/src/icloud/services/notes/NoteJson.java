package icloud.services.notes;

import icloud.services.notes.objects.Note;

import java.util.List;

public class NoteJson {

	private String syncToken = null;
	
	private List<Note> notes = null;

	private List<Note> deleted = null;
	
	public String getSyncToken() {
		return syncToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Note> getDeleted() {
		return deleted;
	}

	public void setDeleted(List<Note> deleted) {
		this.deleted = deleted;
	}
}
