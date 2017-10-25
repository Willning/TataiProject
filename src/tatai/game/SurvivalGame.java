package tatai.game;

import maths.EquationFactory;
import tatai.gui.level.Level;

public class SurvivalGame extends Game {
    private int lives;

    public SurvivalGame(GameType gameType, EquationFactory equationFactory, GameDifficulty gameDifficulty) {
        super(gameType, equationFactory, gameDifficulty);
        lives = 3;
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        getLevel().updateLives(lives);
    }

    @Override
    protected void loseRound() {
        lives--;
        getLevel().updateLives(lives);
        super.loseRound();
    }

    @Override
    boolean endGameCondition() {
        return (lives == 0);
    }
}
