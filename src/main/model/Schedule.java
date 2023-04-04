package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.HashMap;

// represents a week schedule of itinerary with events
public class Schedule implements JsonWritable {
    private HashMap<Weekday, HashMap<Double, EventX>> weekSchedule;


    // Constructor
    // EFFECTS: constructs an empty HashMap representing a week
    public Schedule() {
        this.weekSchedule = new HashMap<>();
        EventLog.getInstance().logEvent(new Event("instantiates a new empty schedule"));

    }

    // REQUIRES: event can not be in current schedule, startTime slot must be empty, startTime cannot overlap with
    // endTime of another event in schedule
    // MODIFIES: this
    // EFFECTS: adds given event to corresponding startTime slot and weekday
    public void addEvent(EventX event) {

        if (this.weekSchedule.containsKey(event.getWeekday())) {
            HashMap<Double, EventX> d = this.weekSchedule.get(event.getWeekday());
            d.put(event.getStartTime(), event);
        } else {
            HashMap<Double, EventX> d = new HashMap<>();
            d.put(event.getStartTime(), event);
            this.weekSchedule.put(event.getWeekday(), d);
        }

        EventLog.getInstance().logEvent(new Event("added the event " + event.getEventName()
                + " to the schedule"));
    }

    // REQUIRES: event must be somewhere in weekSchedule
    // EFFECTS: remove given event from weekSchedule
    public void removeEvent(EventX event) {
        Weekday weekday = event.getWeekday();
        HashMap<Double, EventX> d = this.weekSchedule.get(weekday);
        double time = event.getStartTime();
        d.remove(time, event);
        if (d.isEmpty()) {
            this.weekSchedule.remove(weekday);
        }
        EventLog.getInstance().logEvent(new Event("removed the event " + event.getEventName()
                + " from the schedule"));
    }

    // REQUIRES: given Weekday and startTime slot are not empty and contain an event
    // EFFECTS: returns the event at the given Weekday and startTime slot
    public EventX getEvent(Weekday weekday, double startTime) {
        HashMap<Double, EventX> d = this.weekSchedule.get(weekday);
        return d.get(startTime);
    }


    // getters

    // EFFECTS: returns weekSchedule HashMap
    public HashMap<Weekday, HashMap<Double, EventX>> getWeekSchedule() {
        return this.weekSchedule;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Week Schedule", weekScheduleToJson());
        EventLog.getInstance().logEvent(new Event("saving schedule by converting the schedule to a Json object"));
        return json;
    }

    // EFFECTS: converts the schedule into a Json array
    private JSONArray weekScheduleToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Weekday weekday : weekSchedule.keySet()) {
            HashMap<Double, EventX> daySchedule = weekSchedule.get(weekday);
    
            jsonArray.put(dayScheduleToJson(daySchedule, weekday));
        }
        EventLog.getInstance().logEvent(new Event("created a json array representing a week schedule"));
        return jsonArray;
    }

    // EFFECTS: converts the day schedule into a Json object
    private JSONObject dayScheduleToJson(HashMap<Double, EventX> daySchedule, Weekday weekday) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(weekday.toString(), eventToJson(daySchedule, weekday));
        EventLog.getInstance().logEvent(new Event("converted the day schedule to a json object"));
        return jsonObject;
    }

    // EFFECTS: converts all the events in a day into a Json array
    private JSONArray eventToJson(HashMap<Double, EventX> daySchedule, Weekday weekday) {
        JSONArray jsonArray = new JSONArray();
        for (double time : daySchedule.keySet()) {
            jsonArray.put(getEvent(weekday, time).toJson());
        }
        EventLog.getInstance().logEvent(new Event("created a json array representing a day schedule"));
        return jsonArray;
    }
}
