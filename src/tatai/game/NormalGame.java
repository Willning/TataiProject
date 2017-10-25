package tatai.game;

import maths.EquationFactory;

public class NormalGame extends Game{

    public static final int AMOUNT_OF_ROUNDS = 10;

    public NormalGame(GameType gameType, EquationFactory equationFactory, GameDifficulty gameDifficulty) {
        super(gameType, equationFactory, gameDifficulty);
    }

    @Override
    boolean endGameCondition() {
        return (getCurrentRound() == AMOUNT_OF_ROUNDS);
    }
}
