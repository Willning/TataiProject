package maths;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class returns a unique simple equation genereted 
 */
public class SimpleEquationFactory implements EquationFactory {
	private int firstNumber;
	private int secondNumber;
	private Operator operator;
	private int answer;
	private int max;
	private int min;

	/**
	 * return the string "Simple Equations"
	 */
	public String asString() {
		return "Simple Equations";
	}

	/**
	 * default constructor
	 */
	public SimpleEquationFactory() {

	}

	/**
	 * constructor of the class
	 *
	 * @param max
	 *            the maximum value the equation would take
	 * @param firstNumber
	 *            the value of the first number
	 */
	public SimpleEquationFactory(int max, int firstNumber) {
		this.firstNumber = firstNumber;
		this.max = max;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * generate the unique equation, the operation it can take is add, divide,
	 * minus, and multiply.
	 * This only goes up to the 10 times table as per the NZ curriculum standards.
	 */
	private void createEquation() {
		operator = generateOperator();

		
		if (operator == Operator.ADD) {
			firstNumber = randomNumber(1, max);
			answer = randomNumber(firstNumber, max);
			secondNumber = answer - firstNumber;

		} else if (operator == Operator.DIVIDE) {

			answer = randomNumber(1,10);
			secondNumber = randomNumber(1,10);

			while (secondNumber * answer >= max){
				answer = randomNumber(1,10);
				secondNumber = randomNumber(1,10);
			}
			firstNumber = secondNumber * answer;

		} else if (operator == Operator.MINUS) {
			firstNumber = randomNumber(1, max);
			answer = randomNumber(1, firstNumber);
			secondNumber = firstNumber - answer;

		} else if (operator == Operator.MULTIPLY) {
			firstNumber = randomNumber(1, 10);
			secondNumber = randomNumber(1, 10);

			while (firstNumber* secondNumber >= max){
				firstNumber = randomNumber(1, 10);
				secondNumber = randomNumber(1, 10);
			}
			answer = firstNumber * secondNumber;
		}

	}

	/**
	 * return a equation generated, in type Equation
	 */
	public Equation generate() {
		createEquation();

		return new Equation(firstNumber + operator.toString() + secondNumber +"=?", answer);
	}

	/**
	 * randomly generate the operator used for the equation
	 *
	 * @return the operator
	 */
	public Operator generateOperator() {
		int seed = randomNumber(1, 4);
		for (Operator operator : Operator.values()) {
			if (seed == operator.getSeed()) {
				return operator;
			}
		}
		throw new RuntimeException("Error in generating an operator");
	}

	/**
	 * randomly generate a number between the limits
	 *
	 * @param min
	 *            the minimum value it would take
	 * @param max
	 *            the maximum value it would take
	 * @return the number generated
	 */
	public int randomNumber(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min, max: " + max + "min :" + min);
		} else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	public int getFirstNumber(){
		return firstNumber;
	}

	public int getSecondNumner(){
		return secondNumber;
	}

	public Operator getOperator(){
		return operator;
	}

	@Override
	public void setMin(int min) {
		this.min = min;
	}
}
