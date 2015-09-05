package apps.note;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import icloud.SessionData;

public class NoteSessionData extends SessionData {

	private Map<String, Note> notes = new HashMap<String, Note>();

	public Set<String> getNoteKeys() {
		return notes.keySet();
	}

	public boolean hasNote(String noteUUID) {
		return notes.containsKey(noteUUID);
	}

	public void addNote(String noteUUID, Note note) {
		notes.put(noteUUID, note);
	}

	public void deleteNote(String noteUUID) {
		System.out.println("Notes Key List: " + notes.keySet());
		System.out.println("Delete Note: " + noteUUID);
		System.out.println(notes.get(noteUUID));
		if (notes.containsKey(noteUUID)) {
			System.out.println("Key: " + noteUUID + " matched");
			Iterator<String> it = notes.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				if (key.equalsIgnoreCase(noteUUID))
					it.remove();
			}
			// notes.remove(noteUUID);
		}
	}

	public Note getNote(String noteUUID) {
		if (notes.containsKey(noteUUID)) {
			return notes.get(noteUUID);
		}
		// TODO: change null to something better
		return null;
	}

	public Map<String, Note> getAllNotes() {
		return notes;
	}

	public String toString() {
		return notes.toString();
	}
}
