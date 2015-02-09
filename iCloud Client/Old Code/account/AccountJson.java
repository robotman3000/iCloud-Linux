package icloud.config.account;

import java.util.List;

public class AccountJson {

	private boolean isExtendedLogin;
	private boolean pcsServiceIdentitiesIncluded;
	private boolean hasMinimumDeviceForPhotosWeb;
	private boolean pcsEnabled;

	private int version;

	private Webservices webservices;

	private DSInfo dsInfo;

	public class DSInfo {
		boolean primaryEmailVerified;
		String iCloudAppleIdAlias;
		String lastName;
		String appleIdAlias;
		String locale;
		boolean hasICloudQualifyingDevice;
		boolean isPaidDeveloper;
		String appleId;
		boolean gilligan_invited;
		boolean gilligan_enabled;
		String dsid;
		String primaryEmail;
		int statusCode;
		boolean brMigrated;
		String languageCode;
		String aDsID;
		boolean locked;
		String fullName;
		String firstName;
		List<String> appleIdAliases;
	}

	private List<String> appsOrder;

	private Apps apps;

	private RequestInfo requestInfo;

	public class RequestInfo {
		private String region;
		private String timeZone;
		private String country;

		public String getCountry() {
			return country;
		}

		public String getRegion() {
			return region;
		}

		public String getTimeZone() {
			return timeZone;
		}

	}

	public boolean isExtendedLogin() {
		return isExtendedLogin;
	}

	public boolean isPcsServiceIdentitiesIncluded() {
		return pcsServiceIdentitiesIncluded;
	}

	public boolean isHasMinimumDeviceForPhotosWeb() {
		return hasMinimumDeviceForPhotosWeb;
	}

	public boolean isPcsEnabled() {
		return pcsEnabled;
	}

	public int getVersion() {
		return version;
	}

	public DSInfo getDsInfo() {
		return dsInfo;
	}

	public List<String> getAppsOrder() {
		return appsOrder;
	}

	public Webservices getWebservices() {
		return webservices;
	}

	public Apps getApps() {
		return apps;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
}
