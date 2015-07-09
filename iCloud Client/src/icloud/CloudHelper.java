package icloud;

import icloud.json.BuildInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * This class contains all the logic shared between classes
 * @author enims
 *
 */
public class CloudHelper {

	public static final String baseURL = "https://www.icloud.com";
	
	public static BuildInfo getBuildInfo() throws CloudException {
		BuildInfo buildInfo = null;
		boolean numberFound = false;
		try {
			HttpResponse<String> response = Unirest.get(baseURL).asString();
			Document httpDoc = Jsoup.parse(response.getBody());
			Elements scripts = httpDoc.head().select("script");
			
			forLoop:
			for(Element script : scripts){
				String text = script.toString();
				if(text.contains("BUILD_INFO")){
					String aStr = script.html().replace("BUILD_INFO=", "");
					String newJson = aStr.substring(0, aStr.length() - 1); // Remove the last char from the string; ie the ";"
					Gson gson = new Gson();
					buildInfo = gson.fromJson(newJson, BuildInfo.class);
					numberFound = true;
					break forLoop;
				}
			}
			if(!numberFound){
				throw new CloudException("Error obtaining Build Number");
			}
		} catch (UnirestException e) {
			throw new CloudException("Error obtaining Build Info", e);
		}
		return buildInfo;
	}
}
