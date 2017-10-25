package maths;

import java.util.ArrayList;
import java.util.Random;

/**
 *This is a  equation Factory that takes in a custom list and reads the elements and outputs the next equation
 *every time the generate is called.
 *
 */

public class CustomEquationFactory implements EquationFactory {

    private ArrayList<Equation> customList = new ArrayList<>();

    public CustomEquationFactory(){

        //here take in a custom list, every time generate is called, iterate once and output question
    }

    @Override
    public Equation generate(Operator operator) {
        return null;
    }

    @Override
    public Equation generate() {

        if (customList.size() == 1){
            return customList.get(0);
        }else {
            Random random = new Random();

            Equation equation = customList.get(random.nextInt(customList.size() - 1));

            return equation;
        }
    }

    @Override
    public void setMax(int max) {

    }

    public void setCustomList(ArrayList<Equation> questions){
        customList = questions;
    }

    @Override
    public void setMin(int min) {

    }
}
