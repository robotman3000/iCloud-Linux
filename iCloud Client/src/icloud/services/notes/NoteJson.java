package icloud.services.notes;

import icloud.services.notes.objects.Note;

import java.util.ArrayList;

public class NoteJson {

	private String syncToken;
	private ArrayList<Note> notes;
	private ArrayList<Note> deleted;
	
	public String getSyncToken() {
		return syncToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public ArrayList<Note> getDeleted() {
		return deleted;
	}

	public void setDeleted(ArrayList<Note> deleted) {
		this.deleted = deleted;
	}
}
