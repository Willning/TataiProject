package taatai.numberProcessing;

/**
 * Maths operators
 */
public enum Operator {
	MULTIPLY(1, "x"), ADD(2, "+"), MINUS(3, "-"), DIVIDE(4, "/");

	private final int seed;
	private final String rep;

	/**
	 * @param seed
	 *            This is useful for random operator generation using random number
	 *            generation
	 * @param rep
	 *            Used as toString();
	 */
	Operator(int seed, String rep) {
		this.rep = rep;
		this.seed = seed;
	}

	@Override
	public String toString() {
		return rep;
	}

	/**
	 * Returns the seed useful for random operator generation.
	 * 
	 * @return
	 */
	public int getSeed() {
		return seed;
	}
}
