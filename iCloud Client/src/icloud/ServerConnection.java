package icloud;

import java.net.URI;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;

public class ServerConnection {

	private String responseBody;
	private Header[] headers;
	private String responseCode;
	private Header[] Cookies;

	public ServerConnection() {
		clearVars();
	}

	private void clearVars() {
		setResponseBody("");
		HttpUriRequest httpmethod = RequestBuilder.create("GET").build();
		httpmethod.addHeader("Key", "value");
		Header[] headerslist = httpmethod.getAllHeaders();
		setHeaders(headerslist);

	}

	public Header[] getHeaders() {
		return headers;
	}

	private void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public String getResponseBody() {
		return responseBody;
	}

	private void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getResponseCode() {
		return responseCode;
	}

	private void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Header[] getCookies() {
		return Cookies;
	}

	private void setCookies(Header[] cookies) {
		Header[] fixedCookies = cookies;
		for (int index = 0; index < cookies.length; index++) {
			Header test = cookies[index];
			fixedCookies[index] = fixCookie(test);
		}
		Cookies = fixedCookies;
	}

	private Header fixCookie(Header cookieToFix) {
		HttpUriRequest cookieMaker = RequestBuilder.create("GET").build();
		cookieMaker.addHeader("Cookie", cookieToFix.getValue());;
		Header[] Cookies = cookieMaker.getAllHeaders();
		return Cookies[0];
	}

	/*
	 * public void connect(String requestMethod, String protocol, String server,
	 * int port, StringEntity requestBody, Header[] headers) throws Exception {
	 * 
	 * // For avoiding conflicts clearVars();
	 * 
	 * CloseableHttpClient httpclient = HttpClients.createDefault(); URI uri =
	 * new
	 * URIBuilder().setScheme(protocol).setHost(server).setPort(port).build();
	 * 
	 * HttpUriRequest httpmethod =
	 * RequestBuilder.create(requestMethod).setUri(uri
	 * ).setEntity(requestBody).build();
	 * 
	 * for (int index = 0; index < headers.length; index++) {
	 * httpmethod.addHeader(headers[index]); }
	 * 
	 * ResponseHandler<String> responseHandler = new BasicResponseHandler();
	 * 
	 * System.out.println("\n" +
	 * "--------------------------------------------------------------------------------"
	 * ); System.out.println("Pending Connection Details:");
	 * System.out.println(httpmethod.toString()); for (int index = 0; index <
	 * headers.length; index++) { System.out.println(headers[index]); }
	 * System.out.println(
	 * "--------------------------------------------------------------------------------"
	 * + "\n"); CloseableHttpResponse response = httpclient.execute(httpmethod);
	 * setResponseBody(responseHandler.handleResponse(response));
	 * 
	 * this.setHeaders(response.getAllHeaders());
	 * 
	 * }
	 * 
	 * /**
	 * 
	 * @param authKey
	 * 
	 * @param protocall
	 * 
	 * @param server
	 * 
	 * @param port
	 * 
	 * @param requestBody
	 * 
	 * @param headers
	 * 
	 * @param requestMethod
	 * 
	 * @param queryStrings
	 * 
	 * @throws Exception
	 */
	public void connect(String requestMethod, String protocol, String server, int port, String path, String[] queryStringNames, String[] queryStringValues, StringEntity requestBody, Header[] headers) throws Exception {

		// For avoiding conflicts
		clearVars();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uribuild = new URIBuilder().setScheme(protocol).setHost(server).setPort(port).setPath(path);

		if (queryStringValues.length == queryStringNames.length) {
			for (int index = 0; index < queryStringValues.length; index++) {
				uribuild.setParameter(queryStringNames[index], queryStringValues[index]);
			}
		}
		URI uri = uribuild.build();

		HttpUriRequest httpmethod = RequestBuilder.create(requestMethod).setUri(uri).setEntity(requestBody).build();
		for (int index = 0; index < headers.length; index++) {
			httpmethod.addHeader(headers[index]);
		}

		for (int index = 0; index < headers.length; index++) {
			httpmethod.addHeader(headers[index]);
		}



		this.setResponseCode(response.getStatusLine().toString());
		this.setHeaders(response.getAllHeaders());
		this.setCookies(response.getHeaders("Set-Cookie"));
	}

}
