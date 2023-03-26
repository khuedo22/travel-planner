package ui;

import model.Event;
import model.Month;
import model.Schedule;
import model.Weekday;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static model.Weekday.*;

public class PlanningAppGui extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JButton addEventButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton viewScheduleButton;
    private JButton removeButton;
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
        removeButton.addActionListener(new RemoveEventAction());
    }


    private class RemoveEventAction implements ActionListener {

        JLabel success;
        JButton selectButton;
        JPanel timePanel;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame removeFrame = new JFrame("Remove Event");
            removeFrame.setSize(700, 300);
            removeFrame.setVisible(true);
            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.LINE_AXIS));
            removeFrame.add(optionsPanel);
            List<Weekday> weekdays = new ArrayList<>();
            weekdays.add(Monday);
            weekdays.add(Tuesday);
            weekdays.add(Wednesday);
            weekdays.add(Thursday);
            weekdays.add(Friday);
            weekdays.add(Saturday);
            weekdays.add(Sunday);

            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            Set<Weekday> weekdaysInSchedule = weekSchedule.keySet();

            for (Weekday weekday : weekdays) {
                JPanel dayPanel = new JPanel();
                TitledBorder dayTitle = BorderFactory.createTitledBorder(weekday.toString());
                dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
                dayPanel.setBorder(dayTitle);
                dayPanel.setPreferredSize(new Dimension(100, 300));
                optionsPanel.add(dayPanel);
                if (weekdaysInSchedule.contains(weekday)) {
                    HashMap<Double, Event> daySchedule = weekSchedule.get(weekday);
                    Set<Double> timeSet = daySchedule.keySet();
                    List<Double> timeList = new ArrayList<>(timeSet);
                    Collections.sort(timeList);
                    for (Double time : timeList) {
                        Event event = daySchedule.get(time);
                        timePanel = new JPanel();
                        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
                        dayPanel.add(timePanel);
                        JLabel timeInfo = new JLabel(convertTime(event.getStartTime()));
                        timePanel.add(timeInfo);
                        JLabel eventName = new JLabel(event.getEventName());
                        timePanel.add(eventName);
                        JLabel description = new JLabel(event.getDescription());
                        timePanel.add(description);
                        //todo figure out time formats
                        createTimeJLabels(timePanel, event.getMonth().toString());
                        createTimeJLabels(timePanel, Integer.toString(event.getDay()));
                        createTimeJLabels(timePanel, Integer.toString(event.getYear()));
                        selectButton = new JButton("Select");
                        selectButton.addActionListener(new RemoveEvent(event));
                        timePanel.add(selectButton);
                        success = new JLabel("");
                        timePanel.add(success);

                    }
                }

            }
        }



        private class RemoveEvent implements ActionListener {
            private Event event;

            public RemoveEvent(Event event) {
                this.event = event;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                schedule.removeEvent(event);
                success.setText("Successfully Removed");
                timePanel.remove(selectButton);
            }
        }
    }


    // ActionListener for ViewSchedule function
    private class ViewScheduleAction implements ActionListener {
        JFrame scheduleFrame;
        JPanel schedulePanel;
        JPanel dayPanel;

        @Override
        public void actionPerformed(ActionEvent e) {
//            scheduleFrame = new JFrame("Schedule");
//            scheduleFrame.setSize(700, 300);
//            scheduleFrame.setVisible(true);
//            schedulePanel = new JPanel();
//            schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.LINE_AXIS));
//            scheduleFrame.add(schedulePanel);
            // abstract above
            setMainFrame();

            List<Weekday> weekdays = new ArrayList<>(Arrays.asList(Weekday.values()));

            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            Set<Weekday>  weekdaysInSchedule = weekSchedule.keySet();

            for (Weekday weekday : weekdays) {
//                dayPanel = new JPanel();
//                TitledBorder dayTitle = BorderFactory.createTitledBorder(weekday.toString());
//                dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
//                dayPanel.setBorder(dayTitle);
//                dayPanel.setPreferredSize(new Dimension(100, 300));
//                schedulePanel.add(dayPanel);
                createWeekdayPanel(weekday);

                // abstract above, weekday as parameter
                if (weekdaysInSchedule.contains(weekday)) {
//                    HashMap<Double, Event> daySchedule = weekSchedule.get(weekday);
//                    Set<Double> timeSet = daySchedule.keySet();
//                    List<Double> timeList = new ArrayList<>(timeSet);
//                    Collections.sort(timeList);
                    List<Double> timeList = getOrderedTimes(weekSchedule, weekday);
                    // abstract above
                    for (Double time : timeList) {
                        Event event = weekSchedule.get(weekday).get(time);
//                        JPanel timePanel = new JPanel();
//                        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
//                        dayPanel.add(timePanel);
//                        JLabel timeInfo = new JLabel(convertTime(event.getStartTime()));
//                        timePanel.add(timeInfo);
//                        JLabel eventName = new JLabel(event.getEventName());
//                        timePanel.add(eventName);
//                        JLabel description = new JLabel(event.getDescription());
//                        timePanel.add(description);
//                        //todo figure out time formats
//                        createTimeJLabels(timePanel, event.getMonth().toString());
//                        createTimeJLabels(timePanel, Integer.toString(event.getDay()));
//                        createTimeJLabels(timePanel, Integer.toString(event.getYear()));
                        createTimePanel(event);
                    }

                        // todo fix layout
                }
            }
        }

        public void createTimePanel(Event event) {
            JPanel timePanel = new JPanel();
            timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
            dayPanel.add(timePanel);
            JLabel timeInfo = new JLabel(convertTime(event.getStartTime()));
            timePanel.add(timeInfo);
            JLabel eventName = new JLabel(event.getEventName());
            timePanel.add(eventName);
            JLabel description = new JLabel(event.getDescription());
            timePanel.add(description);
            //todo figure out time formats
            createTimeJLabels(timePanel, event.getMonth().toString());
            createTimeJLabels(timePanel, Integer.toString(event.getDay()));
            createTimeJLabels(timePanel, Integer.toString(event.getYear()));
        }

        public void setMainFrame() {
            scheduleFrame = new JFrame("Schedule");
            scheduleFrame.setSize(700, 300);
            scheduleFrame.setVisible(true);
            schedulePanel = new JPanel();
            schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.LINE_AXIS));
            scheduleFrame.add(schedulePanel);
        }

        public void createWeekdayPanel(Weekday weekday) {
            dayPanel = new JPanel();
            TitledBorder dayTitle = BorderFactory.createTitledBorder(weekday.toString());
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
            dayPanel.setBorder(dayTitle);
            dayPanel.setPreferredSize(new Dimension(100, 300));
            schedulePanel.add(dayPanel);
        }

        public List<Double> getOrderedTimes(HashMap<Weekday, HashMap<Double, Event>> weekSchedule, Weekday weekday) {
            HashMap<Double, Event> daySchedule = weekSchedule.get(weekday);
            Set<Double> timeSet = daySchedule.keySet();
            List<Double> timeList = new ArrayList<>(timeSet);
            Collections.sort(timeList);
            return timeList;
        }

        public void createTimeJLabels(JPanel panel, String textField) {
            JLabel label = new JLabel(textField);
            panel.add(label);
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
//            addEventFrame = new JFrame("Add Event");
//            addEventFrame.setVisible(true);
//            addEventFrame.setSize(300, 600);
//            addEventPanel = new JPanel();
//            addEventFrame.add(addEventPanel);
//            success = new JLabel("");
            createMainFrame();

//            eventNameTextField = createAddEventLabelAndFields("Enter the event name", addEventPanel);
//            descriptionTextField = createAddEventLabelAndFields("Enter the description", addEventPanel);
            createNameAndDescriptionFields();

//            startTimeHourInput = createHourDropdownAndLabel("Select the start time");
//            startTimeMinuteInput = createMinuteDropdown();
//            addEventPanel.add(startTimeHourInput);
//            addEventPanel.add(startTimeMinuteInput);
            createTimeFields();

//            endTimeHourInput = createHourDropdownAndLabel("Select the end time");
//            endTimeMinuteInput = createMinuteDropdown();
//            addEventPanel.add(endTimeHourInput);
//            addEventPanel.add(endTimeMinuteInput);

//            weekdayInput = createWeekdayDropdownAndLabel("Select the weekday");
//            addEventPanel.add(weekdayInput);
//
//
//
//            monthsInput = createMonthDropdownAndLabel("Select the month");
//            addEventPanel.add(monthsInput);
//
//            dayTextField = createAddEventLabelAndFields("Enter the day of the month", addEventPanel);
//            yearTextField = createAddEventLabelAndFields("Enter the year", addEventPanel);
            createDateFields();
            confirmEvent();

            success.setText("Successfully added to schedule");

        }

        public void createMainFrame() {
            addEventFrame = new JFrame("Add Event");
            addEventFrame.setVisible(true);
            addEventFrame.setSize(300, 600);
            addEventPanel = new JPanel();
            addEventFrame.add(addEventPanel);
            success = new JLabel("");
        }

        public void createNameAndDescriptionFields() {
            eventNameTextField = createAddEventLabelAndFields("Enter the event name", addEventPanel);
            descriptionTextField = createAddEventLabelAndFields("Enter the description", addEventPanel);
        }

        public void createTimeFields() {
            startTimeHourInput = createHourDropdownAndLabel("Select the start time");
            startTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(startTimeHourInput);
            addEventPanel.add(startTimeMinuteInput);

            endTimeHourInput = createHourDropdownAndLabel("Select the end time");
            endTimeMinuteInput = createMinuteDropdown();
            addEventPanel.add(endTimeHourInput);
            addEventPanel.add(endTimeMinuteInput);
        }

        public void createDateFields() {
            weekdayInput = createWeekdayDropdownAndLabel("Select the weekday");
            addEventPanel.add(weekdayInput);



            monthsInput = createMonthDropdownAndLabel("Select the month");
            addEventPanel.add(monthsInput);

            dayTextField = createAddEventLabelAndFields("Enter the day of the month", addEventPanel);
            yearTextField = createAddEventLabelAndFields("Enter the year", addEventPanel);
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
                JLabel success = new JLabel("Successfully added to schedule");
                addEventPanel.add(success);



            }

        }


    }

    public void createTimeJLabels(JPanel panel, String textField) {
        JLabel label = new JLabel(textField);
        panel.add(label);
    }

    public String convertTime(double time) {
        String numberStr = String.valueOf(time);
        int indexOfDecimal = numberStr.indexOf(".");
        String hour = numberStr.substring(0, indexOfDecimal);
        String minute = numberStr.substring((indexOfDecimal + 1)) + "0";
        return hour + ":" + minute;

    }



    private void intializeLayout() {
        frame = new JFrame("Travel Planner");
        panel = new JPanel();
        addEventButton = new JButton("Add Event");
        saveButton = new JButton("Save schedule");
        loadButton = new JButton("Load schedule");
        viewScheduleButton = new JButton("View Schedule");
        removeButton = new JButton("Remove");

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


}
