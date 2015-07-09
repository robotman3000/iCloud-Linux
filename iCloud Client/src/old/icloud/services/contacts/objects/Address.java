package old.icloud.services.contacts.objects;

public class Address {

	private AddressValues field = new AddressValues();
	private String label;

	public String getPostalCode() {
		return field.postalCode;
	}

	public String getStreet() {
		return field.street;
	}

	public String getCountryCode() {
		return field.countryCode;
	}

	public String getState() {
		return field.state;
	}

	public String getCity() {
		return field.city;
	}

	public String getCountry() {
		return field.country;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPostalCode(String postalCode) {
		this.field.postalCode = postalCode;
	}

	public void setStreet(String street) {
		this.field.street = street;
	}

	public void setCountryCode(String countryCode) {
		this.field.countryCode = countryCode;
	}

	public void setState(String state) {
		this.field.state = state;
	}

	public void setCity(String city) {
		this.field.city = city;
	}

	public void setCountry(String country) {
		this.field.country = country;
	}

	private class AddressValues {
		private String postalCode = "";
		private String street;
		private String countryCode;
		private String state;
		private String city;
		private String country;
	}
}
