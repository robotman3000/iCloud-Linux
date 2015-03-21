package icloud.services.account;

import java.util.HashMap;

import icloud.services.account.objects.DSInfo;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;

public class AccountData {

	//TODO: Add safety checks to the getters and setters. ie don't return null instead throw an exeception
	private RequestInfo requestInfo = new RequestInfo();
	private DSInfo dsInfo = new DSInfo();
	private StorageUsageInfo storageTotals = new StorageUsageInfo();
	private HashMap<String, Device> devices = new HashMap<>();
	private HashMap<String, StorageBlockInfo> storageBlocks = new HashMap<>();
	private QuotaStatus quotaStatus = new QuotaStatus();

	public DSInfo getDsInfo() {
		return dsInfo;
	}

	public void setDsInfo(DSInfo dsInfo) {
		this.dsInfo = dsInfo;
	}

	public void addUserDevice(String key, Device device) {
		this.devices.put(key, device);
	}

	public void addStorageBlock(String key, StorageBlockInfo storeBlock) {
		this.storageBlocks.put(key, storeBlock);
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public StorageUsageInfo getStorageTotals() {
		return storageTotals;
	}

	public HashMap<String, Device> getDevices() {
		return devices;
	}

	public HashMap<String, StorageBlockInfo> getStorageBlocks() {
		return storageBlocks;
	}

	public QuotaStatus getQuotaStatus() {
		return quotaStatus;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public void setStorageTotals(StorageUsageInfo storageTotals) {
		this.storageTotals = storageTotals;
	}

	public void addDevice(String key, Device value) {
		this.devices.put(key, value);
	}

	public void setStorageBlocks(String key, StorageBlockInfo value) {
		this.storageBlocks.put(key, value);
	}

	public void setQuotaStatus(QuotaStatus quotaStatus) {
		this.quotaStatus = quotaStatus;
	}
}
