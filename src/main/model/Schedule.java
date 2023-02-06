package model;

import java.util.HashMap;

// represents a schedule of itinerary with events added
public class Schedule {
    private HashMap<Weekday, HashMap<Double, Event>> weekSchedule;


    // Constructor
    // EFFECTS: constructs new HashMaps for each field
    public Schedule() {
        this.weekSchedule = new HashMap<>();

    }

    // REQUIRES: event can not be in current schedule, startTime slot must be empty
    // MODIFIES: this
    // EFFECTS: adds event with given specifications to the correct startTime slot and week day
    public void addEvent(String name, String description, double startTime, double endTime, Weekday weekday, int day,
                         Month month, int year) {
        // check if weekSchedule contains the given weekday key
        // if it does, then we will modify that existing hashmap in that key by calling get() method,
        // and putting the event in that hashmap at the given startTime
        // if weekSchedule does not contain the given key, then we will create a new hashmap, put the event in that
        // hashmap at the given startTime, then put the hashmap in weekSchedule with the key being the given weekday.
        Event e = new Event(name, description, startTime, endTime, weekday, day, month, year);
        if (this.weekSchedule.containsKey(weekday)) {
            HashMap<Double, Event> d = this.weekSchedule.get(weekday);
            d.put(startTime, e);
        } else {
            HashMap<Double, Event> d = new HashMap<>();
            d.put(startTime, e);
            this.weekSchedule.put(weekday, d);
        }
    }

    public void removeEvent(Weekday weekday, double time) {
        HashMap<Double, Event> d = this.weekSchedule.get(weekday);
        d.remove(time);
        if (d.isEmpty()) {
            this.weekSchedule.remove(weekday);
        }
    }



    // getters

    // EFFECTS: returns weekSchedule HashMap
    public HashMap<Weekday, HashMap<Double, Event>> getWeekSchedule() {
        return this.weekSchedule;
    }


}
