package common.http;

public class HttpHeader {

	private String headerName;
	private String headerValue;

	public HttpHeader(String headerName, String headerValue){
		this.setHeaderName(headerName);
		this.setHeaderValue(headerValue);
	}

	public String getHeaderName() {
		return headerName;
	}

	private void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	private void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	
	
}
