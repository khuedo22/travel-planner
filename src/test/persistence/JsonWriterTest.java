package persistence;

import model.Event;
import model.Month;
import model.Schedule;
import model.Weekday;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

// test in this class are based on test for JsonWriter in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Schedule schedule = new Schedule();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            Schedule schedule = new Schedule();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(schedule);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            schedule = reader.read();;
            HashMap<Weekday, HashMap<Double, Event>> weekSchedule = schedule.getWeekSchedule();
            assertEquals(0, weekSchedule.size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try {
            Schedule schedule = new Schedule();
            Event event1 = new Event("flying", "becoming a bird", 13.30, 15.30,
                    Weekday.Monday, 10, Month.March, 2023);
            Event event2 = new Event("painting", "painting lessons", 9.00, 11.30,
                    Weekday.Wednesday, 12, Month.March, 2023);
            Event event3 = new Event("gym", "exercising for health", 14.30, 15.00,
                    Weekday.Wednesday, 12, Month.March, 2023);
            schedule.addEvent(event1);
            schedule.addEvent(event2);
            schedule.addEvent(event3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(schedule);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            schedule = reader.read();
            assertEquals(2, schedule.getWeekSchedule().size());
            assertTrue(schedule.getWeekSchedule().containsKey(Weekday.Monday));
            assertTrue(schedule.getWeekSchedule().containsKey(Weekday.Wednesday));

            assertEquals(1, schedule.getWeekSchedule().get(Weekday.Monday).size());
            assertEquals("flying", schedule.getWeekSchedule().get(Weekday.Monday).get(13.30).getEventName());
            HashMap<Double, Event> wednesday = schedule.getWeekSchedule().get(Weekday.Wednesday);
            assertEquals(2, wednesday.size());
            assertEquals("painting", wednesday.get(9.00).getEventName());
            assertEquals("gym", wednesday.get(14.30).getEventName());


        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
