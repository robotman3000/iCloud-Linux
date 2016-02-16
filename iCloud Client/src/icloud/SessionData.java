package icloud;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class SessionData {

	protected boolean isPost = false;
	
	protected SessionData(HashMap<CloudConfStoreKeys, String> sessionConfig, Credentials authTokens, UUID sessionKey) {
		startup();
	}
	
	public boolean isPost(){
		return isPost;
	}
	
	protected abstract Map<CloudConfStoreKeys, String> startup();
}
