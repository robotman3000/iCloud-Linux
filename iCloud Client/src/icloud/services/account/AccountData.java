package icloud.services.account;

import java.util.TreeSet;

import icloud.services.account.objects.DSInfo;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;

public class AccountData {
	
	private RequestInfo requestInfo = new RequestInfo();
	private DSInfo dsInfo = new DSInfo();
	private StorageUsageInfo storageTotals = new StorageUsageInfo();
	private TreeSet<Device> devices = new TreeSet<>();
	private TreeSet<StorageBlockInfo> storageBlocks = new TreeSet<>();
	private QuotaStatus quotaStatus = new QuotaStatus();
	
	@Deprecated
	public DSInfo getDsInfo() {
		return dsInfo;
	}
	
	public void setDsInfo(DSInfo dsInfo) {
		this.dsInfo = dsInfo;
	}
	
	public void addUserDevice(Device device) {
		this.devices.add(device);
	}
	// get list
	// get item
	public void addStorageBlock(StorageBlockInfo storeBlock) {
		this.storageBlocks.add(storeBlock);
	}

	public void setLocaleInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public void setStorageTotals(StorageUsageInfo storageUsageInfo) {
		this.storageTotals = storageUsageInfo;
	}

	public void setStorageQuotas(QuotaStatus quotaStatus) {
		this.quotaStatus = quotaStatus;
	}
}
