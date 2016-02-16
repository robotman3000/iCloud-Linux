package icloud;

public class CloudConfStoreKeys {
	
	public enum AppNames {
		NOTES, CONTACTS, REMINDERS, ACCOUNT
	}
	
	public enum ServerRequestType {
		STARTUP, CREATE, UPDATE, DELETE, CHANGESET, LOGIN, VALIDATE, LOGOUT
	}
	
	public enum RequestMode {
		POST, GET
	}
	
	public enum ServerRequestKeys {
		URL, REQUEST_MODE, HEADERS_LIST, COOKIES_LIST, QUERY_STRING_LIST
	}
	
	public enum ConfStoreKeys {
        hasMinimumDeviceForPhotosWeb,
        isExtendedLogin,
        requestInfo_region,
        requestInfo_timeZone,
        requestInfo_country,
        dsinfo_lastName,
        dsinfo_firstName,
        dsinfo_fullName,
        dsinfo_appleidAlias,
        dsinfo_locale,
        dsinfo_appleId,
        dsinfo_languageCode,
	}
	
	public enum QueryStrings {
        //TODO: Add keys for all the webservice url's
		buildNumber,
		clientBuildNumber,
		dsid,
		syncToken
	}
	
	public enum CookieNames {
		
	}
	
	public enum HeaderNames {
		
	}
}