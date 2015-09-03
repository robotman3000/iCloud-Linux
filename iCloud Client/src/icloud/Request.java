package icloud;

import icloud.event.CloudEvent;
import icloud.handler.CloudEventHandler;
import icloud.json.Jsonable;
import icloud.request.event.RequestEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.handler.RequestEventHandler;

import java.net.URL;

import apps.note.NoteSessionData;

public abstract class Request implements Jsonable {

	public URL getURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEventHandler(CloudEventHandler evt){
		// Add event to listeners list
	}

	public void handleCloudEvent(CloudEvent theEvent) {
		// TODO Auto-generated method stub; Add this method to an interface
		// Loop through listeners list and call
		// evt.onEvent(requestMadeEvent);
		
	}

	public void parseResponse(SessionData sessionData) {
		// TODO Auto-generated method stub
		
	}
		
}
