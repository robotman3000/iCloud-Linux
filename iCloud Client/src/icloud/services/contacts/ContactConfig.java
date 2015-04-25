package icloud.services.contacts;

import icloud.services.BaseConfig;
import icloud.services.contacts.objects.HeaderPositions;

public class ContactConfig extends BaseConfig {
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

	public void setHeaderPositions(HeaderPositions headerPositions) {
		// TODO Auto-generated method stub
		
	}
}
