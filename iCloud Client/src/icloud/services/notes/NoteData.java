package icloud.services.notes;

import icloud.services.BaseData;
import icloud.services.notes.objects.NoteBook;

import java.util.HashMap;
import java.util.Map;

public class NoteData extends BaseData {

	private Map<String, NoteBook> userNotes = new HashMap<String, NoteBook>();

	public NoteData(){
		userNotes.put("main", new NoteBook());
	}
	
	public Map<String, NoteBook> getUserNotes() {
		return userNotes;
	}
	
	public String toString() {
		return "NoteData [userNotes=" + userNotes + "]";
	}
}
