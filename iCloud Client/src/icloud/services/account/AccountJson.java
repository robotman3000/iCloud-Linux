package icloud.services.account;

import icloud.services.account.objects.Apps;
import icloud.services.account.objects.DSInfo;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;
import icloud.services.account.objects.Webservices;

import java.util.TreeSet;

public class AccountJson {
	private boolean isExtendedLogin;
	private boolean pcsServiceIdentitiesIncluded;
	private boolean hasMinimumDeviceForPhotosWeb;
	private boolean pcsEnabled;
	private int version;
	private boolean isMemberOfFamily;
	private int status;
	private String status_message;
	private String error;
	private boolean success;
	
	private TreeSet<String> appsOrder;
	private TreeSet<Device> devices;
	private TreeSet<StorageBlockInfo> storageUsageByMedia;

	//TODO: Add below object to json file in AccountJson
	/*,"configBag":{"urls":{"accountCreateUI":"string","getICloudTerms":"string","accountCreate":"string","downloadICloudTerms":"string","accountRepairUI":"string","repairDone":"string"},"accountCreateEnabled":"true"}}*/
	
	public Webservices webservices = new Webservices();
	public DSInfo dsInfo = new DSInfo();
	public Apps apps = new Apps();
	public RequestInfo requestInfo = new RequestInfo();
	public StorageUsageInfo storageUsageInfo = new StorageUsageInfo();
	public QuotaStatus quotaStatus = new QuotaStatus();

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

	public boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}

	public int getVersion() {
		return version;
	}

	public boolean isMemberOfFamily() {
		return isMemberOfFamily;
	}

	public int getStatus() {
		return status;
	}

	public String getStatus_message() {
		return status_message;
	}

	public TreeSet<String> getAppsOrder() {
		return appsOrder;
	}

	public TreeSet<Device> getDevices() {
		return devices;
	}

	public TreeSet<StorageBlockInfo> getStorageUsageByMedia() {
		return storageUsageByMedia;
	}
}
