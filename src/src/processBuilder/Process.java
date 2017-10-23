package processBuilder;

/**
 * This class is made to represent a linux command.
 */
public class Process {

	private final String CMD;
	private final String NAME;

	/**
	 * @param CMD
	 *            This is the command as a string
	 * @param NAME
	 *            This is the chosen name that will refer to the command
	 */
	public Process(String CMD, String NAME) {
		this.CMD = CMD;
		this.NAME = NAME;
	}

	/**
	 * @return Returns the command line as string.
	 */
	public String getCMD() {
		return CMD;
	}

	/**
	 * Returns the name of the command.
	 * 
	 * @return
	 */
	public String getNAME() {
		return NAME;
	}
}
