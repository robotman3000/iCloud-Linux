package old.icloud.services.notes;

import old.icloud.services.BaseConfig;

public class NoteConfig extends BaseConfig {

	private String syncToken;

	public String getSyncToken() {
		return syncToken;
	}

	public void setSyncToken(String syncToken) {
		this.syncToken = syncToken;
	}
}
