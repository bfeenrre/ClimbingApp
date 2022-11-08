package persistence;

import model.Route;
import model.Journal;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Journal j = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal j = reader.read();
            int contents = j.getClimbedRoutes().size() + j.getRoutesToClimb().size() + j.getRoutesInProgress().size();
            assertEquals(0, contents);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournalBasic() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal j = reader.read();
            ArrayList<Route> climbedRoutes = j.getClimbedRoutes();
            ArrayList<Route> toClimbRoutes = j.getRoutesToClimb();
            ArrayList<Route> inProgressRoutes = j.getRoutesInProgress();
            assertEquals(1, climbedRoutes.size());
            assertEquals(2, toClimbRoutes.size());
            assertEquals(0, inProgressRoutes.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournalRouteCharacteristics() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal j = reader.read();
            Route r1 = j.getClimbedRoutes().get(0);
            Route r2 = j.getRoutesToClimb().get(1);
            Route r3 = j.getRoutesToClimb().get(0);
            checkRoute(r1, "needle", "V6", "TRADITIONAL", "85.3726817, -28.4592715",
                    "none");
            checkRoute(r2, "Silence", "5.15d", "SPORT","64.4906030, 10.8185290",
                    "if Adam Ondra can do it, why can't I?");
            checkRoute(r3, "pine canyon", "5.14a", "TOP ROPE", "-43.3726817, 126.4592715",
                    "need to work on finger strength before coming back to this route - the crux is very crimp heavy");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testParseListOfJournal(){}
}