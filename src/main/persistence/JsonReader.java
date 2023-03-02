package persistence;

// code based on JsonReader from JsonSterializationDemo

import model.Event;
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

import static java.lang.Integer.parseInt;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    private Schedule parseSchedule(JSONObject jsonObject) throws IOException {
        Schedule schedule = new Schedule();
        addDaySchedules(schedule, jsonObject);
        return schedule;
    }
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // extracts each day schedule OBJECT from ARRAY that is assigned to week schedule
    private void addDaySchedules(Schedule schedule, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Week Schedule");
        for (Object json : jsonArray) {
            JSONObject daySchedule = (JSONObject) json;
            addDaySchedule(schedule, daySchedule);
        }
    }
    // given an object, extracts array that is assigned to the weekday
    private void addDaySchedule(Schedule schedule, JSONObject daySchedule) {
        Set<String> weekdaysString = daySchedule.keySet();
        for (String weekdayString : weekdaysString) {
            JSONArray jsonArray = daySchedule.getJSONArray(weekdayString);
            addEvents(schedule, jsonArray);
        }

    }

    private void addEvents(Schedule schedule, JSONArray jsonArray) {
        for (Object jsonObject : jsonArray) {
            JSONObject eventJSON = (JSONObject) jsonObject;
            String eventName = eventJSON.getString("event name");
            String description = eventJSON.getString("description");
            Double startTime = Double.parseDouble((eventJSON.getString("start time")));
            Double endTime = Double.parseDouble(eventJSON.getString("end time"));
            Weekday weekday = Weekday.valueOf(eventJSON.getString("weekday"));
            int day = parseInt(eventJSON.getString("day"));
            int year = parseInt(eventJSON.getString("month"));
            Month month = Month.valueOf(eventJSON.getString("year"));
            String transportationInfo = eventJSON.getString("transportation info");
            Double price = Double.parseDouble(eventJSON.getString("price"));
            String additionalNotes = eventJSON.getString("additional notes");
            Event event = new Event(eventName, description, startTime, endTime, weekday, day, month, year);
            event.editPrice(price);
            event.editAdditionalNotes(additionalNotes);
            event.editTransportation(transportationInfo);
            schedule.addEvent(event);
        }
    }

}



