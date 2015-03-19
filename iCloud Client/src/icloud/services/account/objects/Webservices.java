package icloud.services.account.objects;

import java.util.ArrayList;

public class Webservices {

	private final Webservice reminders = new Webservice();
	private final Webservice mail = new Webservice();
	private final Webservice drivews = new Webservice();
	private final Webservice settings = new Webservice();
	private final Webservice keyvalue = new Webservice();
	private final Webservice push = new Webservice();
	private final Webservice contacts = new Webservice();
	private final Webservice findme = new Webservice();
	private final Webservice photos = new Webservice();
	private final Webservice ubiquity = new Webservice();
	private final Webservice iwmb = new Webservice();
	private final Webservice ckdatabasews = new Webservice();
	private final Webservice docws = new Webservice();
	private final Webservice account = new Webservice();
	private final Webservice streams = new Webservice();
	private final Webservice notes = new Webservice();
	private final Webservice calendar = new Webservice();

	public class Webservice {
		private transient String name = "";
		private String status;
		private String url;
		private boolean pcsRequired;
		private iCloudEnv iCloudEnv = new iCloudEnv();

		public String getStatus() {
			return status;
		}

		public String getUrl() {
			return url;
		}
		
		public String getName(){
			return name;
		}

		protected Webservice setName(String name) {
			this.name = name;
			return this;
		}

		public boolean isPcsRequired() {
			return pcsRequired;
		}

		public iCloudEnv getiCloudEnv() {
			return iCloudEnv;
		}

		public class iCloudEnv {
			private String shortId;

			public String getShortId() {
				return shortId;
			}
		}
	}

	public ArrayList<Webservice> getWebservices() {
		ArrayList<Webservice> web = new ArrayList<Webservice>();

		Webservice[] wrfe = { reminders.setName("reminders"),
				mail.setName("mail"), drivews.setName("drivews"),
				settings.setName("settings"), keyvalue.setName("keyvalue"),
				push.setName("push"), contacts.setName("contacts"),
				findme.setName("findme"), photos.setName("photos"),
				ubiquity.setName("ubiquity"), iwmb.setName("iwmb"),
				ckdatabasews.setName("ckdatabasews"), docws.setName("docws"),
				account.setName("account"), streams.setName("streams"),
				notes.setName("notes"), calendar.setName("calendar") };
		for (Webservice wServ : wrfe){
			web.add(wServ);
		}
		return web;
	}

}
