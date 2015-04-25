package common.http;


public class URLConfig {

	public static final String default_protocol = "https://";
	public static final int default_port = 443;
	public static final HttpHeader default_header_origin = new HttpHeader("Origin", "https://www.icloud.com");
	public static final HttpHeader default_header_userAgent = new HttpHeader("User-Agent", "Mozilla/5.0 Java_iCloud/1.0");

	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";

	public static final String query_arg_clientBN = "clientBuildNumber";
	public static final String query_arg_clientId = "clientId";
	public static final String query_arg_dsid = "dsid";
	public static final String query_arg_proxyDest = "proxyDest";
	public static final String query_arg_token = "token";
	public static final String query_arg_syncToken = "syncToken";
	public static final String query_arg_validateToken = "validateToken";
	public static final String query_arg_attachmentId = "attachmentId";
	public static final String query_arg_clientVersion = "clientVersion";
	public static final String query_arg_locale = "locale";
	public static final String query_arg_order = "order";
	public static final String query_arg_prefToken = "prefToken";
	public static final String query_arg_method = "method";
}
