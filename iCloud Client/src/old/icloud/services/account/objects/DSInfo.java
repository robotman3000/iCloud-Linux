package old.icloud.services.account.objects;

import com.google.gson.annotations.SerializedName;

public class DSInfo {

	private boolean primaryEmailVerified = false;
	private String iCloudAppleIdAlias = "";
	private String lastName = "";
	private String appleIdAlias = "";
	private String locale = "";
	private boolean hasICloudQualifyingDevice = false;
	private boolean isPaidDeveloper = false;
	private String appleId = "";
	@SerializedName("gilligan-invited") private boolean gilligan_invited = false;
	@SerializedName("gilligan-enabled") private boolean gilligan_enabled = false;
	private String dsid = "";
	private String primaryEmail = "";
	private int statusCode = -1;
	private boolean brMigrated = false;
	private String languageCode = "";
	private String aDsID = "";
	private boolean locked = false;
	private String fullName = "";
	private String firstName = "a first name";
	private String[] appleIdAliases = {};

	public boolean isPrimaryEmailVerified() {
		return primaryEmailVerified;
	}

	public String getiCloudAppleIdAlias() {
		return iCloudAppleIdAlias;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAppleIdAlias() {
		return appleIdAlias;
	}

	public String getLocale() {
		return locale;
	}

	public boolean isHasICloudQualifyingDevice() {
		return hasICloudQualifyingDevice;
	}

	public boolean isPaidDeveloper() {
		return isPaidDeveloper;
	}

	public String getAppleId() {
		return appleId;
	}

	public boolean isGilligan_invited() {
		return gilligan_invited;
	}

	public boolean isGilligan_enabled() {
		return gilligan_enabled;
	}

	public String getDsid() {
		return dsid;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean isBrMigrated() {
		return brMigrated;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public String getaDsID() {
		return aDsID;
	}

	public boolean isLocked() {
		return locked;
	}

	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String[] getAppleIdAliases() {
		return appleIdAliases;
	}
}
