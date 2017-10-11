package taatai.player;

import taatai.GameData;
import taatai.views.mainContainer.MainContainer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(MainContainer.USERS_DIRECTORY + username));
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns whether the user can play level two of a gameType.
	 * @param gameName
	 * @return
	 */
	public boolean canPlayLevelTwo(String gameName) {
		for (GameData gameDatum : games) {
			if (gameDatum.getScore() >= 8 && gameDatum.getGameType().equals(gameName)) {
				return true;
			}
		}
		return false;
	}
}
