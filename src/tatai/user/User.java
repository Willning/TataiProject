package tatai.user;

import tatai.StateSingleton;
import tatai.game.GameDifficulty;
import tatai.game.GameType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Object represents a User
 */
public class User implements Serializable{

	private String username;
	private ArrayList<GameData> games;

	/**
	 * Constructor called
	 * @param username Username (what the user will be referred to as).
	 */
	public User(String username){
		this.username = username;
		games = new ArrayList<>();
	}

	/**
	 * Method that is called to add a new saved game to the user.
	 * @param gameData
	 */
	public void addGame(GameData gameData) {
		games.add(gameData);
		save();
	}

	/**
	 * Returns all the users played games.
	 * @return
	 */
	public ArrayList<GameData> getGames() {
		return games;
	}

	/**
	 * Returns the username of a user.
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Serializes user to a file that is of their username.
	 */
	public void save() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(StateSingleton.USERS_DIR + username));
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns whether the user can play level two of a gameType.
	 * @param gameType
	 * @return
	 */
	public boolean canPlayNextLevel(GameType gameType, GameDifficulty gameDifficulty) {
		for (GameData gameDatum : games) {
			if (gameDatum.getScore() >= 8 && gameDatum.getGameType().equals(gameType) && gameDatum.getGameDifficulty().equals(gameDifficulty)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPlayedGames() {
		return !(games.size() == 0);
	}

	public long getTimeSinceLastPlayed() {
		games.sort(new Comparator<GameData>() {
			@Override
			public int compare(GameData o1, GameData o2) {
				if (o1.getTime().isAfter(o2.getTime())) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		return java.time.temporal.ChronoUnit.DAYS.between(games.get(0).getTime(), LocalDate.now());
	}
}
