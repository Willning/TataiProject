package maths;

public interface EquationFactory {

	/**
	 * Returns a randomly generated equation.
	 * 
	 * @return
	 */
	Equation generate();

	/**
	 * generates a random equation given the operator, in the cases where an Operator is not used,
	 * this method is a dummy
	 */
	Equation generate(Operator operator);

	/**
	 * Max number allowed to be answer.
	 * 
	 * @param max
	 */
	void setMax(int max);

	void setMin(int min);
}
