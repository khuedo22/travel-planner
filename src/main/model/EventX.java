package model;

import org.json.JSONObject;
import persistence.JsonWritable;

// represents an event or activity that a user may schedule or plan
public class EventX implements JsonWritable {
    private String eventName;
    private String description;
    private double startTime;
    private double endTime;
    private Weekday weekday;
    private int day;
    private int year;
    private Month month;

    // extra info
    private String transportationInfo;
    private double price;
    private String additionalNotes;


    // REQUIRES: 0.0 <= startTime and endTime < 24.0, decimal places can only be .30 or .00 day >= 1 and day <= 31,
    // year > 0
    // EFFECTS: constructs an event class with the given name, description, startTime and endTime, and "no info" for
    // transportationInfo, additionalNotes, and price = 0
    public EventX(String name, String description, double startTime, double endTime, Weekday weekday, int day,
                  Month month, int year) {
        this.eventName = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.day = day;
        this.month = month;
        this.year = year;
        this.transportationInfo = "no info";
        this.price = 0;
        this.additionalNotes = "no info";
        EventLog.getInstance().logEvent(new Event("created a new event called " + getEventName()
                + " starting at " + getStartTime()));
    }

    // getters

    // EFFECTS: returns the event name
    public String getEventName() {
        return this.eventName;
    }

    // EFFECTS: returns the event description
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns the start time of event in format xx.xx (24 hr style)
    public double getStartTime() {
        return this.startTime;
    }

    // EFFECTS: returns the end time of event in format xx.xx (24 hr style)
    public double getEndTime() {
        return this.endTime;
    }

    // EFFECTS: returns the weekday of event
    public Weekday getWeekday() {
        return this.weekday;
    }

    // EFFECTS: returns the day (number) of event
    public int getDay() {
        return this.day;
    }

    // EFFECTS: returns the month of the event
    public Month getMonth() {
        return this.month;
    }

    // EFFECTS: returns the year of event
    public int getYear() {
        return this.year;
    }


    // EFFECTS: returns transportation information of an event
    public String getTransportationInfo() {
        return this.transportationInfo;
    }


    //EFFECTS: returns price of an event
    public double getPrice() {
        return this.price;
    }

    //EFFECTS: returns additionalNotes
    public String getAdditionalNotes() {
        return this.additionalNotes;
    }


    // EFFECTS: returns information about event name, description, transportation and price of event in format below
    public String getSomeEventInfo() {
        String eventName = this.eventName;
        String description = this.description;
        String transportation = this.transportationInfo;
        String price = Double.toString(this.price);
        String addNotes = this.additionalNotes;

        return "Event: " + eventName + "\nDescription: " + description
                + "\nTransportation: " + transportation + "\nPrice: " + price + "\nAdditional Notes: " + addNotes;
    }

    // editors
    // MODIFIES: this
    // EFFECTS: sets event name to newName
    public void editEventName(String newName) {
        this.eventName = newName;
        EventLog.getInstance().logEvent(new Event("Changed the name of the event"));
    }

    // MODIFIES: this
    // EFFECTS: sets description to newDescription
    public void editDescription(String newDescription) {
        this.description = newDescription;
        EventLog.getInstance().logEvent(new Event("Changed the description of the event"));
    }

    // REQUIRES: new times must be >= 0.00 and < 24.00, decimal places must be either .00 or .30
    // MODIFIES: this
    // EFFECTS: sets time to newStartTime
    public void editTime(double newStartTime, double newEndTime) {
        this.startTime = newStartTime;
        this.endTime = newEndTime;
        EventLog.getInstance().logEvent(new Event("changed the start and end time of the event"));
    }


    // REQUIRES: 1 <= newDay <= 31, newYear > 0
    // MODIFIES: this
    // EFFECTS: sets the new date (Weekday, Month, Day, Year) for the event
    public void editDate(Month newMonth, Weekday newWeekday, int newDay, int newYear) {
        this.month = newMonth;
        this.weekday = newWeekday;
        this.day = newDay;
        this.year = newYear;
        EventLog.getInstance().logEvent(new
                Event("changed the date (month, weekday, day, year) of the event"));
    }


    // MODIFIES: this
    // EFFECTS: sets new transportation info
    public void editTransportation(String newTransportation) {
        this.transportationInfo = newTransportation;
    }


    // REQUIRES: newPrice >= 0.0
    // MODIFIES: this
    // EFFECTS: sets new price
    public void editPrice(double newPrice) {
        this.price = newPrice;
    }


    // MODIFIES: this
    // EFFECTS: sets new additional notes
    public void editAdditionalNotes(String newNotes) {
        this.additionalNotes = newNotes;
    }


    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("event name", eventName);
        jsonObject.put("description", description);
        jsonObject.put("start time", startTime);
        jsonObject.put("end time", endTime);
        jsonObject.put("weekday", weekday);
        jsonObject.put("day", day);
        jsonObject.put("month", month);
        jsonObject.put("year", year);
        jsonObject.put("transportation info", transportationInfo);
        jsonObject.put("price", price);
        jsonObject.put("additional notes", additionalNotes);
        EventLog.getInstance().logEvent(new Event("converted event " + getEventName() + " to a Json object"));
        return jsonObject;


    }
}
