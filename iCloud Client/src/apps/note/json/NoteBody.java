package apps.note.json;

import java.util.List;

import apps.note.Note;
import icloud.json.JsonBody;

public class NoteBody extends JsonBody {
	private String syncToken;
	private List<Note> notes;
	private List<Note> deleted;

	public String getSyncToken() {
		return syncToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes2) {
		this.notes = notes2;
	}

	public List<Note> getDeleted() {
		return deleted;
	}

	public void setDeleted(List<Note> deleted) {
		this.deleted = deleted;
	}
}
