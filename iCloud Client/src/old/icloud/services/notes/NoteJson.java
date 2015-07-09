package old.icloud.services.notes;

import java.util.ArrayList;
import java.util.List;

import old.icloud.services.BaseJson;
import old.icloud.services.notes.objects.Note;

/**
 * @Invisible
 */
public class NoteJson extends BaseJson{

	private String syncToken;
	private List<Note> notes;
	private List<Note> deleted;
	
	public String getSyncToken() {
		return syncToken;
	}

	/**
	 * @Invisible
	 */
	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * @Invisible
	 */
	public void setNotes(List<Note> notes2) {
		this.notes = notes2;
	}

	public List<Note> getDeleted() {
		return deleted;
	}

	/**
	 * @Invisible
	 */
	public void setDeleted(List<Note> deleted) {
		this.deleted = deleted;
	}
}
