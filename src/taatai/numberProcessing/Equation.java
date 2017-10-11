package taatai.numberProcessing;

/**
 * Class that represents a question for the game.
 */
public class Equation {
	String representation;
	int answer;

	/**
	 * @param representation
	 *            This is the string that will be shown on the GUI
	 * @param answer
	 *            If this answer is received the answer will be considered correct.
	 */
	public Equation(String representation, int answer) {
		this.representation = representation;
		this.answer = answer;
	}

	/**
	 * String representation of equation used by game in GUI.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return representation;
	}

	/**
	 * Returns the correct answer to the equation.
	 * 
	 * @return
	 */
	public int answer() {
		return answer;
	}
}
