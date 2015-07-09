package icloud;

public class CloudException extends Exception {

	public CloudException(String string) {
		super(string);
	}

	public CloudException(String message, Throwable e) {
		super(message, e);
	}

}
