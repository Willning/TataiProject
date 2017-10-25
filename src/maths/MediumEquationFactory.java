package maths;

import java.util.Random;

/**
 * Equation factory that generates random equations based on Medium difficulty, simple equations to 99 and
 * sequences up to 10.
 */

public class MediumEquationFactory implements EquationFactory{

    SimpleEquationFactory simpleEquations = new SimpleEquationFactory();
    MultiEquationFactory multiEquations = new MultiEquationFactory();

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
}
