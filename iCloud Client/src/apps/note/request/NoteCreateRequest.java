package apps.note.request;

import java.util.HashMap;
import java.util.UUID;

import icloud.CloudConfStoreKeys;
import icloud.SessionData;
import icloud.event.CloudEvent;
import icloud.json.JsonBody;
import apps.note.NoteSessionData;

public class NoteCreateRequest extends NoteRequest {

	public NoteCreateRequest(){
		this.populateVars(null, this); // The null is the session key to be
	}
	
	@Override
	public JsonBody toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseResponse(SessionData sessionData, JsonBody jsonBody) {
		if(sessionData instanceof NoteSessionData){
			NoteSessionData nSData = (NoteSessionData) sessionData;
			
		}
	}
	
	@Override
	public void handleCloudEvent(CloudEvent evt){
		// Overide Super if extra parsing is needed
		// or "extend" super with
		super.handleCloudEvent(evt);
		// Note that if super is not "extended" some of the events may not be properly handled
		
	}

	@Override
	protected void setQueryStrings(HashMap<CloudConfStoreKeys, String> hashMap, UUID uuid) {

	}
}
