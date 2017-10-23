package tatai.user;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import maths.Equation;
import tatai.StateSingleton;

public class CustomList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String listName;
	private ArrayList<Equation> equations;

	public void save() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(StateSingleton.CUSTOM_LIST_DIR + "lists"));
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
