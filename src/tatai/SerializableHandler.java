package tatai;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializableHandler implements Serializable {

	public void saveObject(Object toSave, String filePath) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOutputStream.writeObject(toSave);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	
	public <T> ArrayList<T> loadObjectsInDirectory(String dirPath, Class<T> clazz) {
		ArrayList<T> objects = new ArrayList<>();
		
		File dir = new File(dirPath);
		if (!dir.exists()) {
            dir.mkdir();
        } else {
        	File[] files = dir.listFiles();
            for (File file : files) {
                try {
                	String pathToFile = dirPath + file.getName();
                	objects.add(clazz.cast(loadObject(pathToFile)));
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
    			}
            }
        }
        return objects;
	}

}