package icloud.notes;

import java.util.HashMap;
import java.util.Map;

// fix me up
public class NoteBook {

	Map<String, Note> notes = new HashMap<String, Note>();

	public void addNote(String noteUUID, Note note) {
		this.notes.put(noteUUID, note);
	}

	public Map<String, Note> getAllNotes() {
		return notes;
	}

	public void deleteNote(String noteUUID) {
		//if (notes.containsKey(noteUUID)) {
			notes.remove(noteUUID);
		//}
	}

	public String toString() {
		return notes.toString();

	}
}
