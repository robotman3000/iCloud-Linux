package icloud.handler;

import icloud.event.CloudEvent;

public interface CloudEventHandler {

	public abstract void onEvent(CloudEvent evt);
}
