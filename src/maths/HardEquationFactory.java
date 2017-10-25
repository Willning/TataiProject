package maths;

import java.util.Random;

public class HardEquationFactory implements EquationFactory {

    SimpleEquationFactory simpleEquations = new SimpleEquationFactory();
    MultiEquationFactory multiEquations = new MultiEquationFactory();
    SequenceEquationFactory sequenceEquations = new SequenceEquationFactory();

    public HardEquationFactory(){
        simpleEquations.setMax(99);
        multiEquations.setMax(99);
        sequenceEquations.setMax(50);
    }

    @Override
    public Equation generate() {
        Random random = new Random();

        int choice = random.nextInt(3);

        if (choice == 1){
            return simpleEquations.generate();
        }else if (choice == 2){
            return sequenceEquations.generate();
        }else{
            return multiEquations.generate();
        }
    }
}
