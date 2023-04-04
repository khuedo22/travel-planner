package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Month.May;
import static model.Weekday.Monday;
import static model.Weekday.Tuesday;
import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {
    private Schedule scheduleTest;
    @BeforeEach
    public void setup() {
        scheduleTest = new Schedule();
        EventX event1 = new EventX("Restaurant", "visiting a restaurant",
                9.30, 11.30, Monday, 2, May, 2023);
        EventX event2 = new EventX("Zoo", "going to the zoo",
                13.30, 14.30, Tuesday, 3, May, 2023);
        EventX event3 = new EventX("Eiffel tower", "climbing the Eiffel tower",
                11.30, 14, Monday, 2, May, 2023);
        EventX event4 = new EventX("beach", "relaxing at the beach",
                17, 19, Monday, 2, May, 2023);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, scheduleTest.getWeekSchedule().size());

    }

    @Test
    public void addEventToEmptyWeekTest() {
        EventX event1 = new EventX("Restaurant", "visiting a restaurant",
                9.30, 11.30, Monday, 2, May, 2023);
        scheduleTest.addEvent(event1);
        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertEquals(1, scheduleTest.getWeekSchedule().get(Monday).size());
        assertEquals("Restaurant", scheduleTest.getWeekSchedule().get(Monday).get(9.30).getEventName());
    }

    @Test
    public void addTwoEventsOnDifferentDaysTest() {
        EventX event1 = new EventX("Restaurant", "visiting a restaurant",
                9.30, 11.30, Monday, 2, May, 2023);
        scheduleTest.addEvent(event1);
        EventX event2 = new EventX("Zoo", "going to the zoo",
                13.30, 14.30, Tuesday, 3, May, 2023);
        scheduleTest.addEvent(event2);
        assertEquals(2, scheduleTest.getWeekSchedule().size());
        assertEquals(1, scheduleTest.getWeekSchedule().get(Tuesday).size());
        assertEquals(1, scheduleTest.getWeekSchedule().get(Monday).size());
        assertEquals("Zoo", scheduleTest.getWeekSchedule().get(Tuesday).get(13.30).getEventName());
        assertEquals("Restaurant", scheduleTest.getWeekSchedule().get(Monday).get(9.30).getEventName());
    }
    @Test
    public void addTwoEventsOnSameDayTest() {
        EventX event3 = new EventX("Eiffel tower", "climbing the Eiffel tower",
                11.30, 14, Monday, 2, May, 2023);
        EventX event1 = new EventX("Restaurant", "visiting a restaurant",
                9.30, 11.30, Monday, 2, May, 2023);
        scheduleTest.addEvent(event3);
        scheduleTest.addEvent(event1);
        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertEquals(2, scheduleTest.getWeekSchedule().get(Monday).size());
        assertEquals("Eiffel tower", scheduleTest.getWeekSchedule().get(Monday).get(11.30).getEventName());
        assertEquals("Restaurant", scheduleTest.getWeekSchedule().get(Monday).get(9.30).getEventName());
    }

    @Test
    public void removeOnlyEventInScheduleTest() {
        EventX event1 = new EventX("Restaurant", "visiting a restaurant",
                9.30, 11.30, Monday, 2, May, 2023);
        scheduleTest.addEvent(event1);
        scheduleTest.removeEvent(event1);
        assertFalse(scheduleTest.getWeekSchedule().containsKey(Monday));
        assertTrue(scheduleTest.getWeekSchedule().isEmpty());
    }

    @Test
    public void removeOneEventDifferentDaysScheduleTest() {
        EventX event2 = new EventX("Zoo", "going to the zoo",
                13.30, 14.30, Tuesday, 3, May, 2023);
        EventX event3 = new EventX("Eiffel tower", "climbing the Eiffel tower",
                11.30, 14, Monday, 2, May, 2023);

        scheduleTest.addEvent(event2);
        scheduleTest.addEvent(event3);
        scheduleTest.removeEvent(event2);
        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertFalse(scheduleTest.getWeekSchedule().containsKey(Tuesday));
        assertTrue(scheduleTest.getWeekSchedule().containsKey(Monday));
    }

    @Test
    public void removeOneEventSameDayScheduleTest() {
        EventX event4 = new EventX("beach", "relaxing at the beach",
                17.00, 19.00, Monday, 2, May, 2023);
        EventX event3 = new EventX("Eiffel tower", "climbing the Eiffel tower",
                11.30, 14.00, Monday, 2, May, 2023);
        scheduleTest.addEvent(event4);
        scheduleTest.addEvent(event3);
        scheduleTest.removeEvent(event3);

        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertTrue(scheduleTest.getWeekSchedule().containsKey(Monday));
        assertEquals(1, scheduleTest.getWeekSchedule().get(Monday).size());
        assertFalse(scheduleTest.getWeekSchedule().get(Monday).containsKey(11.30));
        assertTrue(scheduleTest.getWeekSchedule().get(Monday).containsKey(17.00));
    }

    @Test
    public void getEventTest() {
        EventX event4 = new EventX("beach", "relaxing at the beach",
                17.00, 19, Monday, 2, May, 2023);
        scheduleTest.addEvent(event4);
        EventX event = scheduleTest.getEvent(Monday,17.00);
        assertEquals("beach", event.getEventName());
        assertEquals(Monday, event.getWeekday());
        assertEquals(17.00, event.getStartTime());
    }
}
