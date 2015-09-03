package icloud.request.handler;

import icloud.handler.CloudEventHandler;
import icloud.request.event.RequestEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.event.RequestRecievedEvent;

public interface RequestEventHandler extends CloudEventHandler {

	public void onRequestEvent(RequestEvent evt);
	public void onRequestMadeEvent(RequestMadeEvent evt);
	public void onRequestReceivedEvent(RequestRecievedEvent evt);
}
