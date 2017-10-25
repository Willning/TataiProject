package maths;

import java.util.Random;

/**
 * return a unique random number
 * 
 */
public class SingleNumberEquationFactory implements EquationFactory {

	private int max;
	private int min;



	public SingleNumberEquationFactory() {
		max = 9;
		min = 1;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public Equation generate() {
		int number = randomNumber(min, max);
		return new Equation(number + "", number);
	}

	/**
	 * randomly generate the number between the limits
	 * 
	 * @param min
	 *            the minimum number it can take
	 * @param max
	 *            the maximum number it can take
	 * @return the number generated
	 */
	private int randomNumber(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min, max: " + max + "min :" + min);
		} else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public Equation generate(Operator operator) {
		//Dummy method
		return null;
	}
}
