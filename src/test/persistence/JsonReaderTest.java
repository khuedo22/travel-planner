package persistence;

import model.EventX;
import model.Schedule;
import model.Weekday;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

// test in this class are based on test for JsonReader in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest {

    @Test
    void readNonExistentFileTest() {
        JsonReader reader = new JsonReader("./data/nothing.json");
        try {
            Schedule schedule = reader.read();
            fail("IOException should have be thrown");
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
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule schedule = reader.read();

            HashMap<Weekday, HashMap<Double, EventX>> weekSchedule = schedule.getWeekSchedule();
            assertEquals(2, weekSchedule.size());
            assertTrue(weekSchedule.containsKey(Weekday.Tuesday));
            assertTrue(weekSchedule.containsKey(Weekday.Wednesday));

            HashMap<Double, EventX> tuesday = weekSchedule.get(Weekday.Tuesday);
            assertEquals(1, tuesday.size());
            assertEquals("flying", tuesday.get(12.30).getEventName());

            HashMap<Double, EventX> wednesday = weekSchedule.get(Weekday.Wednesday);
            assertEquals(2, wednesday.size());
            assertEquals("jogging", wednesday.get(13.00).getEventName());
            assertEquals("restaurant", wednesday.get(14.0).getEventName());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
