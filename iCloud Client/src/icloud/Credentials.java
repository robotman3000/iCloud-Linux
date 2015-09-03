package icloud;

import java.net.HttpCookie;
import java.util.HashSet;
import java.util.Set;

public class Credentials {

	// TODO: Should "onAuth" events be put in this class
	private final String username;
	private final String password;
	private final boolean extendedLogin;
	private Set<HttpCookie> sessionTokens = new HashSet<HttpCookie>();
	
	public Credentials(String username, String password, boolean extendedLogin) {
		this.username = username;
		this.password = password;
		this.extendedLogin = extendedLogin;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isExtendedLogin() {
		return extendedLogin;
	}

	public Set<HttpCookie> getTokens(){
		return sessionTokens;
	}
	
	public void updateTokens(Set<HttpCookie> newTokens){
		sessionTokens = new HashSet<HttpCookie>(newTokens);
	}

	public String getTokenValue(String string) {
		for(HttpCookie cookie : sessionTokens){
			if (cookie.getName().equalsIgnoreCase(string)){
				return cookie.getValue();
			}
		}
		return "";
	}
	
	// TODO: Add methods to query and get a specific cookie or set of cookies
}
