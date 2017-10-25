package processBuilder;

/**
 * Exception used when something wrong happens in Process processing.
 */
public class ProcessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProcessException(String message) {
		super(message);
	}
}
