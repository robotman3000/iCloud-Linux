package old.icloud.services.notes;

import java.util.HashMap;
import java.util.Map;

import old.icloud.services.BaseData;
import old.icloud.services.notes.objects.NoteBook;

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
