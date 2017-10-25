package maths;


import java.util.ArrayList;
import java.util.Random;

/**
 * This class will be responsible for generating whats the next number in the sequence type questions.
 * The way that the sequence question works is that you get 3 elements and you have to name the fourth.
 */
public class SequenceEquationFactory implements EquationFactory {

    private int max;

    private int min;

    private int answer;

    private ArrayList<Integer> sequence = new ArrayList<Integer>();


    @Override
    public Equation generate() {
        sequence.clear();

        createSequence();
        String output = "";
        //parse out the individual numbers into a string.
        if (!sequence.isEmpty()){
            output = String.format("%d, %d, %d, ?", sequence.get(0), sequence.get(1), sequence.get(2));
        }
        System.out.println(answer);
        System.out.println(output);

        return new Equation(output, answer) ;
    }

    private void createSequence(){
        //create a seed number which will be the answer.
        Random random = new Random();
        answer  = random.nextInt(max - min) +min;

        //need to make a better step size generation
        //Maximum step that should be counted is 5. This may change in future.
        int stepSize = 5;

        while ((answer + stepSize * 3 > max) && (answer - stepSize * 3 <= 0) ){
            //if adventurous, randomise the stepsapce based on a decreasing range.
            stepSize --;
        }


        //will try to be an ascending sequence first
        if (answer - stepSize*3 > 0) {
            //ascending sequence;
            for (int i = 0; i < 3; i++) {

                sequence.add(answer - (3 - i) * stepSize);
            }
        } else if (answer + stepSize*3 <= max){
            //descending sequence;
            for (int i = 0; i < 3; i++) {
                sequence.add(answer + (3 - i) * stepSize);
            }

        }

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
