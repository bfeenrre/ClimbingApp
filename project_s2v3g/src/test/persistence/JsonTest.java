package persistence;

import model.Journal;
import model.Route;

import static org.junit.jupiter.api.Assertions.assertEquals;

// represents a route tester that will fail if the route provided to it doesn't match the criteria provided
public class JsonTest {

    protected void checkRoute(Route r, String name, String rating, String climbType, String location, String notes) {
        assertEquals(name, r.getName());
        assertEquals(rating, r.getRating());
        assertEquals(climbType, r.getClimbType());
        assertEquals(location, r.getLocation());
        assertEquals(notes, r.getNotes());
    }
}