package icloud.services.account;

import icloud.services.account.objects.Apps;
import icloud.services.account.objects.DSInfo;
import icloud.services.account.objects.RequestInfo;
import icloud.services.account.objects.Webservices;

import java.util.ArrayList;

public class AccountJson {
		public final boolean isExtendedLogin = false;
		public final boolean pcsServiceIdentitiesIncluded = false;
		public final boolean hasMinimumDeviceForPhotosWeb = false;
		public final boolean pcsEnabled = false;
		public final int version = -1;
		public final ArrayList<String> appsOrder = new ArrayList<>();
		
		public final Webservices webservices = new Webservices();
		public final DSInfo dsInfo = new DSInfo();
		public final Apps apps = new Apps();
		public final RequestInfo requestInfo = new RequestInfo();
}
