package TUI;

public class CommandInstantiationException extends RuntimeException {

	public CommandInstantiationException(String message) {
		super(message);
	}
	
	public CommandInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}
}
