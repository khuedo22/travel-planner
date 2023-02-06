package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {
    // delete or rename this class!
    private Event event;
    @BeforeEach

    public void runBefore() {
        event = new Event("Zoo", "going to the zoo",
                13.30, 17, Weekday.Monday, 18, Month.May, 2023);
    }

    @Test
    public void testEvent() {
        assertEquals("Zoo", event.getEventName());
        assertEquals("going to the zoo", event.getDescription());
        assertEquals(13.3, event.getStartTime());
        assertEquals(Weekday.Monday, event.getWeekday());
        assertEquals(18, event.getDay());
        assertEquals(Month.May, event.getMonth());
        assertEquals(2023, event.getYear());
        assertEquals("no info", event.getTransportationInfo());
        assertEquals("no info", event.getDirections());
        assertEquals(0, event.getPrice());
        assertEquals("no info", event.getAdditionalNotes());
    }

    @Test
    public void testAddAdditionalInfo() {
        event.addAdditionalInfo("subway", "get off at X station, turn left", 5);
        assertEquals("subway", event.getTransportationInfo());
        assertEquals("get off at X station, turn left", event.getDirections());
        assertEquals(5, event.getPrice());

    }

    @Test
    public void testGetEventInfo() {
        assertEquals("Zoo\nDate: Monday May 18 2023\nTime: 13.3\nDescription: going to the zoo" +
                "\nTransportation: no info\nDirections: no info\nPrice: 0.0", event.getEventInfo());
    }

    @Test
    public void testGetDate() {
        assertEquals(Weekday.Monday, event.getWeekday());
        assertEquals(18, event.getDay());
        assertEquals(Month.May, event.getMonth());
        assertEquals(2023, event.getYear());
        assertEquals("Monday May 18 2023", event.getDate());
    }

    @Test
    public void testEditEventName() {
        event.editEventName("park");
        assertEquals("park", event.getEventName());
    }

    @Test
    public void testEditDescription() {
        event.editDescription("walking around the park");
        assertEquals("walking around the park", event.getDescription());
    }

    @Test
    public void testEditTime() {
        event.editTime(11.55);
        assertEquals(11.55, event.getStartTime());

    }

    @Test
    public void testEditDay() {
        event.editDay(18);
        assertEquals(18, event.getDay());
    }

    @Test
    public void testEditWeekday() {
        event.editWeekday(Weekday.Friday);
        assertEquals(Weekday.Friday, event.getWeekday());
    }

    @Test
    public void testEditMonth() {
        event.editMonth(Month.July);
        assertEquals(Month.July, event.getMonth());
    }

    @Test
    public void testEditYear() {
        event.editYear(2024);
        assertEquals(2024, event.getYear());
    }

    @Test
    public void testEditTransportation() {
        event.editTransportation("car");
        assertEquals("car", event.getTransportationInfo());
    }
    @Test
    public void testEditDirection() {
        event.editDirection("drive along highway Y");
        assertEquals("drive along highway Y", event.getDirections());
    }
    @Test
    public void testEditPrice() {
        event.editPrice(50.75);
        assertEquals(50.75, event.getPrice());
    }

}