package common;

import icloud.services.URLConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class URLBuilder {
	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	private String protocol = "http:\\";
	private int port = 80;
	private String host = "example.com";
	private String file = "index.html";
	private HashMap<String, String> queryStringMap = new HashMap<>();
	private SystemLogger logger = new SystemLogger(SystemLogger.LoggingVerboseity.NONE); 
	
	// TODO: Make all applicable methods use the systemlogger
	public URLBuilder(String protocol, String host, int port, String file, HashMap<String, String> keyMap){
		this.setProtocol(protocol);
		this.setHost(host);
		this.setPort(port);
		this.setPath(file);
		this.setQueryStringMap(keyMap);	
	}
	
	public URLBuilder(String protocol, String host, int port, String file, KeyValuePair... keyValues){
		HashMap<String, String> list = new HashMap<>();
		if(keyValues != null && keyValues.length > 0){
			for(KeyValuePair kv : keyValues){
				if(kv.getKey() != null && kv.getValue() != null && kv.getKey().length() > 0 && kv.getValue().length() > 0){
					list.put(kv.getKey(), kv.getValue());
				}
			}
		}
		this.setProtocol(protocol);
		this.setHost(host);
		this.setPort(port);
		this.setPath(file);
		this.setQueryStringMap(list);	
	}

	public URLBuilder(String protocol, String host, int port, String file){
		this(protocol, host, port, file, new KeyValuePair("", ""));
	}
	
	public URLBuilder(String protocol, String host, int port){
		this(protocol, host, port, "");
	}
	
	public URLBuilder(String protocol, String host) {
		this(protocol, host, URLConfig.default_port, "");
	}
	
	public URLBuilder(String host) {
		this(URLConfig.default_protocol, host);
	}
	
	public URLBuilder() {
		
	}
	
	public URL buildURL() throws MalformedURLException {
		Iterator<String> it = queryStringMap.keySet().iterator();
		StringBuilder queryStringB = new StringBuilder();
		while (it.hasNext()) {
			String var = it.next();
			String var2 = queryStringMap.get(var);
			String var3 = var + "=" + var2;
			queryStringB.append(var3 + "&");
		}
		queryStringB.deleteCharAt(queryStringB.length() - 1);

		URL url;
		StringBuilder str = new StringBuilder();

		if (protocol != null) {
			str.append(protocol + "://");
		}

		if (host != null) {
			str.append(host);
		}

		if (port != -1) {
			str.append(":" + port);
		}

		if (file != null) {
			str.append(file);
		}
		str.append(queryStringB.toString());
		// url = new URL(protocol + host + ":" + port + file +
		// queryStringB.toString());
		url = new URL(str.toString());
		return url;
	}
	
	public URLBuilder addQueryString(String key, String value) {
		queryStringMap.put(key, value);
		return this;
	}

	public void removeQueryString(String key) {
		if (queryStringMap.containsKey(key)) {
			queryStringMap.remove(key);
		}
	}
	
	public URLBuilder setPath(String path) {
		if(path != null && path.length() > 0){
			this.file = cleanSlashes(path);
		} else {
			//TODO: throw new InvalidPathException();
		}
		return this;
	}

	public URLBuilder setPort(int port) {
		// Nothing needed here as int's can't be null
		this.port = port;
		return this;
	}

	public URLBuilder setProtocol(String protocol) {
		try {
			String newProtocol = protocol.replaceAll("://", "");
			@SuppressWarnings("unused")
			URL url = new URL(newProtocol, "example.com", "/index.html"); /* This will throw a MalformedURLException if something is wrong
			* so we can safely assume that the protocol is at fault and not the hard coded values */
			this.protocol = newProtocol;
		} catch (MalformedURLException e) {
			//TODO: throw new InvalidProtocolException
			e.printStackTrace();
		}
		return this;
	}

	public URLBuilder setQueryStringMap(HashMap<String, String> queryStrings) {
		// Make a new map as to stop uncontroled modifications
		for(String key : queryStrings.keySet()){
			for(String value : queryStrings.values()){
				this.queryStringMap.put(key, value);
			}
		}
		return this;
	}

	public URLBuilder setHost(String host) {
		if(host != null && host.length() > 0){
			this.host = cleanSlashes(host);
		} else {
			//TODO: throw new InvalidHostException();
		}
		return this;
	}

	public URLBuilder setLogger(SystemLogger logger){
		// Don't make a new copy so that logger settings stay in sync by using the same object reference
		//TODO: fix a null pointer exeception that could occur if the logger is made null by external sources
		if (logger != null){
			this.logger = logger;
		}
		return this;
	}
	
	public String getPath() {
		return file;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public Map<String, String> getQueryStringMap() {
		// Make a new map as to stop uncontroled modifications
		HashMap<String, String> listor = new HashMap<>();
		for(String key : queryStringMap.keySet()){
			for(String value : queryStringMap.values()){
				listor.put(key, value);
			}
		}
		return listor;
	}

	public String getUrl() {
		return host;
	}
	
	private String cleanSlashes(String cleanMe){
		String layer1 = cleanMe.trim();
		int length = layer1.length();
		if(layer1.charAt(length - 1) == "/".charAt(0) 
				|| layer1.charAt(length - 1) == "\\".charAt(0)){
			StringBuilder str = new StringBuilder(layer1);
			str.deleteCharAt(str.length());
			return str.toString();
		}
		return layer1;
	}
}
