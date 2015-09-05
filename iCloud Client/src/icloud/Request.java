package icloud;

import icloud.event.CloudEvent;
import icloud.handler.CloudEventHandler;
import icloud.json.JsonBody;
import icloud.json.Jsonable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public abstract class Request implements Jsonable, EventHandler {

	protected boolean isPost = true;
	protected URL requestURL;
	protected ArrayList<CloudEventHandler> eventHandlerList = new ArrayList<CloudEventHandler>(); 
	
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

	public Map<String, Object> getQueryStrings() {
		// TODO Auto-generated method stub
		return null;
	}	
}
