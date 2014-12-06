package icloud;

import org.apache.commons.codec.binary.Base64;
import java.util.UUID;
import java.util.Random;

public class CommonLogic {

	public static String generateAuthKey(String username, String password) {
		// Generate Authentication String
		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		return authStringEnc;
	}

	public static String generateUUID() {
		new UUID(new Random().nextLong(), new Random().nextLong());
		UUID generatedUUID = UUID.randomUUID();
		return generatedUUID.toString();
	}
}
