package icloud;
import icloud.json.JsonBody;
import icloud.request.event.RequestErrorEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.event.RequestRecievedEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

import common.http.URLBuilder;


public class CloudSessionManager {

	private static final CloudSessionManager self = new CloudSessionManager();
	private File defaultConfPath = new File("cloudConf.json");
	private CloudSessionManager(){}
	public synchronized static CloudSessionManager getInstance(){ return self; }
	
	private HashMap<UUID, UserSession> sessions = new HashMap<UUID, UserSession>();
	
	public synchronized void executeRequest(UUID sessionKey, Request theRequest){
		
		JsonBody body = theRequest.toJson();
		URL url = theRequest.getURL();
		Map<String, String> queryStrings = theRequest.getQueryStrings();
		
		// Use Gson to convert the JsonBody to a string
		String strBody = new Gson().toJson(body);
		if(theRequest.isPostReq()){
			// TODO: Finish this request line
			HttpRequestWithBody request = Unirest.post(url.toString());
			for(String key : queryStrings.keySet()){
				request.queryString(key, queryStrings.get(key));
			}
		}
		theRequest.handleCloudEvent(new RequestMadeEvent());
		// Get response back and save it
		theRequest.handleCloudEvent(new RequestRecievedEvent());
		// If response code is not error then
		//     Get the usersession NoteSessionData with the given key
			   UserSession uSession = sessions.get(sessionKey);
			   
		       theRequest.parseResponse(uSession.getNoteSessionData(), null/*The Response Object /*The Config Map*/);
		       
		// Else
		//     The error object is created it will contain the exception
		//     Object if applicable and all data related to the error
		       theRequest.handleCloudEvent(new RequestErrorEvent());
		       
	}
	
	private String makeURLStr(URL url, Map<String, String> queryStrings) {
		URLBuilder theUrl = new URLBuilder(url);
		theUrl.setQueryStringMap(queryStrings);
		return theUrl.toString();
	}
	
	protected synchronized UserSession getSession(UUID sessionID) {
		return sessions.get(sessionID);
	}
	
	public UUID createSession(Credentials authKeys) {
		UserSession session = new UserSession(UUID.randomUUID(), authKeys);
		loadCloudConfig(defaultConfPath, session);
		return null;
	}
	
	private void loadCloudConfig(File confPath, UserSession session) {
		JsonElement rootTree = null;
		try {
			rootTree = new JsonParser().parse(new FileReader(confPath));
		} catch (JsonIOException e1) {
			e1.printStackTrace();
		} catch (JsonSyntaxException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			saveDefaultConf(confPath);
			e1.printStackTrace();
		}
		
		for (CloudConfStoreKeys.AppNames app : CloudConfStoreKeys.AppNames.values()){
			JsonObject appsList = rootTree.getAsJsonObject().getAsJsonObject(app.name());
			for(CloudConfStoreKeys.ServerRequestType reqType : CloudConfStoreKeys.ServerRequestType.values()){
				JsonElement var = appsList.get(reqType.name());
				if(var != null){
					JsonObject appReq = var.getAsJsonObject();
					for(CloudConfStoreKeys.ServerRequestKeys reqKey : CloudConfStoreKeys.ServerRequestKeys.values()){
						String theKey = app + "." + reqType + "." + reqKey;
						JsonElement theValueE = appReq.get(reqKey.name());
						if(theValueE != null){
							String theValue = theValueE.getAsString();
							System.out.println("Loading key: " + theKey + " = " + theValue);
						} else {
							System.out.println("Skipping Key: " + theKey);
						}
					}
				} else {
					System.out.println("Ignoring Key: " + app + "." + reqType);
				}
			}
		}
		
/*		try {
			FileOutputStream out = new FileOutputStream(confPath);
			cloudConf.store(out, "This is the config file for Libcloudlinux");
		} catch (IOException e) {
			System.err.println("Error: Cannot save cloudConfig");
			e.printStackTrace();
		}*/
	}
	
	private void saveDefaultConf(File confPath) {
		
		JsonObject root = new JsonObject();
		
		for (CloudConfStoreKeys.AppNames app : CloudConfStoreKeys.AppNames.values()){
			JsonObject appJ = new JsonObject();
			root.add(app.name(), appJ);
			for(CloudConfStoreKeys.ServerRequestType reqType : CloudConfStoreKeys.ServerRequestType.values()){
				JsonObject reqTJ = new JsonObject();
				appJ.add(reqType.name(), reqTJ);
				for(CloudConfStoreKeys.ServerRequestKeys reqKey : CloudConfStoreKeys.ServerRequestKeys.values()){
					reqTJ.addProperty(reqKey.name(), "null");
				}
			}
		}
		
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gson.toJson(root));
			FileOutputStream out = new FileOutputStream(confPath);
			out.write(gson.toJson(root).getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void endSession(UUID sessionKey) {
		// TODO Auto-generated method stub
		
	}
	
	public void authenticateSession(UUID sessionKey) {
		// TODO Auto-generated method stub
		
	}
	
	public void deAuthenticateSession(UUID sessionKey) {
		// TODO Auto-generated method stub
		
	}
}



