package icloud.services.account;

import icloud.services.account.objects.DSInfo;
import icloud.services.account.objects.Device;
import icloud.services.account.objects.QuotaStatus;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.StorageBlockInfo;
import icloud.services.account.objects.StorageUsageInfo;
import icloud.services.account.objects.Webservices.Webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class AccountConfig {

	// TODO: make setters create a new instance
	private boolean isExtendedLogin = false;
	private boolean pcsServiceIdentitiesIncluded = false;
	private boolean hasMinimumDeviceForPhotosWeb = false;
	private boolean isMemberOfFamily = false;
	private HashMap<String, Webservice> webservices = new HashMap<>();
	
	private StorageUsageInfo storageUsageInfo = new StorageUsageInfo();
	private QuotaStatus quotaStatus = new QuotaStatus();

	private TreeSet<StorageBlockInfo> storageUsageByMedia = new TreeSet<>();
	private boolean pcsEnabled;

	public boolean isExtendedLogin() {
		return isExtendedLogin;
	}

	protected void setExtendedLogin(boolean isExtendedLogin) {
		this.isExtendedLogin = isExtendedLogin;
	}
	
	protected void setPcsServiceIdentitiesIncluded(
			boolean pcsServiceIdentitiesIncluded) {
		this.pcsServiceIdentitiesIncluded = pcsServiceIdentitiesIncluded;
	}
	
	public boolean arePcsServiceIdentitiesIncluded() {
		return pcsServiceIdentitiesIncluded;
	}
	
	protected void setHasMinimumDeviceForPhotosWeb(
			boolean hasMinimumDeviceForPhotosWeb) {
		this.hasMinimumDeviceForPhotosWeb = hasMinimumDeviceForPhotosWeb;
	}
	
	public boolean hasMinimumDeviceForPhotosWeb() {
		return hasMinimumDeviceForPhotosWeb;
	}

	protected void setMemberOfFamily(boolean isMemberOfFamily) {
		this.isMemberOfFamily = isMemberOfFamily;
	}
	
	public boolean isMemberOfFamily() {
		return isMemberOfFamily;
	}
	
	public void setPcsEnabled(boolean pcsEnabled) {
		this.pcsEnabled = pcsEnabled;
	}
	
	public boolean isPcsEnabled(){
		return this.pcsEnabled;
	}
	
	public void addWebservice(Webservice webService) {
		this.webservices.put(webService.getName(), webService);	
	}
	
	
	
	
	
	
	
	
	public StorageUsageInfo getStorageUsageInfo() {
		return storageUsageInfo;
	}

	public QuotaStatus getQuotaStatus() {
		return quotaStatus;
	}
	
	public HashMap<String, Webservice> getWebservices() {
		return webservices;
	}

	public TreeSet<StorageBlockInfo> getStorageUsageByMedia() {
		return storageUsageByMedia;
	}

	@Deprecated
	protected void setWebservices(ArrayList<Webservice> arrayList) {
		for (Webservice webServ : arrayList) {
			this.webservices.put(webServ.getName(), webServ);
		}
	}

	protected void setStorageUsageInfo(StorageUsageInfo storageUsageInfo) {
		this.storageUsageInfo = storageUsageInfo;
	}

	protected void setQuotaStatus(QuotaStatus quotaStatus) {
		this.quotaStatus = quotaStatus;
	}

	protected void setStorageUsageByMedia(
			TreeSet<StorageBlockInfo> storageUsageByMedia) {
		this.storageUsageByMedia = storageUsageByMedia;
	}
}