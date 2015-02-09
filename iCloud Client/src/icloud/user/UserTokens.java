package icloud.user;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserTokens {

	private List<HttpCookie> userTokens = new ArrayList<HttpCookie>();

	public UserTokens() {
	}

	@Deprecated
	public UserTokens(List<HttpCookie> userTokens) {
		updateTokens(userTokens);
	}

	public String getTokenValue(String tokenName) {
		String tokenValue = "";
		Iterator<HttpCookie> iterator = userTokens.iterator();
		while (iterator.hasNext()) {
			HttpCookie key = iterator.next();
			if ((key != null) && (tokenName != null) && (tokenName.equalsIgnoreCase(key.getName()))) {
				tokenValue = key.getValue();
			}
		}
		return tokenValue;
	}

	public List<HttpCookie> getTokens() {
		List<HttpCookie> returnedTokens = new ArrayList<HttpCookie>();
		returnedTokens.addAll(userTokens);
		return returnedTokens;
	}

	public void updateTokens(List<HttpCookie> userTokens) {
		if (userTokens != null && userTokens.size() > 0) {
			this.userTokens.clear();
			this.userTokens.addAll(userTokens);
		}
	}
}