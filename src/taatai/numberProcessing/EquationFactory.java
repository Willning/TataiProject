package taatai.numberProcessing;

public interface EquationFactory {

	/**
	 * Returns a unique generated equation.
	 * 
	 * @return
	 */
	Equation generate();

	/**
	 * String representation, essentially "game type".
	 */
	String asString();

	/**
	 * Max number allowed to be answer.
	 * 
	 * @param max
	 */
	void setMax(int max);

	void setMin(int min);
}
