package api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseClient {
    private final String FILE_PATH = "database/moviedata.parquet";

    public DatabaseClient() {
        File f = new File(FILE_PATH);
        if(!f.exists()){
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error, Failed to create database. " + e.toString());
            }
        }
    }

}
