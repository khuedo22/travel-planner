package persistence;

import model.Schedule;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
// all methods in this class is based on methods in the JsonWriter class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// represents a writer that writes a Json file representing a schedule
public class JsonWriter {
    private String destination;
    private PrintWriter writer;
    private static final int TAB = 6;

    // MODIFIES: this
    // EFFECTS: constructs a writer that writes a Json file representing a schedule to the given destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer. If writer of destination file cannot be opened, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes a Json file representing a schedule
    public void write(Schedule s) {
        JSONObject json = s.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }


}
