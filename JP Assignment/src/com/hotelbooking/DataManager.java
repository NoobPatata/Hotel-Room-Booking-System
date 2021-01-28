package com.hotelbooking;

import java.util.ArrayList;
import java.io.*;

/**
 * @author Chye Wei Lun
 */
public class DataManager implements Serializable {
    /**
     * Read in a list of objects from serialized file
     * @param filename to save to
     * @return list of objects
     */
    public ArrayList<Object> readSerializedObjectList(String filename) {

        ArrayList<Object> pDetails = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            pDetails = (ArrayList<Object>) in.readObject();
            in.close();
        }
        catch (FileNotFoundException ex){
            return pDetails;
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return pDetails;
    }

    /**
     * Write in a list of objects to a serialized file
     * @param filename to write to
     */
    public void writeSerializedObjectList(String filename, ArrayList list) {
        File file;
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
