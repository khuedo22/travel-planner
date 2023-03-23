package ui;

import model.Event;
import model.Month;
import model.Schedule;
import model.Weekday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static model.Weekday.*;

public class PlanningAppGui extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JButton addEventButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton viewScheduleButton;
    private Schedule schedule;



    // EFFECTS: runs the application
    public static void main(String[] args) {
        new PlanningAppGui();
    }

    // EFFECTS: constructs main window of planner
    public PlanningAppGui() {

        super("Travel Planner");
        // TODO
        intializeLayout();
        initializeAction();
        schedule = new Schedule();
    }

    // EFFECTS: adds actionListener to all the buttons in main frame
    private void initializeAction() {
        viewScheduleButton.addActionListener(new ViewScheduleAction());
        addEventButton.addActionListener(new AddEventAction());
    }

    // ActionListener for ViewSchedule function
    private class ViewScheduleAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame scheduleFrame = new JFrame("Schedule");
            scheduleFrame.setSize(600, 300);
            scheduleFrame.setVisible(true);
            scheduleFrame.setSize(300, 600);
            JPanel schedulePanel = new JPanel();
            schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.LINE_AXIS));
            scheduleFrame.add(schedulePanel);

            List<Weekday> weekdays = new ArrayList<>();
            weekdays.add(Monday);
            weekdays.add(Tuesday);
            weekdays.add(Wednesday);
            weekdays.add(Thursday);
            weekdays.add(Friday);
            weekdays.add(Saturday);
            weekdays.add(Sunday);

            HashMap<Weekday, HashMap<double, Event>> weekSchedule = schedule.getWeekSchedule();
            Set<Weekday>  weekdaysInSchedule = weekSchedule.keySet();

            for (Weekday weekday : weekdays) {

            }
        }
    }

    // ActionListener for Add Event function
    private class AddEventAction implements ActionListener {

        JFrame addEventFrame;
        JTextField eventNameTextField;
        JTextField descriptionTextField;
        JComboBox<Integer> startTimeHourInput;
        JComboBox<String> startTimeMinuteInput;
        JComboBox<Integer> endTimeHourInput;
        JComboBox<String> endTimeMinuteInput;
        JComboBox<Weekday> weekdayInput;
        JTextField dayTextField;
        JTextField yearTextField;
        JComboBox<Month> monthsInput;
        JPanel addEventPanel;
        JLabel success;

        @Override
        public void actionPerformed(ActionEvent e) {

            createAddEventInputs();
        }

        // EFFECTS: creates the frame and layout for window that adds an event to the schedule
        private void createAddEventInputs() {
            addEventFrame = new JFrame("Add Event");
            addEventFrame.setVisible(true);
            addEventFrame.setSize(300, 600);
            addEventPanel = new JPanel();
            addEventFrame.add(addEventPanel);
            success = new JLabel("");

            eventNameTextField = createAddEventLabelAndFields("Enter the event name", addEventPanel);
            descriptionTextField = createAddEventLabelAndFields("Enter the description", addEventPanel);

            startTimeHourInput = createHourDropdownAndLabel("Select the start time");
            startTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(startTimeHourInput);
            addEventPanel.add(startTimeMinuteInput);

            endTimeHourInput = createHourDropdownAndLabel("Select the end time");
            endTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(endTimeHourInput);
            addEventPanel.add(endTimeMinuteInput);

            JLabel weekdayLabel = new JLabel("Select the weekday");
            addEventPanel.add(weekdayLabel);
            weekdayInput = createWeekdayDropdownAndLabel("Select the weekday");
            addEventPanel.add(weekdayInput);



            monthsInput = createMonthDropdownAndLabel("Select the month");
            addEventPanel.add(monthsInput);

            dayTextField = createAddEventLabelAndFields("Enter the day of the month", addEventPanel);
            yearTextField = createAddEventLabelAndFields("Enter the year", addEventPanel);

            confirmEvent();

            success.setText("Successfully added to schedule");



        }

        // EFFECTS: creates the confirmation button
        private void confirmEvent() {
            JButton confirmButton = new JButton("Add event to schedule");
            confirmButton.addActionListener(new ConfirmEventActionListener());
            addEventPanel.add(confirmButton);
        }

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

        // EFFECTS: creates a JComboBox dropdown and label representing the weekdays. Returns the JComboBox
        public JComboBox<Weekday> createWeekdayDropdownAndLabel(String label) {
            addEventPanel.add(new JLabel(label));
            Weekday[] weekdayOptions = {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday};
            return new JComboBox<>(weekdayOptions);
        }

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
            JTextField addEventInput = new JTextField(25);
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

            public ConfirmEventActionListener() {

            }

            // EFFECTS: convers the selected minutes to doubles and returns it
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
                JLabel success = new JLabel("Successfully added to schedule");
                addEventPanel.add(success);



            }

        }


    }



    private void intializeLayout() {
        frame = new JFrame("Travel Planner");
        panel = new JPanel();
        addEventButton = new JButton("Add Event");
        saveButton = new JButton("Save schedule");
        loadButton = new JButton("Load schedule");
        viewScheduleButton = new JButton("View Schedule");
        panel.add(addEventButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(viewScheduleButton);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }





}
