package maths;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class that represents a question for the game.
 */
public class Equation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String representation;
	private int answer;

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
	
	public boolean hasSameQuestion(Equation equation) {
		if (equation.toString().equals(toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * For use in a table view
	 * @return
	 */
	public String getRepresentationView() {
		return toString();
	}
	
	/**
	 * For use in a table view
	 * @return
	 */
	public String getAnswerView() {
		return "" + answer();
	}
}
