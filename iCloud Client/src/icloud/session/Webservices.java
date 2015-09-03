package icloud.session;

import java.util.ArrayList;

public class Webservices {

	public Webservice reminders = new Webservice();
	public Webservice mail = new Webservice();
	public Webservice drivews = new Webservice();
	public Webservice settings = new Webservice();
	public Webservice keyvalue = new Webservice();
	public Webservice push = new Webservice();
	public Webservice contacts = new Webservice();
	public Webservice findme = new Webservice();
	public Webservice photos = new Webservice();
	public Webservice ubiquity = new Webservice();
	public Webservice iwmb = new Webservice();
	public Webservice ckdatabasews = new Webservice();
	public Webservice docws = new Webservice();
	public Webservice account = new Webservice();
	public Webservice streams = new Webservice();
	public Webservice notes = new Webservice();
	public Webservice calendar = new Webservice();

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

		public String getName() {
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
		for (Webservice wServ : wrfe) {
			web.add(wServ);
		}
		return web;
	}

}
