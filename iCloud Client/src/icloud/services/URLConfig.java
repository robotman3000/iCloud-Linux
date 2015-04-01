package icloud.services;

import java.net.MalformedURLException;
import java.net.URL;

import common.CommonLogic;

public class URLConfig {

	public static final String account_url_default_host = "setup.icloud.com";
	public static final String account_url_login = "/setup/ws/1/login?";
	public static final String account_url_logout = "/setup/ws/1/logout?";
	public static final String account_url_validate = "/setup/ws/1/validate?";
	public static final String account_url_getDevices = "/setup/web/device/getDevices?";
	public static final String account_url_getFamilyDetails = "/setup/web/family/getFamilyDetails?";
	public static final String account_url_getStorageUsageInfo = "/setup/ws/1/storageUsageInfo?";

	public static final String notes_url_default_host = "notesws.icloud.com";
	public static final String notes_url_startup = "/no/startup?";
	public static final String notes_url_createnotes = "/no/createNotes?";
	public static final String notes_url_updatenotes = "/no/updateNotes?";
	public static final String notes_url_deletenotes = "/no/deleteNotes?";
	public static final String notes_url_retriveAttachment = "/no/retrieveAttachment?";
	public static final String notes_url_retrieveNotes = "/no/retrieveNotes?";
	public static final String notes_url_changeset = "/no/changeset?";

	public static final String contacts_url_default_host = "contactsws.icloud.com";
	public static final String contacts_url_getMeCard = "/co/mecard/?";
	public static final String contacts_url_setMeCard = "/co/mecardid/card/?";
	public static final String contacts_url_startup = " /co/startup?";
	public static final String contacts_url_changeset = "/co/changeset?";
	public static final String contacts_url_groups = "/co/groups/card/?";
	public static final String contacts_url_contacts = "/co/contats/card/?";

	public static final String default_protocol = "https://";
	public static final int default_port = 443;
	public static final String default_header_origin = "https://www.icloud.com";
	public static final String default_header_userAgent = "Mozilla/5.0 Java_iCloud/1.0";

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

	private URL accountURL;
	private URL notesURL;
	private URL contactsURL;

	// TODO: make urls be defined in an enum to remove copy pasted methods
	public URL getAccountsURL() {
		if (CommonLogic.isNull(accountURL)) {
			try {
				updateAccountsURL(default_protocol, account_url_default_host,
						default_port);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block | hard code backup values here
				e.printStackTrace();
			}
		}
		return accountURL;
	}

	public URL getNotesURL() {
		if (CommonLogic.isNull(notesURL)) {
			try {
				updateNotesURL(default_protocol, notes_url_default_host,
						default_port);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block | hard code backup values here
				e.printStackTrace();
			}
		}
		return notesURL;
	}

	public URL getContactsURL() {
		if (CommonLogic.isNull(contactsURL)) {
			try {
				updateContactsURL(default_protocol, contacts_url_default_host,
						default_port);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block | hard code backup values here
				e.printStackTrace();
			}
		}
		return contactsURL;
	}

	
	public void updateAccountsURL(String protocol, String url, int port) throws MalformedURLException {
		accountURL = new URL(protocol, url, port, "");
	}

	public void updateNotesURL(String protocol, String url, int port) throws MalformedURLException{
		notesURL = new URL(protocol, url, port, "");		
	}

	public void updateContactsURL(String protocol, String url, int port) throws MalformedURLException{
		contactsURL = new URL(protocol, url, port, "");
	}

	
	public void updateAccountsURL(String url, int port) throws MalformedURLException {
		accountURL = new URL(default_protocol, url, port, "");
	}

	public void updateNotesURL(String url, int port) throws MalformedURLException {
		notesURL = new URL(default_protocol, url, port, "");
	}

	public void updateContactsURL(String url, int port) throws MalformedURLException {
		contactsURL = new URL(default_protocol, url, port, "");
	}

	
	public void updateAccountsURL(String url) throws MalformedURLException {
		accountURL = new URL(default_protocol, url, default_port, "");
	}

	public void updateNotesURL(String url) throws MalformedURLException {
		notesURL = new URL(default_protocol, url, default_port, "");
	}

	public void updateContactsURL(String url) throws MalformedURLException {
		contactsURL = new URL(default_protocol, url, default_port, "");
	}

}
