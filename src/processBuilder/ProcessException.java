package processBuilder;

/**
 * Exception used when something wrong happens in Process processing.
 */
public class ProcessException extends RuntimeException {
	public ProcessException(String message) {
		super(message);
	}
}
