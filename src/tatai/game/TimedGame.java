package tatai.game;

import maths.EquationFactory;
import tatai.gui.level.Level;

import java.util.Timer;
import java.util.TimerTask;

public class TimedGame extends Game {

    public static final int TIME_LIMIT = 180;

    private Timer timer;
    private int timeLeft;
    private boolean levelSetStatus = false;

    public TimedGame(GameType gameType, EquationFactory equationFactory, GameDifficulty gameDifficulty) {
        super(gameType, equationFactory, gameDifficulty);
        timer =  new Timer();

        timeLeft = TIME_LIMIT;
    }

    @Override
    boolean endGameCondition() {
        return (timeLeft == 0);
    }

    private void updateTime() {
        getLevel().updateTime(timeLeft);
        if (timeLeft != 0) {
            timeLeft--;
        }
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (!levelSetStatus) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateTime();
                }
            }, 0, 1000);
        }
        levelSetStatus = true;
    }
}
