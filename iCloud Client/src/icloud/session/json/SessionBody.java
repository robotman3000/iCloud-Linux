package icloud.session.json;

import icloud.session.Apps;
import icloud.session.DSInfo;
import icloud.session.Device;
import icloud.session.QuotaStatus;
import icloud.session.RequestInfo;
import icloud.session.StorageBlockInfo;
import icloud.session.Webservices;

import java.util.HashSet;

import com.google.gson.annotations.SerializedName;

public class SessionBody {

	private Boolean isExtendedLogin;
	private Boolean pcsServiceIdentitiesIncluded;
	private Boolean hasMinimumDeviceForPhotosWeb;
	private Boolean pcsEnabled;
	private int version;
	private Boolean isMemberOfFamily;
	private int status;

	@SerializedName("status-message")
	private String status_message;
	private String error;
	private Boolean success;

	private HashSet<String> appsOrder;
	private HashSet<Device> devices;
	private HashSet<StorageBlockInfo> storageUsageByMedia;

	// TODO: Add below object to json file in SessionBody
	/*
	 * ,"configBag":{"urls":{"accountCreateUI":"string","getICloudTerms":"string"
	 * ,
	 * "accountCreate":"string","downloadICloudTerms":"string","accountRepairUI"
	 * :"string","repairDone":"string"},"accountCreateEnabled":"true"}}
	 */

	public Webservices webservices;
	public DSInfo dsInfo;
	public Apps apps;
	public RequestInfo requestInfo;
	public StorageUsageInfo storageUsageInfo;
	public QuotaStatus quotaStatus;

	public Boolean isExtendedLogin() {
		return isExtendedLogin;
	}

	public Boolean isPcsServiceIdentitiesIncluded() {
		return pcsServiceIdentitiesIncluded;
	}

	public Boolean hasMinimumDeviceForPhotosWeb() {
		return hasMinimumDeviceForPhotosWeb;
	}

	public Boolean isPcsEnabled() {
		return pcsEnabled;
	}

	public Boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}

	public int getVersion() {
		return version;
	}

	public Boolean isMemberOfFamily() {
		return isMemberOfFamily;
	}

	public int getStatus() {
		return status;
	}

	public String getStatus_message() {
		return status_message;
	}

	public HashSet<String> getAppsOrder() {
		return appsOrder;
	}

	public HashSet<Device> getDevices() {
		return devices;
	}

	public HashSet<StorageBlockInfo> getStorageUsageByMedia() {
		return storageUsageByMedia;
	}
}
