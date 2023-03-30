package ui;

import model.Event;
import model.Month;
import model.Schedule;
import model.Weekday;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static model.Weekday.*;

public class PlanningAppGui extends JFrame {

    private JFrame welcomeFrame;
    private JFrame frame;
    private JPanel panel;
    private JButton addEventButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton viewScheduleButton;
    private JButton removeButton;
    private Schedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/schedule.json";



    // EFFECTS: runs the application
    public static void main(String[] args) {
        new PlanningAppGui();
    }

    // EFFECTS: constructs main window of planner
    public PlanningAppGui() {
        super("Travel Planner");
        createWelcomeFrame();
    }

    // MODIFIES: this
    // EFFECTS: constructs the welcome page of app
    public void createWelcomeFrame() {
        int width = 1500 / 2;
        int height = 938 / 2;
        welcomeFrame = new JFrame("Welcome");
        welcomeFrame.setSize(new Dimension(width, height));
        welcomeFrame.setVisible(true);
        welcomeFrame.setLayout(new FlowLayout());
        ImageIcon image = new ImageIcon("./data/plane.jpg");
        ImageIcon scaledImage = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(scaledImage);
        JLabel welcomeText = new JLabel("Welcome to Travel Planner");
        welcomeText.setSize(new Dimension(300, 300));

        welcomeFrame.add(background);
        background.setLayout(new FlowLayout());

        JButton startButton = new JButton("Click here to start planning");
        startButton.addActionListener(new InitiateApp());
        background.add(welcomeText);
        background.add(startButton);

        welcomeFrame.setSize(new Dimension(width - 1, height - 1));
        welcomeFrame.setSize(new Dimension(width, height));
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // ActionListener for going to the main page
    private class InitiateApp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            initializeLayout();
            initializeAction();

            welcomeFrame.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds actionListener to all the buttons in main frame
    private void initializeAction() {
        schedule = new Schedule();
        loadButton.addActionListener(new LoadEventAction());
        saveButton.addActionListener(new SaveScheduleAction());
        viewScheduleButton.addActionListener(new ViewScheduleAction());
        addEventButton.addActionListener(new AddEventAction());
        removeButton.addActionListener(new RemoveEventAction());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }

    // MODIFIES: this
    // EFFECTS: creates main menu for application
    private void initializeLayout() {
        frame = new JFrame("Travel Planner");
        panel = new JPanel();
        addEventButton = new JButton("Add event");
        saveButton = new JButton("Save schedule");
        loadButton = new JButton("Load schedule");
        viewScheduleButton = new JButton("View schedule");
        removeButton = new JButton("Remove event");

        panel.add(addEventButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(viewScheduleButton);
        panel.add(removeButton);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // actionListener for displaying the events available to remove

    private class RemoveEventAction implements ActionListener {

        private JFrame scheduleFrame;
        private JPanel schedulePanel;
        private JPanel dayPanel;

        @Override
        public void actionPerformed(ActionEvent e) {

            createSchedule();
        }

        // MODIFIES: this
        // EFFECTS: creates a schedule to display when user wants to remove an event
        public void createSchedule() {
            setMainFrame();

            List<Weekday> weekdays = new ArrayList<>(Arrays.asList(Weekday.values()));

            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            Set<Weekday>  weekdaysInSchedule = weekSchedule.keySet();

            for (Weekday weekday : weekdays) {

                createDayPanel(weekday);

                if (weekdaysInSchedule.contains(weekday)) {

                    List<Double> timeList = getOrderedTimes(weekSchedule, weekday);

                    for (Double time : timeList) {
                        Event event = weekSchedule.get(weekday).get(time);

                        createTimePanel(event);
                    }
                }
            }
        }

        // REQUIRES: Event must be in schedule
        // MODIFIES: this
        // EFFECTS: creates the panel that presents a specific event
        public void createTimePanel(Event event) {
            JPanel timePanel = new JPanel();
            timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
            timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            dayPanel.add(timePanel);
            JLabel timeInfo = new JLabel("Time: " + convertTime(event.getStartTime()));
            timePanel.add(timeInfo);
            JLabel eventName = new JLabel("Name: " + event.getEventName());
            timePanel.add(eventName);
            JLabel description = new JLabel("Description: " + event.getDescription());
            timePanel.add(description);

            createJLabelsForTimePanel(timePanel, event.getMonth().toString(), "Month: ");
            createJLabelsForTimePanel(timePanel, Integer.toString(event.getDay()), "Day: ");
            createJLabelsForTimePanel(timePanel, Integer.toString(event.getYear()), "Year: ");
            JButton selectButton = new JButton("Select");

            timePanel.add(selectButton);
            JLabel success = new JLabel("");
            timePanel.add(success);
            selectButton.addActionListener(new RemoveEvent(event, success));
        }

        // MODIFIES: this
        // EFFECTS: creates the main frame that pops up to view the schedule
        public void setMainFrame() {
            scheduleFrame = new JFrame("Schedule");
            scheduleFrame.setSize(1400, 600);
            scheduleFrame.setVisible(true);
            schedulePanel = new JPanel();
            schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.LINE_AXIS));
            scheduleFrame.add(schedulePanel);
        }

        // MODIFIES: this
        // EFFECTS: creates a panel representing a day
        public void createDayPanel(Weekday weekday) {
            dayPanel = new JPanel();
            TitledBorder dayTitle = BorderFactory.createTitledBorder(weekday.toString());
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
            dayPanel.setBorder(dayTitle);
            dayPanel.setPreferredSize(new Dimension(200, 600));
            schedulePanel.add(dayPanel);
        }

        // EFFECTS: returns a list of the times of the events of that day in order
        public List<Double> getOrderedTimes(HashMap<Weekday, HashMap<Double, Event>> weekSchedule, Weekday weekday) {
            HashMap<Double, Event> daySchedule = weekSchedule.get(weekday);
            Set<Double> timeSet = daySchedule.keySet();
            List<Double> timeList = new ArrayList<>(timeSet);
            Collections.sort(timeList);
            return timeList;
        }

        // EFFECTS: creates a label with the given info and title and adds it to given panel
        public void createJLabelsForTimePanel(JPanel panel, String textField, String title) {
            JLabel label = new JLabel(title + textField);
            panel.add(label);
        }

        // actionListener for removing a selected event
        private class RemoveEvent implements ActionListener {
            private Event event;
            private JLabel success;

            // EFFECTS: sets the desired event to be removed to this event and the success confirmation message
            public RemoveEvent(Event event, JLabel success) {
                this.event = event;
                this.success = success;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                schedule.removeEvent(event);
                success.setText("Successfully Removed");
                JButton removeButton = (JButton) e.getSource();
                removeButton.setEnabled(false);

            }
        }

    }



    // ActionListener for ViewSchedule function
    private class ViewScheduleAction implements ActionListener {
        private JFrame scheduleFrame;
        private JPanel schedulePanel;
        private JPanel dayPanel;
        private JPanel timePanel;

        @Override
        public void actionPerformed(ActionEvent e) {
            createSchedule();
        }

        // MODIFIES: this
        // EFFECTS: creates the schedule that will be displayed when user wants to view their schedule
        public void createSchedule() {
            setMainFrame();

            List<Weekday> weekdays = new ArrayList<>(Arrays.asList(Weekday.values()));

            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            Set<Weekday>  weekdaysInSchedule = weekSchedule.keySet();

            for (Weekday weekday : weekdays) {

                createDayPanel(weekday);

                if (weekdaysInSchedule.contains(weekday)) {

                    List<Double> timeList = getOrderedTimes(weekSchedule, weekday);

                    for (Double time : timeList) {
                        Event event = weekSchedule.get(weekday).get(time);

                        createTimePanel(event);
                    }
                }
            }
        }

        // MODIFIES: this
        // EFFECTS: creates the panel that presents a specific event
        public void createTimePanel(Event event) {
            timePanel = new JPanel();
            timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
            timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            dayPanel.add(timePanel);
            JLabel timeInfo = new JLabel("Time: " + convertTime(event.getStartTime()));
            timePanel.add(timeInfo);
            JLabel eventName = new JLabel("Name: " + event.getEventName());
            timePanel.add(eventName);
            JLabel description = new JLabel("Description: " + event.getDescription());
            timePanel.add(description);

            createJLabelsForTimePanel(timePanel, event.getMonth().toString(), "Month: ");
            createJLabelsForTimePanel(timePanel, Integer.toString(event.getDay()), "Day: ");
            createJLabelsForTimePanel(timePanel, Integer.toString(event.getYear()), "Year: ");
        }

        // MODIFIES: this
        // EFFECTS: creates the main frame that pops up to view the schedule
        public void setMainFrame() {
            scheduleFrame = new JFrame("Schedule");
            scheduleFrame.setSize(1400, 600);
            scheduleFrame.setVisible(true);
            schedulePanel = new JPanel();
            schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.LINE_AXIS));
            scheduleFrame.add(schedulePanel);
        }

