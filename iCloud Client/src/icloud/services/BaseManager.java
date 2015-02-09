package icloud.services;

public class BaseManager {

	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	@Deprecated
	protected String serverURL;
	protected boolean isInitialized = false;
	protected boolean announceConnections = false;
	protected boolean debugEnabled = false;

	@Deprecated
	protected String getServerURL() {
		return serverURL;
	}

	@Deprecated
	protected void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
}
