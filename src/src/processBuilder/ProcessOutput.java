package processBuilder;

/**
 * Class made to abstract the output of a command line process in linux
 */
public class ProcessOutput {
	private String stdout, stderr;

	/**
	 * Returns stdout as String
	 * 
	 * @return
	 */
	public String getStdout() {
		return stdout;
	}

	/**
	 * Returns stderr as string.
	 * 
	 * @return
	 */
	public String getStderr() {
		return stderr;
	}

	/**
	 * @param stdout
	 *            This is the stdout of the process as a string.
	 * @param stderr
	 *            This is the stderr of the process as a string.
	 */
	public ProcessOutput(String stdout, String stderr) {
		this.stderr = stderr;
		this.stdout = stdout;
	}

}
