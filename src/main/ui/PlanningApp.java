package ui;

import model.Event;
import model.Month;
import model.Schedule;
import model.Weekday;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static model.Weekday.*;


// travel planning application
public class PlanningApp {

    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the planning application
    public PlanningApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPlanning();
    }

    // initializes new schedule, takes user input
    private void runPlanning() {
        String choice;
        boolean running = true;
        schedule = new Schedule();
        while (running) {

            input = new Scanner(System.in);
            input.useDelimiter("\n");
            displayMenu();
            choice = input.next();
            choice.toLowerCase();


            if (choice.equals("q")) {
                System.out.println("See you next time");
                running = false;

            } else {

                executeChoice(choice);
            }

        }
    }

    // EFFEECTS: executes user's input choice
    private void executeChoice(String choice) {
        if (choice.equals("s")) {
            scheduleEvent();
            System.out.println("Your event has been scheduled");
            viewSchedule();
        } else if (choice.equals("e")) {
            editEvent();
            System.out.println("Your event has been edited");
            viewSchedule();
        } else if (choice.equals("r")) {
            removeEvent();
            System.out.println("Your event has been removed");
            viewSchedule();
        } else if (choice.equals("v")) {
            viewSchedule();
        } else if (choice.equals("l")) {
            loadSchedule();
            viewSchedule();
        } else if (choice.equals("save")) {
            saveSchedule();
            viewSchedule();
        } else {
            System.out.println("Sorry, your command could not be computed");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the chosen event from the week schedule
    private void removeEvent() {
        if (schedule.getWeekSchedule().isEmpty()) {
            System.out.println("You do not have anything scheduled yet");
        } else {
            Event chosenEvent = getSelectedEvent();
            this.schedule.removeEvent(chosenEvent);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new event with given specifications and add it to the corresponding day and start time slot
    private void scheduleEvent() {

        Event e = createNewEvent();
        this.schedule.addEvent(e);
        addAdditionalInfo(e);


    }

    // EFFECTS: adds information about tranportation, price, and additional notes if user choses to
    public void addAdditionalInfo(Event e) {
        System.out.println("Do you want to add any additional information?");
        System.out.println("y - yes \nn - no");
        String wantsAdditionalInfo = input.next();
        if (wantsAdditionalInfo.equals("y")) {
            System.out.println("Enter transportation information: ");
            String transportationInfo = input.next();
            System.out.println("Enter the price of the event: ");
            double price = input.nextDouble();
            System.out.println("Enter any additional notes about the event: ");
            String addNotes = input.next();
            e.editTransportation(transportationInfo);
            e.editPrice(price);
            e.editAdditionalNotes(addNotes);
        }
    }

    // EFFECTS: creates new event with user input information
    public Event createNewEvent() {
        System.out.println("Enter the event name: ");
        String newEventName = input.next();
        System.out.println("Enter the description: ");
        String newEventDescription = input.next();
        System.out.println("Enter the start time in format xx.xx in 24 hours. The decimals should only be .00 or .30");
        double newEventStartTime = input.nextDouble();
        System.out.println("Enter the end time in format xx.xx in 24 hours. The decimals should only be .00 or .30");
        double newEventEndTime = input.nextDouble();
        System.out.println("Enter the weekday");
        System.out.println("\nmon - Monday \ntue - Tuesday \nwed - Wednesday \nthurs - Thurdsay "
                + "\nfri - Friday \nsat - Saturday \nsun - Sunday");
        Weekday newEventWeekday = convertWeekday(input.next());
        System.out.println("Enter the day of the month: ");
        int newEventDay = input.nextInt();
        System.out.println("Enter the month: ");
        System.out.println("\njan - January \nfeb - February \nmar - March \napr - April \nmay - May \njun - June"
                + "\njul - July \naug - August \nsept - September \noct - October \nnov - November \ndec - December");
        Month newEventMonth = convertMonth(input.next());
        System.out.println("Enter the year: ");
        int newEventYear = input.nextInt();
        Event e = new Event(newEventName, newEventDescription, newEventStartTime, newEventEndTime, newEventWeekday,
                newEventDay, newEventMonth, newEventYear);
        return e;
    }

    // EFFECTS: takes in user command for what they want to edit
    private void editEvent() {
        if (schedule.getWeekSchedule().isEmpty()) {
            System.out.println("You do not have anything scheduled yet");
        } else {
            Event event = getSelectedEvent();
            System.out.println("Which of the following do you want to edit?");
            System.out.println("name - Event Name \ndes - Description \ntime - Start and End Times \ndate - Date"
                    + "\ntrans - Transportation + \n$ - Price \nadd - Additional Notes");
            String chosenEdit = input.next();
            processChosenEdit(chosenEdit, event);
        }
    }

    // EFFECTS: executes the chosen edit
    private void processChosenEdit(String chosenEdit, Event event) {
        if (chosenEdit.equals("name")) {
            processEditName(event);
        }
        if (chosenEdit.equals("des")) {
            processEditDes(event);
        }
        if (chosenEdit.equals("time")) {
            processEditTime(event);
        }
        if (chosenEdit.equals("date")) {
            processEditDate(event);
        }
        if (chosenEdit.equals("trans")) {
            processEditTrans(event);
        }
        if (chosenEdit.equals("$")) {
            processEditPrice(event);
        }
        if (chosenEdit.equals("add")) {
            processEditAddInfo(event);
        }
    }


    // MODIFIES: this
    // EFFECTS: update the additional information of an event to the user's input
    private void processEditAddInfo(Event event) {
        System.out.println("Enter the new additional information");
        String newAddInfo = input.next();
        event.editAdditionalNotes(newAddInfo);

    }

    // MODIFIES: this
    // EFFECTS: update the price of a chosen event to the user's input
    private void processEditPrice(Event event) {
        System.out.println("Enter the new price");
        Double newPrice = input.nextDouble();
        event.editPrice(newPrice);
    }


    // MODIFIES: this
    // EFFECTS: update the transportation of a chosen event to the user's input
    private void processEditTrans(Event event) {
        System.out.println("Enter the new transportation");
        String newTransportation = input.next();
        event.editTransportation(newTransportation);
    }

    // MODIFIES: this
    // EFFECTS: update the date (weekday, month, day, year) of a chosen event to the user's input
    private void processEditDate(Event event) {

        System.out.println("Enter the new weekday");
        System.out.println("\nmon - Monday \ntue - Tuesday \nwed - Wednesday \nthurs - Thurdsay"
                + "\nfri - Friday \nsat - Saturday \nsun - Sunday");
        Weekday newWeekday = convertWeekday(input.next());
        System.out.println("Enter the new month");
        System.out.println("\njan - January \nfeb - February \nmar - March \napr - April \nmay - May \njun - June"
                + "\njul - July \naug = August \nsept - September \noct - October \nnov - November \ndec - December");
        Month newMonth = convertMonth(input.next());
        System.out.println("Enter the new day");
        int newDayOfMonth = Integer.parseInt(input.next());
        System.out.println("Enter the new year");
        int newYear = Integer.parseInt(input.next());
        event.editDate(newMonth, newWeekday, newDayOfMonth, newYear);

    }


    // MODIFIES: this
    // EFFECTS: update the start and end time of a chosen event to the user's input
    private void processEditTime(Event event) {
        System.out.println("Enter the new start time");
        double newStartTime = input.nextDouble();
        double newEndTime = input.nextDouble();
        event.editTime(newStartTime, newEndTime);

    }

    // MODIFIES: this
    // EFFECTS: updates the description of a chosen event to the user's input
    private void processEditDes(Event event) {
        System.out.println("Enter the new description");
        String newDescription = input.next();
        event.editDescription(newDescription);
    }

    // MODIFIES: this
    // EFFECTS: updates the name of a chosen event to the user's input
    private void processEditName(Event event) {
        System.out.println("Enter the new name");
        String newName = input.next();
        event.editEventName(newName);
    }


    // EFFECTS: displays the application's main menu that list the options
    private void displayMenu() {
        System.out.println("Welcome to Travel Planner! What would you like to do?");
        System.out.println("s - schedule an event \ne - edit an event \nr - remove an event \nv - view schedule"
                + "\nq - quit \nl - load schedule \nsave - save schedule");
    }

    // EFFECTS: displays the current schedule to the user, with all the event's information
    private void viewSchedule() {
        HashMap<Weekday, HashMap<Double, Event>> currentSchedule = schedule.getWeekSchedule();
        System.out.println("Current Schedule");
        ArrayList<Weekday> weekdays = listOfWeekdays();
        for (Weekday weekday : weekdays) {
            if (currentSchedule.containsKey(weekday)) {
                System.out.println("Weekday: " + weekday.toString());
                HashMap<Double, Event> daySchedule = schedule.getWeekSchedule().get(weekday);
                for (double startTime : daySchedule.keySet()) {
                    Event event = daySchedule.get(startTime);
                    System.out.println("Start Time: " + Double.toString(startTime));

                    System.out.println("End time: " + event.getEndTime());
                    System.out.println("Month: " + event.getMonth());
                    System.out.println("Day: " + event.getDay());
                    System.out.println(event.getSomeEventInfo());
                    System.out.println(" ");
                }
            }
        }
    }

    // EFFECTS: return a list of Weekdays in order from Monday to Sunday
    public ArrayList<Weekday> listOfWeekdays() {
        ArrayList<Weekday> weekdays = new ArrayList<>();
        weekdays.add(Monday);
        weekdays.add(Tuesday);
        weekdays.add(Wednesday);
        weekdays.add(Thursday);
        weekdays.add(Friday);
        weekdays.add(Saturday);
        weekdays.add(Sunday);
        return weekdays;
    }


    // EFFECTS: returns the event based on user's sepcified weekday and time
    public Event getSelectedEvent() {
        System.out.println("Enter weekday of the event you want to select: \nmon - Monday \ntue - Tuesday "
                + "\nwed - Wednesday \nthurs - Thurdsay \nfri - Friday \nsat - Saturday \nsun - Sunday");
        String chosenDay = input.next();
        chosenDay.toLowerCase();
        Weekday day = convertWeekday(chosenDay);
        System.out.println(("Enter the time in the format xx.xx in 24 hours. The decimals should only be .00 or .30"));
        double chosenTime = input.nextDouble();
        Event chosenEvent = this.schedule.getEvent(day, chosenTime);
        return chosenEvent;

    }

    // REQUIRES: given weekday choice must be valid and spelled correctly
    // EFFECTS: returns the Weekday associated with the input
    private Weekday convertWeekday(String choice) {
        if (choice.equals("mon")) {
            return Monday;
        } else if (choice.equals("tue")) {
            return Tuesday;
        } else if (choice.equals("wed")) {
            return Wednesday;
        } else if (choice.equals("thurs")) {
            return Thursday;
        } else if (choice.equals("fri")) {
            return Friday;
        } else if (choice.equals("sat")) {
            return Saturday;
        } else if (choice.equals("sun")) {
            return Sunday;
        } else {
            return null;
        }
    }

    // REQUIRES: chosen must be a valid string representing a month
    // EFFECTS: returns the month associated with chosen for the first half.
    private Month convertMonth(String chosen) {
        if (chosen.equals("jan")) {
            return Month.January;
        } else if (chosen.equals("feb")) {
            return Month.February;
        } else if (chosen.equals("mar")) {
            return Month.March;
        } else if (chosen.equals("apr")) {
            return Month.April;
        } else if (chosen.equals("may")) {
            return Month.May;
        } else if (chosen.equals("jun")) {
            return Month.June;
        } else {
            return convertRestMonth(chosen);
        }
    }

    // REQUIRES: chosen must be a valid string representing a month
    // EFFECTS: returns the month associated with chosen for the last half.
    private Month convertRestMonth(String chosen) {
        if (chosen.equals("jul")) {
            return Month.July;
        } else if (chosen.equals("aug")) {
            return Month.August;
        } else if (chosen.equals("sept")) {
            return Month.September;
        } else if (chosen.equals("oct")) {
            return Month.October;
        } else if (chosen.equals("nov")) {
            return Month.November;
        } else {
            return Month.December;
        }
    }

    // method is based on saveWorkRoom method in WorkRoomApp class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the schedule as a Json file
    private void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            System.out.println("Saved schedule to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // method is based on loadWorkRoom method in WorkRoomApp class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads the schedule from a Json file
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded schedule from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
