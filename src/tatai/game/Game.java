package tatai.game;


import maths.Equation;
import maths.EquationFactory;
import maths.PronunciationHandler;
import tatai.gui.level.Level;
import tatai.user.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Game {

    private EquationFactory equationFactory;
    private int currentRound;
    private PronunciationHandler pronunciationHandler;
    private Equation currentEquation;
    private int score = 0;
    private Level level;
    private GameData gameData;
    private String receivedAnswer;
    private int currentAttempt;
    private GameType gameType;
    private GameDifficulty gameDifficulty;
    private SoundHandler soundHandler;

    /**
     * Constructor, it takes the equationFactory that will be used and how many rounds the game will be.
     *
     * @param equationFactory
     */
    public Game(GameType gameType, EquationFactory equationFactory, GameDifficulty gameDifficulty) {
        this.equationFactory = equationFactory;
        this.currentRound = 1;
        this.currentAttempt = 1;
        this.gameData = new GameData(gameType, gameDifficulty);
        this.gameType = gameType;
        this.gameDifficulty = gameDifficulty;
        this.soundHandler = new SoundHandler(this);
        pronunciationHandler = new PronunciationHandler();
        currentEquation = equationFactory.generate();

    }

    /**
     * Called the start playing.
     */
    public void play() {soundHandler.play();}

    /**
     * Called to start recording.
     */
    public void record() {soundHandler.record();}

    /**
     * Called to start processing the sound file
     */
    public void process() {soundHandler.process();}

    /**
     * Called to indicate sound has finished playing
     */
    public void playDone() {
        level.playDone();
    }

    /**
     * Called to indicate recording has finished
     */
    public void recordingDone() {
        level.recordingDone();
    }

    /**
     * Process whether the answer is correct or not and take the appropriate action.
     */
    public void processAnswer() {
        boolean answerCorrect = checkAnswer();

        if (answerCorrect) {
            winRound();
        } else if (currentAttempt >= 2) {
            loseRound();
        } else {
            currentAttempt++;
            level.failedAttempt();
        }

        // Tell the controller that processing is now done.
        level.processingDone();
    }


    /**
     * Method that checks the parsed user input against the actual answer and returns whether they got it right or wrong.
     *
     * @return
     */
    private boolean checkAnswer() {
        // Get user input
        ArrayList<String> lines = soundHandler.getTextFromMLF();
        String userAnswer = "";

        // Turn the user input into a single String
        for (String line : lines) {
            userAnswer += " " + line;
            System.out.println(line);
        }

        // Check the received answer against the actual answer from the pronunciation handler
        receivedAnswer = userAnswer;
        String answer = pronunciationHandler.getPronunciation(currentEquation.answer());
        List<String> answers = Arrays.asList(answer.split(" "));
        if (lines.containsAll(answers)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that is called when the user has gotten the correct answer.
     */
    private void winRound() {
        score++;
        gameData.addRound(currentRound, true, receivedAnswer + "", pronunciationHandler.getPronunciation(currentEquation.answer()) + "", currentEquation.toString(), currentAttempt);
        level.answerCorrect();
        endRound();

    }

    /**
     * Method that is called when the user has gotten the wrong answer twice.
     */
    protected void loseRound() {
        gameData.addRound(currentRound, false, receivedAnswer + "", pronunciationHandler.getPronunciation(currentEquation.answer()) + "", currentEquation.toString(), currentAttempt);
        level.answerWrong();
        endRound();
    }

    /**
     * Method that is called when the current round is over.
     */
    private void endRound() {
        soundHandler.deleteSound();
        currentAttempt = 1;
        currentRound++;
        currentEquation = equationFactory.generate();
        if (!endGameCondition()) {
            level.nextLevel();
        } else {
            level.endGame();
        }
    }

    abstract boolean endGameCondition();

    /**
     * Setters and Getters
     */

    public int getCurrentRound() {
        return currentRound;
    }

    public int getScore() {
        return score;
    }

    public GameData gameData() {
        return gameData;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public String getReceivedAnswer() {
        return receivedAnswer;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String equationText() {
        return currentEquation.toString();
    }

    public GameType getGameType() {
        return gameType;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public Level getLevel() {
        return level;
    }
}
