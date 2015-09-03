package icloud.session;

public class Apps {
	private App mail = new App();
	private App reminders = new App();
	private App iclouddrive = new App();
	private App numbers = new App();
	private App settings = new App();
	private App pages = new App();
	private App notes = new App();
	private App find = new App();
	private App contacts = new App();
	private App calendar = new App();
	private App keynote = new App();

	public class App {
		private boolean isQualifiedForBeta = false;

		public boolean isQualifiedForBeta() {
			return isQualifiedForBeta;
		}
	}

	public App[] getApps() {
		App[] appslist = { mail, reminders, iclouddrive, numbers, settings,
				pages, notes, find, contacts, calendar, keynote };
		return appslist;
	}
}
