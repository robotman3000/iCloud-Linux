package icloud.notes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// fix me up
public class NoteBook {

	private Map<String, Note> notes = new HashMap<String, Note>();

	public void addNote(String noteUUID, Note note) {
		notes.put(noteUUID, note);
	}

	public void updateNote(String noteUUID, Note note) {
		//TODO
		//this.notes.put(noteUUID, note);
	}

	public Map<String, Note> getAllNotes() {
		return notes;
	}

	public void deleteNote(String noteUUID) {
		System.out.println("Notes Key List: " + notes.keySet());
		System.out.println("Delete Note: " + noteUUID);
		System.out.println(notes.get(noteUUID));
		if (notes.containsKey(noteUUID)) {
			System.out.println("Key: " + noteUUID + " matched");
			Iterator<String> it = notes.keySet().iterator();
			
			while (it.hasNext())
			{
			  String key = it.next();
			  if (key.equalsIgnoreCase(noteUUID))
			    it.remove();
			 }
		
		//	notes.remove(noteUUID);
		}
	}

	public String toString() {
		return notes.toString();

	}
}
