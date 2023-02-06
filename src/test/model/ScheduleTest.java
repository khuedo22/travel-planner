package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static model.Month.May;
import static model.Weekday.Monday;
import static model.Weekday.Tuesday;
import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {
    private Schedule scheduleTest;
    @BeforeEach
    public void setup() {
        scheduleTest = new Schedule();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, scheduleTest.getWeekSchedule().size());

    }

    @Test
    public void addEventTest() {
//        Event restaurant = new Event("Restaurant", "visiting a restaurant", 9.30, Monday,
//                2, May, 2023);
       scheduleTest.addEvent("Restaurant", "visiting a restaurant", 9.30, 11.30,  Monday,
               2, May, 2023);
       assertEquals(1, scheduleTest.getWeekSchedule().size());
       assertEquals(1, scheduleTest.getWeekSchedule().get(Monday).size());

        scheduleTest.addEvent("Zoo", "going to the zoo",
                13.30, 14.30, Tuesday, 3, May, 2023);
        assertEquals(2, scheduleTest.getWeekSchedule().size());
        assertEquals(1, scheduleTest.getWeekSchedule().get(Tuesday).size());

        scheduleTest.addEvent("Eiffel tower", "climbing the Eiffel tower",
                12, 14, Monday, 2, May, 2023);
        assertEquals(2, scheduleTest.getWeekSchedule().size());
        assertEquals(2, scheduleTest.getWeekSchedule().get(Monday).size());
//        Event zoo = new Event("Zoo", "going to the zoo",
//                13.30, Tuesday, 18, Month.June, 2023);
//        scheduleTest.addEvent(zoo, Tuesday, 3, 13);
//        assertEquals(2, scheduleTest.getDaySchedule().size());


    }

    @Test
    public void removeEventTest() {
        //Event restaurant = new Event("Restaurant", "visiting a restaurant", 9.30, Monday,
          //      2, May, 2023);
        //Event zoo = new Event("Zoo", "going to the zoo",
          //      13.30, Tuesday, 18, Month.June, 2023);
        scheduleTest.addEvent("Restaurant", "visiting a restaurant", 9.30, 11.30, Monday,
                      2, May, 2023);
        scheduleTest.removeEvent(Monday, 9.30);
        assertEquals(0, scheduleTest.getWeekSchedule().size());
        assertFalse(scheduleTest.getWeekSchedule().containsKey(Monday));

        scheduleTest.addEvent("Zoo", "going to the zoo",
                13.30, 14.30, Tuesday, 3, May, 2023);
        scheduleTest.addEvent("Eiffel tower", "climbing the Eiffel tower",
                12, 13, Monday, 2, May, 2023);
        scheduleTest.removeEvent(Tuesday, 13.30);
        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertFalse(scheduleTest.getWeekSchedule().containsKey(Tuesday));

        scheduleTest.addEvent("beach", "relaxing at the beach", 17, 19, Monday, 2, May, 2023);
        scheduleTest.removeEvent(Monday, 12);
        assertEquals(1, scheduleTest.getWeekSchedule().size());
        assertTrue(scheduleTest.getWeekSchedule().containsKey(Monday));

    }
}
