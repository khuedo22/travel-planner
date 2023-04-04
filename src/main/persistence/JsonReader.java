package persistence;

// all methods in this class is based on the JsonReader class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git


import model.EventX;
import model.Month;
import model.Schedule;
import model.Weekday;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;


// represents a reader that reads a Json file representing a schedule
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader that reads a Json file from given source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the Json file representing a schedule and returns the schedule. If file cannot be read,
    // throws IOException
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // MODIFIES: schedule
    // EFFECTS: returns a schedule from corresponding Json object, throws IOException if schedule cannot be
    // read from file
    private Schedule parseSchedule(JSONObject jsonObject) throws IOException {
        Schedule schedule = new Schedule();
        addDaySchedules(schedule, jsonObject);
        return schedule;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: schedule
    // EFFECTS: extracts all day schedules from Json object and adds it to schedule that is being read
    private void addDaySchedules(Schedule schedule, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Week Schedule");
        for (Object json : jsonArray) {
            JSONObject daySchedule = (JSONObject) json;
            addDaySchedule(schedule, daySchedule);
        }
    }

    // MODIFIES: schedule
    // EFFECTS: extracts a day schedule from JsonObject and adds to schedule that is being read
    private void addDaySchedule(Schedule schedule, JSONObject daySchedule) {
        Set<String> weekdaysString = daySchedule.keySet();
        for (String weekdayString : weekdaysString) {
            JSONArray jsonArray = daySchedule.getJSONArray(weekdayString);
            addEvents(schedule, jsonArray);
        }

    }

    // MODIFIES: schedule
    // EFFECTS: extracts events from a Json array day schedule and adds to schedule that is being read
    private void addEvents(Schedule schedule, JSONArray jsonArray) {
        for (Object jsonObject : jsonArray) {
            JSONObject eventJson = (JSONObject) jsonObject;
            String eventName = eventJson.getString("event name");
            String description = eventJson.getString("description");
            Double startTime = eventJson.getDouble("start time");
            Double endTime = eventJson.getDouble("end time");
            Weekday weekday = Weekday.valueOf(eventJson.getString("weekday"));
            int day = eventJson.getInt("day");
            int year = eventJson.getInt("year");
            Month month = Month.valueOf(eventJson.getString("month"));
            String transportationInfo = eventJson.getString("transportation info");
            Double price = eventJson.getDouble("price");
            String additionalNotes = eventJson.getString("additional notes");
            EventX event = new EventX(eventName, description, startTime, endTime, weekday, day, month, year);
            event.editPrice(price);
            event.editAdditionalNotes(additionalNotes);
            event.editTransportation(transportationInfo);
            schedule.addEvent(event);
        }
    }

}



