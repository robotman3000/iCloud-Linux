package icloud.services.account.objects;

public class Apps {
	public final App mail = new App();
	public final App reminders = new App();
	public final App iclouddrive = new App();
	public final App numbers = new App();
	public final App settings = new App();
	public final App pages = new App();
	public final App notes = new App();
	public final App find = new App();
	public final App contacts = new App();
	public final App calendar = new App();
	public final App keynote = new App();
	
	public class App{
		public final boolean isQualifiedForBeta = false;
	}
	
	public App[] getApps(){
		App[] appslist = {mail, reminders, iclouddrive, numbers, settings, pages, notes, find, contacts, calendar, keynote};
		return appslist;
	}
}