        // MODIFIES: this
        // EFFECTS: creates a panel representing a day
        public void createDayPanel(Weekday weekday) {
            dayPanel = new JPanel();
            TitledBorder dayTitle = BorderFactory.createTitledBorder(weekday.toString());
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
            dayPanel.setBorder(dayTitle);
            dayPanel.setPreferredSize(new Dimension(200, 600));
            schedulePanel.add(dayPanel);
        }

        // EFFECTS: returns a list of the times of the events of that day in order
        public List<Double> getOrderedTimes(HashMap<Weekday, HashMap<Double, Event>> weekSchedule, Weekday weekday) {
            HashMap<Double, Event> daySchedule = weekSchedule.get(weekday);
            Set<Double> timeSet = daySchedule.keySet();
            List<Double> timeList = new ArrayList<>(timeSet);
            Collections.sort(timeList);
            return timeList;
        }

        // EFFECTS: creates a label with the given info and title and adds it to given panel
        public void createJLabelsForTimePanel(JPanel panel, String textField, String title) {
            JLabel label = new JLabel(title + textField);
            panel.add(label);
        }
    }

    // ActionListener for Add Event function
    private class AddEventAction implements ActionListener {

        private JFrame addEventFrame;
        private JTextField eventNameTextField;
        private JTextField descriptionTextField;
        private JComboBox<Integer> startTimeHourInput;
        private JComboBox<String> startTimeMinuteInput;
        private JComboBox<Integer> endTimeHourInput;
        private JComboBox<String> endTimeMinuteInput;
        private JComboBox<Weekday> weekdayInput;
        private JTextField dayTextField;
        private JTextField yearTextField;
        private JComboBox<Month> monthsInput;
        private JPanel addEventPanel;
        private JButton confirmButton;
        private JLabel success;

        @Override
        public void actionPerformed(ActionEvent e) {
            createAddEventInputs();
        }

        // MODIFIES: this
        // EFFECTS: creates the frame and layout for window that adds an event to the schedule
        private void createAddEventInputs() {

            createMainFrame();
            createNameAndDescriptionFields();
            createTimeFields();
            createDateFields();
            confirmEvent();
            success.setText("Successfully added to schedule");

        }

        // MODIFIES: this
        // EFFECTS: creates the main frame for adding an event to schedule
        public void createMainFrame() {
            addEventFrame = new JFrame("Add Event");
            addEventFrame.setSize(400, 600);
            addEventPanel = new JPanel();
            addEventPanel.setLayout(new BoxLayout(addEventPanel, BoxLayout.Y_AXIS));
            addEventFrame.add(addEventPanel);
            addEventFrame.setVisible(true);
            success = new JLabel("");
        }

        // MODIFIES: this
        // EFFECTS: sets the name and description fields
        public void createNameAndDescriptionFields() {
            eventNameTextField = createAddEventLabelAndFields("Enter the event name", addEventPanel);
            addFiller();
            descriptionTextField = createAddEventLabelAndFields("Enter the description", addEventPanel);
            addFiller();
        }

        // MODIFIES: this
        // EFFECTS: sets dropdown menus for all the time fields
        public void createTimeFields() {
            startTimeHourInput = createHourDropdownAndLabel("Select the start time");
            startTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(startTimeHourInput);
            addEventPanel.add(startTimeMinuteInput);
            addFiller();

            endTimeHourInput = createHourDropdownAndLabel("Select the end time");
            endTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(endTimeHourInput);
            addEventPanel.add(endTimeMinuteInput);
            addFiller();
        }

        // MODIFIES: this
        // EFFECTS: sets all the date fields
        public void createDateFields() {
            weekdayInput = createWeekdayDropdownAndLabel("Select the weekday");
            addEventPanel.add(weekdayInput);
            addFiller();
            monthsInput = createMonthDropdownAndLabel("Select the month");
            addEventPanel.add(monthsInput);
            addFiller();
            dayTextField = createAddEventLabelAndFields("Enter the day of the month", addEventPanel);
            addFiller();
            yearTextField = createAddEventLabelAndFields("Enter the year", addEventPanel);
            addFiller();
        }

        // MODIFIES: this
        // EFFECTS: adds invisible filler
        public void addFiller() {
            addEventPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        }


        // MODIFIES: this
        // EFFECTS: creates the confirmation button
        private void confirmEvent() {
            confirmButton = new JButton("Add event to schedule");
            confirmButton.addActionListener(new ConfirmEventActionListener());
            addEventPanel.add(confirmButton);
        }

        // MODIFIES: this
        // EFFECTS: creates a JComboBox dropdown and label representing the hours of time. Returns the JComboBox
        public JComboBox<Integer> createHourDropdownAndLabel(String label) {
            Integer[] hourTimeOptions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
                    23};
            addEventPanel.add(new JLabel(label));
            return new JComboBox<>(hourTimeOptions);
        }

        // EFFECTS: creates a JComboBox dropdown and label representing the minutes of time. Returns the JComboBox
        public JComboBox<String> createMinuteDropdown() {
            String[] minuteTimeOptions = {"00", "30"};
            return new JComboBox<>(minuteTimeOptions);
        }

        // MODIFIES: this
        // EFFECTS: creates a JComboBox dropdown and label representing the weekdays. Returns the JComboBox
        public JComboBox<Weekday> createWeekdayDropdownAndLabel(String label) {
            addEventPanel.add(new JLabel(label));
            Weekday[] weekdayOptions = {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday};

            return new JComboBox<>(weekdayOptions);
        }

        // MODIFIES: this
        // EFFECTS: creates a JComboBox dropdown and label representing the months. Returns the JComboBox
        public JComboBox<Month> createMonthDropdownAndLabel(String label) {
            addEventPanel.add(new JLabel(label));
            Month[] monthOption = {Month.January, Month.February, Month.March, Month.April, Month.May, Month.June,
                    Month.July, Month.August, Month.September, Month.October, Month.November, Month.December};

            return new JComboBox<>(monthOption);
        }


        // EFFECTS: creates a JTextField and label, adds label to panel, returns the JTextField
        public JTextField createAddEventLabelAndFields(String label, JPanel panel) {
            JLabel addEventLabel = new JLabel(label);
            JTextField addEventInput = new JTextField();
            panel.add(addEventLabel);
            panel.add(addEventInput);
            return addEventInput;
        }


        // ActionListener for confirming an event
        private class ConfirmEventActionListener implements ActionListener {
            private String eventName;
            private String description;
            private double startTime;
            private double endTime;
            private Weekday weekday;
            private int day;
            private int year;
            private Month month;


            // EFFECTS: converts the selected minutes to doubles and returns it
            public double convertMinuteTime(String endtimeString) {
                if (endtimeString == "30") {
                    return 0.30;
                } else {
                    return 0.00;
                }
            }


            @Override
            public void actionPerformed(ActionEvent e) {
                eventName = eventNameTextField.getText();
                description = descriptionTextField.getText();
                Integer startTimeHour = (Integer) startTimeHourInput.getSelectedItem();
                double startTimeMinute = convertMinuteTime((String) startTimeMinuteInput.getSelectedItem());
                startTime = startTimeHour + startTimeMinute;
                Integer endTimeHour = (Integer) endTimeHourInput.getSelectedItem();
                double endTimeMinute = convertMinuteTime((String) endTimeMinuteInput.getSelectedItem());
                endTime = endTimeHour + endTimeMinute;
                weekday = (Weekday) weekdayInput.getSelectedItem();
                day = Integer.parseInt((dayTextField.getText()));
                year = Integer.parseInt(yearTextField.getText());
                month = (Month) monthsInput.getSelectedItem();
                Event event = new Event(eventName, description, startTime, endTime, weekday, day, month, year);
                schedule.addEvent(event);

                JFrame success = new JFrame("Success");
                success.setSize(new Dimension(250, 150));
                success.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 45));
                JLabel successLabel = new JLabel("Event successfully added");
                success.add(successLabel);
                success.setVisible(true);
                JButton confirm = (JButton) e.getSource();
                confirm.setEnabled(false);
            }
        }
    }



    // EFFECTS: converts given double to time format as a string
    public String convertTime(double time) {
        String numberStr = String.valueOf(time);
        int indexOfDecimal = numberStr.indexOf(".");
        String hour = numberStr.substring(0, indexOfDecimal);
        String minute = numberStr.substring((indexOfDecimal + 1)) + "0";
        return hour + ":" + minute;
    }




    // actionListener for loading the schedule
    private class LoadEventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                schedule = jsonReader.read();
                JFrame success = new JFrame("Successfully loaded");
                success.setSize(200, 150);
                success.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 45));
                success.add(new JLabel("Successfully loaded"));
                success.setVisible(true);
                success.setVisible(true);
            } catch (IOException ex) {
                JFrame jframe = new JFrame("Unable to load schedule");
                jframe.setSize(100, 100);
                jframe.add(new JLabel("Unable to load schedule"));
                jframe.setVisible(true);
            }
        }
    }


    // actionListener for saving schedule
    private class SaveScheduleAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(schedule);
                jsonWriter.close();
                JFrame success = new JFrame("Successfully saved");
                success.setSize(200, 150);
                success.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 45));
                success.add(new JLabel("Successfully saved"));
                success.setVisible(true);
            } catch (FileNotFoundException ex) {
                JFrame jframe = new JFrame("Could not save schedule");
                jframe.setSize(200, 150);
                jframe.add(new JLabel("Could not save schedule"));
                jframe.setVisible(true);
            }
        }
    }
}
