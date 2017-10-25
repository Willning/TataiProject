package tatai.user.trophies;

import tatai.user.User;

import java.util.ArrayList;
import java.util.Stack;

public class TrophyManager {
    public ArrayList<Trophy> availableTrophies;

    public TrophyManager() {
        availableTrophies = new ArrayList<>();
        // add trophies below
        availableTrophies.add(new FirstQuestionCorrect());
        availableTrophies.add(new FirstQuestionAnsweredInEachDifficulty());
        availableTrophies.add(new FirstFiveQuestionAnsweredCorrectly());
        availableTrophies.add(new FirstFiveQuestionAnsweredCorrectlyInARow());
        availableTrophies.add(new FirstTenQuestionsCorrect());
        availableTrophies.add(new FirstGameFinished());
        availableTrophies.add(new GameEverydayThreeDays());
    }

    public Stack<Trophy> checkUser(User user) {
        Stack<Trophy>  newTrophies = new Stack<>();
        for (Trophy trophy : availableTrophies) {
            boolean userHasTrophy = false;
            for (Trophy userTrophy : user.getTrophies()) {
                if (userTrophy.getName().equals(trophy.getName())) {
                    userHasTrophy = true;
                }
            }
            if (trophy.getTrophy(user) && !userHasTrophy) {
                newTrophies.push(trophy);
            }
        }
        return newTrophies;
    }
}
