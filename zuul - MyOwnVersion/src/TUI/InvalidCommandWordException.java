package TUI;

public class InvalidCommandWordException extends Exception {

	public InvalidCommandWordException(String message) {
		super(message);
	}
	
	public InvalidCommandWordException(String message, Throwable cause) {
		super(message, cause);
	}
}
