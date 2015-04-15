package common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class URLBuilder {
	// TODO: Add Javadoc; auto insert default values for missing args
	private String protocol = "";
	private int port = -1;
	private String host = "";
	private String file = "";
	private HashMap<String, String> queryStringMap = new HashMap<>();

	// TODO: Make all applicable methods use the systemlogger
	public URLBuilder(String protocol, String host, int port, String file, HashMap<String, String> keyMap) {
		this(protocol, host, port, file);
		this.setQueryStringMap(keyMap);
	}

	public URLBuilder(String protocol, String host, int port, String file, KeyValuePair... keyValues) {
		this(protocol, host, port, file);

		HashMap<String, String> list = new HashMap<>();
		if (keyValues != null && keyValues.length > 0) {
			for (KeyValuePair kv : keyValues) {
				if (kv.getKey() != null && kv.getValue() != null && kv.getKey().length() > 0
						&& kv.getValue().length() > 0) {
					list.put(kv.getKey(), kv.getValue());
				}
			}
		}
		this.setQueryStringMap(list);
	}

	public URLBuilder(String protocol, String host, int port, String file) {
		this(protocol, host, port);
		setPath(file);
	}

	public URLBuilder(String protocol, String host, int port) {
		this(protocol, host);
		setPort(port);
	}

	public URLBuilder(String protocol, String host) {
		this(host);
		setProtocol(protocol);
	}

	public URLBuilder(String host) {
		setHost(host);
	}

	public URLBuilder(URL fromUrl) {
		URI theUri;
		try {
			theUri = fromUrl.toURI();
			setProtocol(theUri.getAuthority());
			setHost(theUri.getHost());
			setPath(theUri.getPath());
			setPort(theUri.getPort());

			String[] list = theUri.getQuery().split("&");
			for (String str : list) {
				String[] abc = str.trim().replaceFirst("=", "\n").split("\n");
				if (abc.length == 2) {
					addQueryString(abc[0], abc[1]);
				} else {
					System.err.println("Skipped adding query string: " + str);
				}
			}
		} catch (URISyntaxException e) {
			// This shouln't ever happen. if it does then either my reasoning is
			// wrong or there is a bug somewhere
			e.printStackTrace();
		}
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
		if (path != null && path.length() > 0) {
			this.file = cleanSlashes(path);
		} else {
			throw new IllegalArgumentException("Invalid path provided");
		}
		return this;
	}

	public URLBuilder setPort(int port) {
		// Nothing needed here as int's can't be null
		if (port > 0) {
			this.port = port;
		} else {
			port = -1; // The "invalid" port used later in buildUrl()
		}

		return this;
	}

	public URLBuilder setProtocol(String protocol) {
		try {
			String newProtocol = protocol.replaceAll("://", "");
			@SuppressWarnings("unused")
			/*
			 * This will throw a MalformedURLException if something is wrong so
			 * we can safely assume that the protocol is at fault and not the
			 * hard coded values
			 */
			URL url = new URL(newProtocol, "example.com", "/index.html");
			this.protocol = newProtocol;
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid protocol provided");
		}
		return this;
	}

	public URLBuilder setQueryStringMap(HashMap<String, String> queryStrings) {
		// Make a new map as to stop uncontroled modifications
		for (String key : queryStrings.keySet()) {
			for (String value : queryStrings.values()) {
				this.queryStringMap.put(key, value);
			}
		}
		return this;
	}

	public URLBuilder setHost(String host) {
		if (host != null && host.length() > 0) {
			this.host = cleanPort(cleanProtocol(cleanSlashes(host)));
		} else {
			throw new IllegalArgumentException("Invalid host provided");
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
		for (String key : queryStringMap.keySet()) {
			for (String value : queryStringMap.values()) {
				listor.put(key, value);
			}
		}
		return listor;
	}

	public String getUrl() {
		return host;
	}

	private String cleanSlashes(String cleanMe) {
		String layer1 = cleanMe.trim();
		int length = layer1.length();
		if (layer1.charAt(length - 1) == "/".charAt(0) || layer1.charAt(length - 1) == "\\".charAt(0)) {
			StringBuilder str = new StringBuilder(layer1);
			str.deleteCharAt(str.length());
			return str.toString();
		}
		return layer1;
	}

	private String cleanProtocol(String cleanMe) {
		String str1 = cleanMe.replaceFirst("://", "@@@@");
		if (str1.contains("@@@@")) {
			String[] str2 = str1.split("@@@@");
			String cleaned = str2[1];
			return cleaned;
		}
		return cleanMe;
	}
	
	private String cleanPort(String host2) {
		if(host2.contains(":")){
			String[] str2 = host2.split(":");
			String cleaned = str2[0];
			return cleaned;
		}
		return host2;
	}
}
