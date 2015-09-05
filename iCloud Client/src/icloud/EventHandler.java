package icloud;

import icloud.event.CloudEvent;
import icloud.handler.CloudEventHandler;

public interface EventHandler {
	
	public void addEventHandler(CloudEventHandler evt);

	public void handleCloudEvent(CloudEvent evt);
}
