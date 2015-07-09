package icloud;

import icloud.SessionFactory.SessionTypes;

public class RandomClass {

	String sessionKey;
	
	public void autenticate(){
		Credentials authInfo = new Credentials("username", "password", false);
		sessionKey = Authenticator.authenticate(authInfo);
		SessionManager.enablePush(sessionKey);
		SessionManager.onPushEvent(sessionKey, new AppleEvent(){
			void onEvent(){
				// Do something about it
			};
		});
	}
	
	public void eventTriggeredMethod(){
		NoteSession nSession = SessionManager.getNoteSession(sessionKey);
		nSession.exceuteRequest(new NoteCreateRequest(new Note("The note to be created")));
		Note theNote = nSession.getNote("the note id");
	}
	
}
