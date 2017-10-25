package maths;

/**
 *This is a  equation Factory that takes in a custom list and reads the elements and outputs the next equation
 *every time the generate is called.
 *
 */

public class CustomEquationFactory implements EquationFactory {

    public CustomEquationFactory(){

        //here take in a custom list, every time generate is called, iterate once and output question
    }

    @Override
    public Equation generate(Operator operator) {
        return null;
    }

    @Override
    public Equation generate() {
        return null;
    }

    @Override
    public void setMax(int max) {

    }

    @Override
    public void setMin(int min) {

    }
}
