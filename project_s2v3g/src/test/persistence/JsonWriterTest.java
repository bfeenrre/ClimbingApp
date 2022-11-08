package persistence;

import model.Journal;
import model.Route;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    private Journal testJournal = new Journal();
    Route r1 = new Route();
    Route r2 = new Route();
    Route r3 = new Route();
    Route r4 = new Route();

    // MODIFIES: this
    // EFFECTS: creates a realistic depiction of a general journal for testing
    private void initTestJournal() {
        r1.buildRoute("needle", "V6", "TRADITIONAL", "85.3726817", "-28.4592715",
                "none");
        r2.buildRoute("pine canyon", "5.14a", "TOP ROPE", "-43.3726817" ,
                "126.4592715", "need to work on finger strength before coming back to this route - " +
                        "the crux is very crimp heavy");
        r3.buildRoute("Silence", "5.15d", "SPORT","64.4906030", "10.8185290",
                "if Adam Ondra can do it, why can't I?");
        r4.buildRoute("yeah idk i'm out of fun names", "5.11b", "BOULDERING", "67.1526374",
                "75.3849572", ":D");
        testJournal.addToList("climbed", r1);
        testJournal.addToList("toClimb", r2);
        testJournal.addToList("inProgress", r3);
        testJournal.addToList("inProgress", r4);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Journal j = new Journal();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal j = new Journal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(j);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            j = reader.read();
            int contents = j.getClimbedRoutes().size() + j.getRoutesInProgress().size() + j.getRoutesToClimb().size();
            assertEquals(0, contents);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            initTestJournal();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(testJournal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            testJournal = reader.read();
            assertEquals(1, testJournal.getRoutesToClimb().size());
            assertEquals(2, testJournal.getRoutesInProgress().size());
            assertEquals(1, testJournal.getClimbedRoutes().size());

            r1 = testJournal.getClimbedRoutes().get(0);
            r2 = testJournal.getRoutesToClimb().get(0);
            r3 = testJournal.getRoutesInProgress().get(0);
            r4 = testJournal.getRoutesInProgress().get(1);
            checkRoute(r1, "needle", "V6", "TRADITIONAL", "85.3726817, -28.4592715",
                    "none");
            checkRoute(r2, "pine canyon", "5.14a", "TOP ROPE", "-43.3726817, 126.4592715",
                    "need to work on finger strength before coming back to this route - the crux is very crimp heavy");
            checkRoute(r3, "Silence", "5.15d", "SPORT","64.4906030, 10.8185290",
                    "if Adam Ondra can do it, why can't I?");
            checkRoute(r4, "yeah idk i'm out of fun names", "5.11b", "BOULDERING",
                    "67.1526374, 75.3849572", ":D");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}