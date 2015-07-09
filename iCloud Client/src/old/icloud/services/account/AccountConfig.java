package old.icloud.services.account;

import java.util.HashMap;

import old.icloud.services.BaseConfig;
import old.icloud.services.account.objects.Webservices.Webservice;

public class AccountConfig extends BaseConfig {

	//TODO: Add safety checks to the getters and setters. ie don't return null instead throw an exeception
	private boolean isExtendedLogin = false;
	private boolean pcsServiceIdentitiesIncluded = false;
	private boolean hasMinimumDeviceForPhotosWeb = false;
	private boolean isMemberOfFamily = false;
	private boolean pcsEnabled;
	private HashMap<String, Webservice> webservices = new HashMap<String, Webservice>();

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
	
	public HashMap<String, Webservice> getWebservices(){
		return webservices;
	}
	
}