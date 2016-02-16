package icloud;

import icloud.event.CloudEvent;
import icloud.handler.CloudEventHandler;
import icloud.json.JsonBody;
import icloud.json.Jsonable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import apps.note.request.NoteRequest;

public abstract class Request implements Jsonable, EventHandler {

	protected boolean isPost = true;
	protected URL requestURL;
	protected String requestPath;
	protected ArrayList<CloudEventHandler> eventHandlerList = new ArrayList<CloudEventHandler>();
	protected Map<String, String> queryStrings = new HashMap<String, String>();
	
	protected void populateVars(UUID sessionKey, Request sender){
		UserSession session = CloudSessionManager.getInstance().getSession(sessionKey);
		
		if(sender instanceof NoteRequest){
			// Doesn't changing my own vars do the exact same thing as editing senders?
			// My reasoning is that they are the same reference because sender is an instance of "me" (Request)
			try {
				sender.requestURL = new URL(session.getSessionConfig().get(CloudConfStoreKeys.noteApp_baseURL).toString() + sender.requestPath);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sender.isPost = session.getNoteSessionData().isPost();
			sender.setQueryStrings(session.getSessionConfig(), session.getSessionID());
		} // else if(sender instanceof ...) 
	}
	
	public URL getURL() {
		return requestURL;
	}

	public abstract void parseResponse(SessionData sessionData, JsonBody jsonBody);

	@Override
	public final void addEventHandler(CloudEventHandler evt) {
		eventHandlerList.add(evt);
	}

	@Override
	public void handleCloudEvent(CloudEvent evt) {
		for (CloudEventHandler hand : eventHandlerList){
			hand.onEvent(evt);
		}
	}

	public boolean isPostReq() {
		return isPost;
	}

	protected abstract void setQueryStrings(HashMap<CloudConfStoreKeys, String> hashMap, UUID uuid);
	
	public Map<String, String> getQueryStrings() {
		return queryStrings;
	}	
}
