package maths;

public interface EquationFactory {

	/**
	 * Returns a unique generated equation.
	 * 
	 * @return
	 */
	Equation generate();

	/**
	 * Max number allowed to be answer.
	 * 
	 * @param max
	 */
	void setMax(int max);

	void setMin(int min);
}
