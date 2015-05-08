package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.Random;

public class CommonLogic {

	//TODO: Add Exception throwing; Add Exception handling; Add Javadoc
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
		return Boolean.parseBoolean(string);
	}

	public static boolean stringToBoolean1(String string){
		boolean result = false;
		
		if ("true".equalsIgnoreCase(string)){
			result = true;
		} else if ("false".equalsIgnoreCase(string)){
			result = false;
		}
		return result;
	}
	
	public static String booleanToString2(boolean bool){
		String result = null;
		if (bool) result = "true"; else result = "false";

		return result;
	}
	
	public static String booleanToString(boolean bool){
		return (bool ? "true" : "false");
	}
	
	public static String booleanToString1(boolean bool){
		String result = "false";
		if (bool){
			result = "true";
		}
		return result;
	}

	public static void printJson(String jsonToPrint){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonToPrint);
		String result2 = gson.toJson(je);
		
		System.out.println("*** BEGIN JSON PRINT ***");
		System.out.println(result2);
		System.out.println("*** END JSON PRINT ***");
	}
	
	public static boolean isNull(Object obj){
		if(obj != null){
			return true;
		}
		return false;
	}
	
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
 
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
    } 
}
