package common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class URLBuilder {
	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	private String protocol = "";
	private int port = -1;
	private String host = "";
	private String file = "";
	private Map<String, String> queryStringMap = new HashMap<String, String>();

	// TODO: add more utility methods and constructors
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
			str.append(protocol);
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

	public String getProtocol() {
		return protocol;
	}

	public URLBuilder setProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public int getPort() {
		return port;
	}

	public URLBuilder setPort(int port) {
		// TODO: auto remove any trailing "/"
		this.port = port;
		return this;
	}

	public String getUrl() {
		return host;
	}

	public URLBuilder setUrl(String url) {
		// TODO: auto remove any trailing "/"
		this.host = url;
		return this;
	}

	public String getPath() {
		return file;
	}

	public URLBuilder setPath(String path) {
		// TODO: auto remove any trailing "/"
		this.file = path;
		return this;
	}

	public Map<String, String> getQueryStringMap() {
		return queryStringMap;
	}

	public void setQueryStringMap(Map<String, String> queryStringMap) {
		this.queryStringMap = queryStringMap;
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
}
