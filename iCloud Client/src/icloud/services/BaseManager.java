package icloud.services;

import java.net.URL;

import com.google.gson.Gson;

public abstract class BaseManager {

	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	// TODO: add a method generate the default headers
	protected boolean isInitialized = false;
	protected URL rootURL;
	protected Gson gson = new Gson();
	
	protected BaseManager(){
		this.isInitialized = true;
	}
	
	protected URL getRootURL(){
		return rootURL;
	}
	
	protected abstract void setRootURL(String newUrl);
}
