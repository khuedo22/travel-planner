package model;

// represents an event or activity that a user may participate in
public class Event {
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
    private String directions;
    private double price;
    private String additionalNotes;


    // REQUIRES: 0.0 <= startTime < 24.0, decimal places can only be .30 or .00 day >= 1 and day <= 31, year > 0
    // EFFECTS: constructs an event class with the given name, description, and startTime
    public Event(String name, String description, double startTime, double endTime, Weekday weekday, int day,
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
        this.directions = "no info";
        this.price = 0;
        this.additionalNotes = "no info";
    }

    // REQUIRES: price >= 0.0
    // EFFECTS: adds additional information to event if users would like
    public void addAdditionalInfo(String transportation, String directions, double price) {
        this.transportationInfo = transportation;
        this.directions = directions;
        this.price = price;
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

    // EFFECTS: returns the time of event
    public double getStartTime() {
        return this.startTime;
    }

    // EFFECTS: returns the weekday of event
    public Weekday getWeekday() {
        return this.weekday;
    }

    // EFFECTS: returns the day of event
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
    // EFFECTS: returns the date of the event as a string with weekday, month, day, and year

    public String getDate() {
        return weekday.toString() + " " + month.toString() + " " + Integer.toString(day) + " "
                + Integer.toString(year);
    }

    // EFFECTS: returns transportation information
    public String getTransportationInfo() {
        return this.transportationInfo;
    }

    // EFFECTS: returns direction information
    public String getDirections() {
        return this.directions;
    }

    //EFFECTS: returns price
    public double getPrice() {
        return this.price;
    }

    //EFFECTS: returns additionalNotes
    public String getAdditionalNotes() {
        return this.additionalNotes;
    }


    // EFFECTS: returns all information of event in the following form
    public String getEventInfo() {
        String eventName = this.eventName;
        String date = getDate();
        String time = Double.toString(this.startTime);
        String description = this.description;
        String transportation = this.transportationInfo;
        String directions = this.directions;
        String price = Double.toString(this.price);
        return eventName + "\nDate: " + date + "\nTime: " + time + "\nDescription: " + description
                + "\nTransportation: " + transportation + "\nDirections: " + directions + "\nPrice: " + price;
    }

    // editors
    // MODIFIES: this
    // EFFECTS: sets event name to newName
    public void editEventName(String newName) {
        this.eventName = newName;
    }

    // MODIFIES: this
    // EFFECTS: sets description to newDescription
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    // REQUIRES: newTime >= 0.0 and < 24.00, decimal places must be <60
    // MODIFIES: this
    // EFFECTS: sets time to newTime
    public void editTime(double newTime) {
        this.startTime = newTime;
    }

    // MODIFIES: this
    // EFFECTS: sets a new weekday for the event
    public void editWeekday(Weekday newWeekday) {
        this.weekday = newWeekday;
    }

    // MODIFIES: this
     // EFFECTS: sets a new month for the event
    public void editMonth(Month newMonth) {
        this.month = newMonth;
    }

    // REQUIRES: newDay >= 1 and newDay <= 31
    // MODIFIES: this
    // EFFECTS: sets a new day for the event
    public void editDay(int newDay) {
        this.day = newDay;
    }

    // REQUIRES: newYear > 0
    // MODIFIES: this
    // EFFECTS: sets a new year for the event
    public void editYear(int newYear) {
        this.year = newYear;
    }

    // MODIFIES: this
    // EFFECTS: sets new transportation info
    public void editTransportation(String newTransportation) {
        this.transportationInfo = newTransportation;
    }

    // MODIFIES: this
    // EFFECTS: sets new direction information
    public void editDirection(String newDirection) {
        this.directions = newDirection;
    }

    // REQUIRES: newPrice >= 0.0
    // MODIFIES: this
    // EFFECTS: sets new price
    public void editPrice(double newPrice) {
        this.price = newPrice;
    }



}
