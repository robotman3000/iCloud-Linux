package icloud;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

	private SessionManager() {
	}

	private static SessionManager self = new SessionManager();

	public static synchronized SessionManager getInstance() {
		return self;
	}

	private Map<String, UserSession> sessionMap = new HashMap<String, UserSession>();

	protected synchronized boolean registerSession(UserSession session) {
		boolean result = false;
		if (session != null) {
			if (!sessionMap.containsValue(session)) {
				sessionMap.put(session.getSessionID(), session);
				result = true;
			}
		}
		return result;
	}
	
	public synchronized SessionReader getSessionReader(String sessionKey){
		
	}
	
	//public static NoteSession getNoteSession(String sessionKey){
		
	//}
}
