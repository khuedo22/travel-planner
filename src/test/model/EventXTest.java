package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Month.June;
import static model.Weekday.Wednesday;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventXTest {

    private EventX event;
    @BeforeEach

    public void runBefore() {
        event = new EventX("Zoo", "going to the zoo",
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
        assertEquals(0, event.getPrice());
        assertEquals("no info", event.getAdditionalNotes());
    }


    @Test
    public void testGetSomeEventInfo() {
        assertEquals("Event: Zoo\nDescription: going to the zoo" +
                "\nTransportation: no info\nPrice: 0.0\nAdditional Notes: no info", event.getSomeEventInfo());
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
    public void testEditDate() {
        event.editDate(June, Wednesday, 15, 2024);
        assertEquals(June, event.getMonth());
        assertEquals(Wednesday, event.getWeekday());
        assertEquals(15, event.getDay());
        assertEquals(2024, event.getYear());
    }

    @Test
    public void testEditTime() {
        event.editTime(14.30, 23.00);
        assertEquals(14.30, event.getStartTime());
        assertEquals(23.00, event.getEndTime());
    }


    @Test
    public void testEditTransportation() {
        event.editTransportation("car");
        assertEquals("car", event.getTransportationInfo());
    }

    @Test
    public void testEditPrice() {
        event.editPrice(50.75);
        assertEquals(50.75, event.getPrice());
    }

    @Test
    public void testEditAdditionalNotes() {
        event.editAdditionalNotes("Bring umbrella");
        assertEquals("Bring umbrella", event.getAdditionalNotes());
    }

}