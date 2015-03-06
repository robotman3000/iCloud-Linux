package icloud.services.account.objects;

public class Webservices {

	public final Webservice reminders = new Webservice();
	public final Webservice mail = new Webservice();
	public final Webservice drivews = new Webservice();
	public final Webservice settings = new Webservice();
	public final Webservice keyvalue = new Webservice();
	public final Webservice push = new Webservice();
	public final Webservice contacts = new Webservice();
	public final Webservice findme = new Webservice();
	public final Webservice photos = new Webservice();
	public final Webservice ubiquity = new Webservice();
	public final Webservice iwmb = new Webservice();
	public final Webservice ckdatabasews = new Webservice();
	public final Webservice docws = new Webservice();
	public final Webservice account = new Webservice();
	public final Webservice streams = new Webservice();
	public final Webservice notes = new Webservice();
	public final Webservice calendar = new Webservice();

	public class Webservice {
		public final String status = "";
		public final String url = "";
		public final boolean pcsRequired = false;
		public final iCloudEnv iCloudEnv = new iCloudEnv();
	}

	public class iCloudEnv {
		public final String shortId = "";
	}

	public Webservice[] getWebservices() {
		Webservice[] web = { reminders, mail, drivews, settings, keyvalue,
				push, contacts, findme, photos, ubiquity, iwmb, ckdatabasews,
				docws, account, streams, notes, calendar };
		return web;
	}

}
