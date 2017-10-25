package maths;

import java.util.Random;

/**
 * Equation factory that generates random equations based on Medium difficulty, simple equations to 99 and
 * sequences up to 10.
 */

public class MediumEquationFactory implements EquationFactory{

    EquationFactory simpleEquations = new SimpleEquationFactory();
    EquationFactory multiEquations = new MultiEquationFactory();

    public MediumEquationFactory(){
        simpleEquations.setMax(99);
        multiEquations.setMax(50);
    }

    @Override
    public Equation generate() {
        Random random = new Random();

        int choice = random.nextInt(2);

        if (choice == 1){
            return simpleEquations.generate();
        }else{
            return multiEquations.generate();
        }

    }

    @Override
    public Equation generate(Operator operator) {
        return null;
    }

    @Override
    public void setMax(int max) {

    }

    @Override
    public void setMin(int min) {

    }
}
