package icloud;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Authenticator {

	//TODO: add better error handling
	//TODO: Add event notifiers for all the different authentication events
	
	private static final String loginURL = "https://setup.icloud.com/setup/ws/1/login";
	
	public enum Events {
		SIGNIN, SIGNOUT, SIGNIN_FAIL, SIGNOUT_FAIL;
	}
	
	public static String authenticate(Credentials authInfo) throws CloudException {
		UserSession theNewUser = new UserSession(authInfo);
		try {
			
			if(!SessionManager.getInstance().registerSession(theNewUser)){
				// This code in here should never ever run but its here for safety
				throw new CloudException("Error registering session with SessionManager; Session was null or already registered");
			}
			
			theNewUser.parseBuildInfo(CloudHelper.getBuildInfo());
			HttpResponse<String> authResponse = callCloudAuthentication(theNewUser, loginURL);
			String response = authResponse.getBody();
			
		} catch (AuthenticationException e) {
			throw e;
		}
		//return SessionManager.getInstance().getKeyFor(theNewUser);
		return theNewUser.getSessionID();
	}
	
	public static UserSession deAuthenticate(String test){
		return null;	
	}
	
	private static HttpResponse<String> callCloudAuthentication(UserSession authSession, String url) throws AuthenticationException {
		try {
			HttpResponse<String> authResponse = Unirest.post(url)
			.queryString("clientBuildNumber", authSession.getClientBuildNumber())
			.queryString("clientId", authSession.getSessionID())
			.header("origin", "https://www.icloud.com")
			.body("{\"apple_id\":\"" + authSession.getUsername() + "\",\"password\":\"" + authSession.getPassword() + "\",\"extended_login\":" + authSession.isExtendedLogin() + "}").asString();
			
			if(authResponse.getStatus() == 421){
				throw new AuthenticationException("Error Authenticating User; Server response code was 421");
			}
			
			return authResponse;
		} catch (UnirestException e) {
			throw new AuthenticationException("Failed to authenticate user", e);
		}
		
	}
}
