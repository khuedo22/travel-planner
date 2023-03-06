package persistence;

import org.json.JSONObject;

// interface is based on Writable interface in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface JsonWritable {
    // EFFECTS: returns class as a Json object
    JSONObject toJson();
}
