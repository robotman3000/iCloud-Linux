package icloud.config.account;

class Apps {
	private App mail;
	private App reminders;
	private App iclouddrive;
	private App numbers;
	private App settings;
	private App pages;
	private App notes;
	private App find;
	private App contacts;
	private App calendar;
	private App keynote;

	public class App {
		private boolean isQualifiedForBeta;

		public boolean isQualifiedForBeta() {
			return isQualifiedForBeta;
		}
	}

	public App getKeynote() {
		return keynote;
	}

	public App getCalendar() {
		return calendar;
	}

	public App getMail() {
		return mail;
	}

	public App getReminders() {
		return reminders;
	}

	public App getIclouddrive() {
		return iclouddrive;
	}

	public App getNumbers() {
		return numbers;
	}

	public App getSettings() {
		return settings;
	}

	public App getPages() {
		return pages;
	}

	public App getNotes() {
		return notes;
	}
	
	public App getFind() {
		return find;
	}

	public App getContacts() {
		return contacts;
	}
}
