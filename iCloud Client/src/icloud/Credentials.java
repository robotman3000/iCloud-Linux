package icloud;

import java.net.HttpCookie;
import java.util.Set;

public class Credentials {

	private String username;
	private String password;
	private boolean extendedLogin = false;
	private Set<HttpCookie> accessTokens;
	
	public Credentials(String username, String password, boolean doExtendedLogin){
		if (username == null || password == null || username.isEmpty() || password.isEmpty()){
			throw new IllegalArgumentException("Invalid Args Provided");
		}
		this.username = username;
		this.password = password;
		this.extendedLogin = doExtendedLogin;
		System.out.println("abc");
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

	protected boolean updateAccessTokens(Set<HttpCookie> accessTokens){
		
		return true; //TODO: Return true on success and false on failior
	}
	
	public Set<HttpCookie> getAccessTokens() {
		return accessTokens;
	}
}
