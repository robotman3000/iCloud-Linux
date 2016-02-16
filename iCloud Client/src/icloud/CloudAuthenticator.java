package icloud;

import java.io.IOException;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CloudAuthenticator {

	public static final String BASE_URL = "https://www.icloud.com";
	public static final String LOGIN_URL = "https://setup.icloud.com/setup/ws/1/login";
	public static final String LOGOUT_URL = "https://setup.icloud.com/setup/ws/1/logout";
	
	protected static UUID authenticate(Credentials authKeys) {
		String buildNum = getBuildNumber();
		UUID sessionKey = UUID.randomUUID();
		HttpResponse<String> authResponse = doSingleStepAuth(authKeys, buildNum, sessionKey);
		if(authResponse.getStatus() != 200){
			//authKeys.handleCloudEvent(new CloudErrorEvent(/*The Error object/any exceptions generated*/));
			return null;
		}
		CloudSessionManager.getInstance().initNewSession(authResponse, buildNum, sessionKey, authKeys);
		return sessionKey;
	}
	
	private static HttpResponse<String> doSingleStepAuth(Credentials authKeys, String buildNum, UUID sessionID) {
		try {
			return Unirest.post(LOGIN_URL)
			.queryString("clientBuildNumber", buildNum)
			.queryString("clientId", sessionID.toString())
			.header("origin", "https://www.icloud.com")
			.body("{\"apple_id\":\"" + authKeys.getUsername() + "\",\"password\":\"" + authKeys.getPassword() + "\",\"extended_login\":" + authKeys.isExtendedLogin() + "}").asString();
		} catch (UnirestException e) {
			// TODO: Trigger a "failed" event and put the exception obj inside
			//throw new AuthenticationException("Failed to authenticate user", e);
		}
		
		// This is to make the compiler happy
		return null;
	}

	private static HttpResponse<String> doDeAuth(Credentials authKeys, String buildNum, UUID sessionID) {
		HttpResponse<String> resp = null;
		try {
			Unirest.post(LOGOUT_URL)
			.queryString("clientBuildNumber", buildNum)
			.queryString("clientId", sessionID.toString())
			.queryString("dsid", CloudSessionManager.getInstance().getSession(sessionID).getSessionConfig().get(CloudConfStoreKeys.dsinfo_dsid))
			.queryString("token", CloudSessionManager.getInstance().getSession(sessionID).getCredentials().getTokenValue("X-APPLE-WEBAUTH-VALIDATE"))
			.header("origin", "https://www.icloud.com")
			.body("{}").asString();
			Unirest.shutdown();
		} catch (UnirestException e) {
			// TODO: Trigger a "failed" event and put the exception obj inside
			//throw new AuthenticationException("Failed to authenticate user", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return resp;
	}
	
	private static String getBuildNumber() {
		String buildInfo = "";
		//boolean numberFound = false;
		try {
			HttpResponse<String> response = Unirest.get(BASE_URL).asString();
			Document httpDoc = Jsoup.parse(response.getBody());
			Elements scripts = httpDoc.head().select("script");
			
			forLoop:
			for(Element script : scripts){
				String text = script.toString();
				if(text.contains("BUILD_INFO")){
					String aStr = script.html().replace("BUILD_INFO=", "");
					String newJson = aStr.substring(0, aStr.length() - 1); // Remove the last char from the string; ie the ";"
					for(String str : newJson.split(",")){
						if(str.contains("buildNumber")){
							String[] str2 = str.split(":");
							buildInfo = str2[str2.length - 1].replaceAll("\"", ""); // This removes any " in the string
						}
					}
					//numberFound = true;
					break forLoop;
				}
			}
/*			if(!numberFound){
				throw new CloudException("Error obtaining Build Number");
			}*/
		} catch (UnirestException e) {
			//throw new CloudException("Error obtaining Build Info", e);
		}
		return buildInfo;
	}

	protected static void deAuthenticate(UUID sessionKey){
		UserSession session;
		if ((session = CloudSessionManager.getInstance().getSession(sessionKey)) != null){
			doDeAuth(session.getCredentials(), session.getSessionConfig().get(CloudConfStoreKeys.buildNumber), sessionKey);
		}
	}
}