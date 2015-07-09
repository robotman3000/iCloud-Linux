package old.icloud.services.contacts.objects;

public class IMKeyValuePair {
	private IMValues field = new IMValues();
	private String label;

	public String getUserName() {
		return field.userName;
	}

	public String getIMService() {
		return field.IMService;
	}

	public void setUserName(String userName) {
		this.field.userName = userName;
	}

	public void setIMService(String iMService) {
		field.IMService = iMService;
	}

	public IMValues getField() {
		return field;
	}

	public String getLabel() {
		return label;
	}

	public void setField(IMValues field) {
		this.field = field;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private class IMValues {
		private String userName = "";
		private String IMService = "";
	}
}
