package icloud;

import java.net.HttpCookie;
import java.util.HashSet;
import java.util.Set;

public class Credentials {

	// TODO: Should "onAuth" events be put in this class
	private final String username;
	private final String password;
	private final boolean extendedLogin;
	private AuthenticationTypes authMode;
	private Set<HttpCookie> sessionTokens = new HashSet<HttpCookie>();
	
	public enum AuthenticationTypes{
		PASSWORD, TWO_STEP
	}
	
	public Credentials(String username, String password, boolean extendedLogin, AuthenticationTypes password2) {
		this.username = username;
		this.password = password;
		this.extendedLogin = extendedLogin;
		this.authMode = password2;
		if(authMode == AuthenticationTypes.TWO_STEP){
			System.err.println("Warning: Two step authentication is not supported yet!\nReverting back to password authentication");
			authMode = AuthenticationTypes.PASSWORD;
		}
	}

	// TODO: Should the username and password getters become protected?
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isExtendedLogin() {
		return extendedLogin;
	}

	protected Set<HttpCookie> getTokens(){
		return sessionTokens;
	}
	
	protected void updateTokens(Set<HttpCookie> newTokens){
		sessionTokens = new HashSet<HttpCookie>(newTokens);
	}

	protected String getTokenValue(String string) {
		for(HttpCookie cookie : sessionTokens){
			if (cookie.getName().equalsIgnoreCase(string)){
				return cookie.getValue();
			}
		}
		return "";
	}
	
	protected AuthenticationTypes getAuthMode(){
		return authMode;
	}
	
	// TODO: Add methods to query and get a specific cookie or set of cookies
}
