package common.http;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import common.KeyValuePair;

public class URLBuilder {
	// TODO: Add Javadoc; auto insert default values for missing args
	private String protocol = "";
	private int port = -1;
	private String host = "";
	private String file = "";
	private/* DWN:Hash */Map<String, String> queryStringMap = new HashMap<String, String>();

	// TODO: Make all applicable methods use the systemlogger
	// TODO: add has methods and remove methods and all adders and setters
	public URLBuilder(String protocol, String host, int port, String file, /* DWN:Hash */Map<String, String> keyMap) {
		this(protocol, host, port, file);
		this.setQueryStringMap(keyMap);
	}

	public URLBuilder(String protocol, String host, int port, String file, KeyValuePair... keyValues) {
		this(protocol, host, port, file);

		/* DWN:Hash */Map<String, String> list = new HashMap<String, String>();
		if (keyValues != null && keyValues.length > 0) {
			for (KeyValuePair kv : keyValues) {
				if (kv.getKey() != null && kv.getValue() != null && kv.getKey().length() > 0 && kv.getValue().length() > 0) {
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

	// DWN: Is this ever called by anything other than the above constructor?
	public URLBuilder(String host) {
		setHost(host);
	}

	public URLBuilder(URL fromUrl) {
		URI theUri;
		try {
			theUri = fromUrl.toURI();
			setProtocol("https://"); // TODO: fix this!!
			setHost(theUri.getHost());
			//setPath(theUri.getPath()); //TODO: fix this!!!!!!!
			setPort(theUri.getPort());

			if(theUri.getQuery() != null && !theUri.getQuery().isEmpty()){
				String[] list = theUri.getQuery().split("&");
				for (String str : list) {
					String[] abc = str.trim().replaceFirst("=", "\n").split("\n");
					if (abc.length == 2) {
						addQueryString(abc[0], abc[1]);
					} else {
						System.err.println("Skipped adding query string: " + str);
					}
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

		StringBuilder queryStringB = new StringBuilder();
		if (queryStringMap.size() > 0) {
			Iterator<String> it = queryStringMap.keySet().iterator();
			while (it.hasNext()) {
				String var = it.next();
				String var2 = queryStringMap.get(var);
				String var3 = var + "=" + var2;
				queryStringB.append(var3 + "&");
			}
			queryStringB.deleteCharAt(queryStringB.length() - 1);
		}
		URL url;
		StringBuilder str = new StringBuilder();

		if (protocol != null && !protocol.isEmpty()) {
			str.append(protocol + "://");
		} else {
			str.append("http://");
		}

		if (host != null && !host.isEmpty()) {
			str.append(host);
		} else {
			throw new MalformedURLException("The host can't be null");
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
		// DWN: if (queryStringMap.containsKey(key)) {
		queryStringMap.remove(key);
		// DWN: }
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
		// // Nothing needed here as int's can't be null
		// if (port > 0) {
		// this.port = port;
		// } else {
		// // DWN: "port" is a local variable. "this.port" is a member variable. I think you mean to set the member here?
		// port = -1; // The "invalid" port used later in buildUrl()
		// }
		this.port = (port > 0 ? port : -1); // DWN: More concise way

		return this;
	}

	public URLBuilder setProtocol(String protocol) {
		//try {
			String newProtocol = protocol.replaceAll("://", "");
			//@SuppressWarnings("unused")
			/*
			 * This will throw a MalformedURLException if something is wrong so we can safely assume that the protocol is at fault and not
			 * the hard coded values
			 */
			// DWN: Good idea, to use URL class to validate your data this way.
			//URL url = new URL(newProtocol, "example.com", "/index.html"); //TODO: This has problems
			this.protocol = newProtocol;
/*		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid protocol provided");
		}*/
		return this;
	}

	public URLBuilder setQueryStringMap(/* DWN: HashMap<String, String> */Map<String, String> queryStrings) {
		// Make a new map as to stop uncontroled modifications
		// DWN
		this.queryStringMap.putAll(queryStrings);
		// for (String key : queryStrings.keySet()) {
		// for (String value : queryStrings.values()) {
		// this.queryStringMap.put(key, value);
		// }
		// }
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
		// DWN: Return an unmodifiable view of the Map
		return Collections.unmodifiableMap(queryStringMap);
		// // Make a new map as to stop uncontroled modifications
		// HashMap<String, String> listor = new HashMap<String, String>();
		// for (String key : queryStringMap.keySet()) {
		// for (String value : queryStringMap.values()) {
		// listor.put(key, value);
		// }
		// }
		// return listor;
	}

	// DWN public String getUrl() {
	public String getHost() { // DWN: Is this what you want, or did you mean to call it "getUrl" ?
		return host;
	}

	private String cleanSlashes(String cleanMe) {
		String layer1 = cleanMe.trim();
		int length = layer1.length();
		if (layer1.charAt(length - 1) == '/' /* DWN "/".charAt(0) */|| layer1.charAt(length - 1) == '\\' /* DWN "\\".charAt(0) */) {
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

	private String cleanPort(String cleanMe) {
		if (cleanMe.contains(":")) {
			String[] str2 = cleanMe.split(":");
			String cleaned = str2[0];
			return cleaned;
		}
		return cleanMe;
	}
}
