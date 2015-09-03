package icloud;
import icloud.json.JsonBody;
import icloud.request.event.RequestErrorEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.event.RequestRecievedEvent;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;

import apps.note.NoteSessionData;


public class CloudSessionManager {

	private static final CloudSessionManager self = new CloudSessionManager();
	private CloudSessionManager(){}
	public static CloudSessionManager getInstance(){ return self; }
	
	private HashMap<UUID, UserSession> sessions = new HashMap<UUID, UserSession>();
	
	public void executeRequest(UUID sessionKey, Request theRequest){
		
		JsonBody body = theRequest.toJson();
		URL url = theRequest.getURL();
		
		// Use Gson to convert the JsonBody to a string
		// Make the request using Unirest
		theRequest.handleCloudEvent(new RequestMadeEvent());
		// Get response back and save it
		theRequest.handleCloudEvent(new RequestRecievedEvent());
		// If response code is not error then
		//     Get the usersession NoteSessionData with the given key
			   UserSession uSession = sessions.get(sessionKey);
			   
		       theRequest.parseResponse(new NoteSessionData() /* this object is stored inside usersession*/ /*, The Response Object, */ /*The Config Map*/);
		       
		// Else
		//     The error object is created it will contain the exception
		//     Object if applicable and all data related to the error
		       theRequest.handleCloudEvent(new RequestErrorEvent());
		       
	}
	
	protected void initNewSession(HttpResponse<String> authResponse, String buildNum, UUID sessionKey, Credentials authKeys) {
		sessions.put(sessionKey, new UserSession(buildNum, authKeys, authResponse));
	}
	
	protected UserSession getSession(UUID sessionID) {
		return sessions.get(sessionID);
	}
}



