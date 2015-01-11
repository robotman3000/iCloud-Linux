package icloud;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;
import java.util.Random;

public class CommonLogic {

	public static final String LOCALHOST = "127.0.0.1";

	public static JsonObject parseJsonData(String jsonData) {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(jsonData);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		return jsonObject;
	}

	public static String generateUUID() {
		new UUID(new Random().nextLong(), new Random().nextLong());
		UUID generatedUUID = UUID.randomUUID();
		return generatedUUID.toString();
	}
	
	public static void splitOut() {
		System.out.println("================================================================================");
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		// Copy Pasted Code

		//
		// To convert the InputStream to String we use the
		// Reader.read(char[] buffer) method. We iterate until the
		// Reader return -1 which means there's no more data to
		// read. We use the StringWriter class to produce the string.
		//
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static boolean stringToBoolean(String string){
		boolean result = false;
		
		if (string.equalsIgnoreCase("true")){
			result = true;
		} else if (string.equalsIgnoreCase("false")){
			result = false;
		}
		return result;
	}
	
	public static String booleanToString (boolean bool){
		String result = "false";
		if (bool){
			result = "true";
		}
		return result;
	}
}
