package persistence;

import model.Event;
import model.Schedule;
import model.Weekday;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test in this class are based on test for JsonReader in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest {

    @Test
    void readNonExistentFileTest() {
        JsonReader reader = new JsonReader("./data/nothing.json");
        try {
            Schedule schedule = reader.read();
            fail("IOException should be thrown");
        } catch (IOException e) {
            // this should pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule schedule = reader.read();
            assertEquals(0, schedule.getWeekSchedule().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule schedule = reader.read();

            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            assertEquals(2, weekSchedule.size());
            assertTrue(weekSchedule.containsKey(Weekday.Monday));
            assertTrue(weekSchedule.containsKey(Weekday.Wednesday));

            HashMap<Double, Event> monday = weekSchedule.get(Weekday.Monday);
            assertEquals(1, monday.size());
            assertEquals("flying", monday.get(13.30).getEventName());

            HashMap<Double, Event> wednesday = weekSchedule.get(Weekday.Wednesday);
            assertEquals(2, wednesday.size());
            assertEquals("painting", wednesday.get(9.00).getEventName());
            assertEquals("gym", wednesday.get(14.30).getEventName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
