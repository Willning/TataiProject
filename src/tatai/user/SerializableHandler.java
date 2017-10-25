package tatai.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public void saveObject(Object toSave, String filePath) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOutputStream.writeObject(toSave);
			objectOutputStream.close();
		} catch (IOException e) {
			// HANDLE ERROR
		}
	}
	
	public Object loadObject(String filePath) throws FileNotFoundException {
		Object toLoad = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
			toLoad = objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			if (e instanceof FileNotFoundException) {
				throw new FileNotFoundException();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toLoad;
	}

}