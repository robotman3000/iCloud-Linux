package icloud;

public abstract class Session<T extends ServerRequest> {

	abstract void exceuteRequest(T theRequestCommand);
}
