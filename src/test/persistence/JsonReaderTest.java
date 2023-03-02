package persistence;

import model.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numThingies());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
