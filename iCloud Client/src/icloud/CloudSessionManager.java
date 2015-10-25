package icloud;
import icloud.json.JsonBody;
import icloud.request.event.RequestErrorEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.event.RequestRecievedEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import common.http.URLBuilder;


public class CloudSessionManager {

	private static final CloudSessionManager self = new CloudSessionManager();
	private CloudSessionManager(){}
	public synchronized static CloudSessionManager getInstance(){ return self; }
	
	private HashMap<UUID, UserSession> sessions = new HashMap<UUID, UserSession>();
	
	public synchronized void executeRequest(UUID sessionKey, Request theRequest){
		
		JsonBody body = theRequest.toJson();
		URL url = theRequest.getURL();
		Map<String, String> queryStrings = theRequest.getQueryStrings();
		
		// Use Gson to convert the JsonBody to a string
		String strBody = new Gson().toJson(body);
		if(theRequest.isPostReq()){
			// TODO: Finish this request line
			HttpRequestWithBody request = Unirest.post(url.toString());
			for(String key : queryStrings.keySet()){
				request.queryString(key, queryStrings.get(key));
			}
		}
		theRequest.handleCloudEvent(new RequestMadeEvent());
		// Get response back and save it
		theRequest.handleCloudEvent(new RequestRecievedEvent());
		// If response code is not error then
		//     Get the usersession NoteSessionData with the given key
			   UserSession uSession = sessions.get(sessionKey);
			   
		       theRequest.parseResponse(uSession.getNoteSessionData(), null/*The Response Object /*The Config Map*/);
		       
		// Else
		//     The error object is created it will contain the exception
		//     Object if applicable and all data related to the error
		       theRequest.handleCloudEvent(new RequestErrorEvent());
		       
	}
	
	private String makeURLStr(URL url, Map<String, String> queryStrings) {
		URLBuilder theUrl = new URLBuilder(url);
		theUrl.setQueryStringMap(queryStrings);
		return theUrl.toString();
	}

	protected synchronized void initNewSession(HttpResponse<String> authResponse, String buildNum, UUID sessionKey, Credentials authKeys) {
		sessions.put(sessionKey, new UserSession(buildNum, authKeys, authResponse, sessionKey));
	}
	
	protected synchronized UserSession getSession(UUID sessionID) {
		return sessions.get(sessionID);
	}
}



