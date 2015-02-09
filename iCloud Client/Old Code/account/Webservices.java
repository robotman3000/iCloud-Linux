package icloud.config.account;

class Webservices {
	
	private Servers reminders;
	private Servers mail;
	private Servers drivews;
	private Servers settings;
	private Servers keyvalue;
	private Servers push;
	private Servers contacts;
	private Servers findme;
	private Servers photos;
	private Servers ubiquity;
	private Servers iwmb;
	private Servers ckdatabasews;
	private Servers docws;
	private Servers account;
	private Servers streams;
	private Servers notes;
	private Servers calendar;

	public class Servers {
		private String status;
		private String url;
		private boolean pcsRequired;

		private ICloudEnv iCloudEnv;

		public class ICloudEnv {
			private String shortId;

			public String getShortId() {
				return shortId;
			}
		}

		public String getStatus() {
			return status;
		}

		public String getUrl() {
			return url;
		}

		public boolean isPcsRequired() {
			return pcsRequired;
		}

		public ICloudEnv getiCloudEnv() {
			return iCloudEnv;
		}

	}

	public Servers getReminders() {
		return reminders;
	}

	public Servers getMail() {
		return mail;
	}

	public Servers getDrivews() {
		return drivews;
	}

	public Servers getSettings() {
		return settings;
	}

	public Servers getKeyvalue() {
		return keyvalue;
	}

	public Servers getPush() {
		return push;
	}

	public Servers getContacts() {
		return contacts;
	}

	public Servers getFindme() {
		return findme;
	}

	public Servers getPhotos() {
		return photos;
	}

	public Servers getUbiquity() {
		return ubiquity;
	}

	public Servers getIwmb() {
		return iwmb;
	}

	public Servers getCkdatabasews() {
		return ckdatabasews;
	}

	public Servers getDocws() {
		return docws;
	}

	public Servers getAccount() {
		return account;
	}

	public Servers getStreams() {
		return streams;
	}

	public Servers getNotes() {
		return notes;
	}

	public Servers getCalendar() {
		return calendar;
	}
}
