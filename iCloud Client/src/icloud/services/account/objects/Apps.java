package icloud.services.account.objects;

public class Apps {
	private final App mail = new App();
	private final App reminders = new App();
	private final App iclouddrive = new App();
	private final App numbers = new App();
	private final App settings = new App();
	private final App pages = new App();
	private final App notes = new App();
	private final App find = new App();
	private final App contacts = new App();
	private final App calendar = new App();
	private final App keynote = new App();
	
	public class App{
		private final boolean isQualifiedForBeta = false;

		public boolean isQualifiedForBeta() {
			return isQualifiedForBeta;
		}
	}
	
	public App[] getApps(){
		App[] appslist = {mail, reminders, iclouddrive, numbers, settings, pages, notes, find, contacts, calendar, keynote};
		return appslist;
	}
}
