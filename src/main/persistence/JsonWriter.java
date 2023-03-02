package persistence;

import model.Schedule;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private String destination;
    private PrintWriter writer;
    private static final int TAB = 4;


    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(Schedule s) {
        JSONObject json = s.toJson();
        saveToFile(json.toString(TAB));
    }

    private void saveToFile(String json) {
        writer.print(json);
    }

    public void close() {
        writer.close();
    }


}
