package icloud.calendars;

import icloud.BaseManager;
import icloud.UserSessionInstance;

public class CalendarManager extends BaseManager {

	private String calendarURL;
	
	public CalendarManager(UserSessionInstance login){
		setCalendarURL(login.getUserID());
		
	}

	public String getCalendarURL() {
		return calendarURL;
	}

	public void setCalendarURL(String calendarURL) {
		this.calendarURL = calendarURL;
	}
}
