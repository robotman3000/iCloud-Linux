package icloud.services.contacts;

public class ContactConfig {
	private String prefToken;
	private String syncToken;

	public String getSyncToken() {
		return syncToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}

	public String getPrefToken() {
		return prefToken;
	}

	public void setPrefToken(String prefToken) {
		this.prefToken = prefToken;
	}
}
