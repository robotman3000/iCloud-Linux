package icloud;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;

public class UserSession {

	private final Credentials authTokens;
	private final HashMap<SessionConfKeys, String> sessionConfig = new HashMap<SessionConfKeys, String>();
	
	protected UserSession(String buildNum, Credentials authKeys, HttpResponse<String> authResponse) {
		authTokens = authKeys;
		sessionConfig.put(SessionConfKeys.buildNumber, buildNum);
		parseBody(authResponse.getBody());
		makeCookies(authResponse.getHeaders());
		
	}

	private void makeCookies(Headers headers) {
		for (String str : headers.keySet()){
			if(str.equalsIgnoreCase("set-cookie")){
				List<String> cookies = headers.get(str);
				Set<HttpCookie> cookieSet = new HashSet<HttpCookie>();
				for(String cookieA : cookies){
					for (HttpCookie cookieB : HttpCookie.parse(cookieA)){
						cookieSet.add(cookieB);
					}
				}
				authTokens.updateTokens(cookieSet);
				//HttpCookie.parse(header);
				/*for(String cookie : cookies){
					HttpCookie cookie2;
					int index2 = 0;
					splitLoop:
					for(int index = 0; index < cookie.length(); index++){
						if(String.valueOf(cookie.charAt(index)) == ";"){
							String[] var = cookie.substring(0, index).split("="); // Split the cookie name and its value
							index2 = index;
							cookie2 = new HttpCookie(var[0], var[1]);
							break splitLoop;
						}
					}
					String[] cookiePeices = cookie.substring(index2, cookie.length() - 1).split(";");
					for (String cookieChip : cookiePeices){
						if(cookieChip.contains("HttpOnly")){
							cookie2.setDomain(pattern);
							cookie2.setHttpOnly(httpOnly);
							cookie2.setMaxAge(expiry);
							cookie2.p
						} else if(cookieChip.contains("")){
							
						} else if(cookieChip.contains("")){
							
						} else if(cookieChip.contains("")){
							
						}
						cookie2.setSecure(flag);
					}
				}*/
			}
		}
	}

	private void parseBody(String body) {
		//SessionBody theBody = new Gson().fromJson(body, SessionBody.class);
		// TODO: Parse any useful data from theBody
	}
	
	protected HashMap<SessionConfKeys, String> getSessionConfig(){
		return sessionConfig;
	}
	
	public String getUsername(){
		return authTokens.getUsername();
	}
	
	public String getPassword(){
		return authTokens.getPassword();
	}

	public boolean isExtendedLogin(){
		return authTokens.isExtendedLogin();
	}
	
	public Credentials getCredentials(){
		return authTokens;
	}
}
