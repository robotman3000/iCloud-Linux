package icloud;

import java.net.HttpCookie;
import java.util.List;

public class UserTokenManager {

	private List<HttpCookie> userTokens;
	private final String UUID;

	public UserTokenManager(String UUID, List<HttpCookie> userTokens) {
		this.UUID = UUID;
		this.userTokens = userTokens;
	}

	public List<HttpCookie> getUserTokens() {
		return userTokens;
	}

	public void updateUserTokens(List<HttpCookie> cookies, String UUID) {
		if (this.UUID == UUID) {
			this.userTokens = cookies;
		}
	}
}