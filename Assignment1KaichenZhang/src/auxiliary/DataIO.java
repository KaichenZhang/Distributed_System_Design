package auxiliary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class DataIO {

 
    private static DataIO instance = new DataIO();
    public static DataIO sharedInstance() {
        return instance;
    }

    private static Gson gson;

    //Constructor
    private DataIO() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public <T> boolean writeToJsonFile(String fileName, T t) {
        try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(gson.toJson(t));
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }
    
    
    public <T> T readFromJsonFile(String fileName, Class<T> _class) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                T t = gson.fromJson(br, _class);
                return t;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }
}
