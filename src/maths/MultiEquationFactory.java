package maths;

import java.util.ArrayList;

/**
 * class generates a multi part equation from using two or more component equations. 
 * Uses the fact that we can make simple equations easily to string together longer equations
 *  
 */

public class MultiEquationFactory implements EquationFactory{

	private Operator InitialOperator;
	private SimpleEquationFactory componentMaker = new SimpleEquationFactory();

	private String partOne;
	private String partTwo;

	private int answer;

	private int max;
	private int min;

	@Override
	public Equation generate(Operator operator) {
		//Again most likely going to be a dummy method for the sake of the interface.
		return null;
	}

	@Override
	public Equation generate() {
		CreateMultiEquation();

		return new Equation("("+partOne+")"+InitialOperator.toString()+"("+partTwo+") =?",answer);
	}

	/**
	 * Creates an equation satisfying the max, then from the operands, create further equations using the operands as answers.
	 * bugs out if the leading operand is 0. 
	 */
	private void CreateMultiEquation(){
		//why not generate answer first?
		componentMaker.setMax(max);
		componentMaker.setMin(min);
		int firstNumber = 0;
		int secondNumber = 0;
		Equation initial = null;

		while (firstNumber == 0 || secondNumber == 0) {
			initial = componentMaker.generate();
			InitialOperator = componentMaker.getOperator();
			firstNumber = componentMaker.getFirstNumber();
			secondNumber = componentMaker.getSecondNumner();

		}

		//from these two ints, spawn two more equations.
		Equation firstEq= questionFromAnswer(firstNumber);		
		Equation secondEq= questionFromAnswer(secondNumber);

		partOne = firstEq.toString();
		partTwo= secondEq.toString();


		answer = initial.answer();

		System.out.println(answer);

	}

	/*This will generate a question from a given answer.
	/*We assume the equation made from the simple equations are valid.  
	 */

	private Equation questionFromAnswer(int answer){
		Operator operator = componentMaker.generateOperator();
		int operandOne = 100;
		int operandTwo = 100;


		if (operator == Operator.ADD) {
			operandOne = componentMaker.randomNumber(1,answer);
			operandTwo = answer - operandOne;

		} else if (operator == Operator.DIVIDE) {
				//get multiples of the answer that are under the max, then choose one at random then algebra the other.
				ArrayList<Integer> multiple = getMultiples(answer);
				int multipleSeed = componentMaker.randomNumber(0, multiple.size() - 1);
				operandOne = multiple.get(multipleSeed);
				operandTwo = operandOne/answer;

		} else if (operator == Operator.MINUS) {

			operandOne = componentMaker.randomNumber(answer, max); 
			operandTwo = operandOne - answer;

		} else if (operator == Operator.MULTIPLY) {
			//WRONG
				ArrayList<Integer> factors = getFactors(answer);

				int factorSeed = componentMaker.randomNumber(0, factors.size()-1);
				
				operandOne = factors.get(factorSeed);
				operandTwo = answer/operandOne;
		}	

		return new Equation(operandOne + operator.toString() + operandTwo, answer);
	}

	/**
	*Private method used to get the Multiples of a number,
	*@param number, the number you wish to find the multiples of.
	 */
	private ArrayList<Integer> getMultiples(int number){
		ArrayList<Integer> factors = new ArrayList<Integer>();
		int multiple = number;
		int factor = 1;

		while (multiple < max){
			multiple = factor*number;
			factors.add(multiple);
			factor++;
		}
		return factors;
	}

	/**
	 * calculate all the factors of a number
	 *
	 * @param n
	 *            the number that wants to find factors from
	 * @return the factor of the number
	 */
	public ArrayList<Integer> getFactors(int n) {
		ArrayList<Integer> factors = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			if (n % i == 0) {
				factors.add(i);
			}
		}
		return factors;
	}


	@Override
	public void setMax(int max) {
		this.max= max;

	}

	@Override
	public void setMin(int min) {
		this.min= min;
	}

}
