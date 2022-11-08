package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

public class RouteTest {
    private Route testRoute;

    @BeforeEach
    void beforeEach(){
        testRoute = new Route();
    }

    @Test
    void testConstructorAndSimpleGettersSetters() {
        // tests Constructor
        assertEquals("Unnamed Route", testRoute.getName());
        assertNull(testRoute.getRating());
        assertNull(testRoute.getClimbType());
        assertEquals("null, null", testRoute.getLocation());
        assertEquals("No Notes", testRoute.getNotes());
        // set new values and check
        testRoute.setName("Name");
        assertEquals("Name", testRoute.getName());
        testRoute.setNotes("new note");
        assertEquals("new note", testRoute.getNotes());
        testRoute.setClimbType("TRADITIONAL");
        testRoute.setRating("V5");
        assertEquals("V5", testRoute.getRating());
    }

    @Test
    void testBuildRoute() {
        testRoute.buildRoute("Silence", "5.15d", "SPORT","64.4906030", "10.8185290",
                "if Adam Ondra can do it, why can't I?");
            assertEquals("Silence", testRoute.getName());
            assertEquals("5.15d", testRoute.getRating());
            assertEquals("SPORT", testRoute.getClimbType());
            assertEquals("64.4906030, 10.8185290", testRoute.getLocation());
            assertEquals("if Adam Ondra can do it, why can't I?", testRoute.getNotes());
    }

    @Test
    void testGetLocation() {
        testRoute.setLatitude("26.9283746");
        assertEquals("26.9283746, null", testRoute.getLocation());
    }

    @Test
    void testSetClimbType() {
        assertFalse(testRoute.setClimbType("trad"));
        assertTrue(testRoute.setClimbType("BOULDERING"));
        assertEquals("BOULDERING", testRoute.getClimbType());
    }

    @Test
    void testGetLatValue() {
        testRoute.setLatitude("80.1928304");
        assertEquals(80.1928304, testRoute.getLatitudeValue());
    }

    @Test
    void testGetLongitudeValue() {
        testRoute.setLongitude("112.3746021");
        assertEquals(112.3746021, testRoute.getLongitudeValue());
        testRoute.setLongitude("80.1928304");
        assertEquals(80.1928304, testRoute.getLongitudeValue());
    }

    @Test
    void testSetLat() {
        // boundaries
        assertTrue(testRoute.setLatitude("90.0000000"));
        assertTrue(testRoute.setLatitude("-90.0000000"));
        assertFalse(testRoute.setLatitude("-90.0000001"));
        assertFalse(testRoute.setLatitude("90.0000001"));
        assertTrue(testRoute.setLatitude("89.9999999"));
        assertTrue(testRoute.setLatitude("-89.9999999"));
        // both false cases
        assertFalse(testRoute.setLatitude("70"));
        assertFalse(testRoute.setLatitude("-75"));
        assertFalse(testRoute.setLatitude("91.0000000"));
        assertFalse(testRoute.setLatitude("-91.0000000"));
        // true cases + make sure value actually assigned
        assertTrue(testRoute.setLatitude("84.3402867"));
        assertEquals(84.3402867, testRoute.getLatitudeValue());
        assertTrue(testRoute.setLatitude("-25.7398361"));
        assertEquals(-25.7398361, testRoute.getLatitudeValue());
    }

    @Test
    void testSetLong() {
        // boundaries
        assertTrue(testRoute.setLongitude("180.0000000"));
        assertTrue(testRoute.setLongitude("-180.0000000"));
        assertFalse(testRoute.setLongitude("-180.0000001"));
        assertFalse(testRoute.setLongitude("180.0000001"));
        assertTrue(testRoute.setLongitude("179.9999999"));
        assertTrue(testRoute.setLongitude("-179.9999999"));
        // both false cases
        assertFalse(testRoute.setLongitude("70"));
        assertFalse(testRoute.setLongitude("-75"));
        assertFalse(testRoute.setLongitude("181.0000000"));
        assertFalse(testRoute.setLongitude("-181.0000000"));
        // true cases + make sure value actually assigned
        assertTrue(testRoute.setLongitude("84.3402867"));
        assertEquals(84.3402867, testRoute.getLongitudeValue());
        assertTrue(testRoute.setLongitude("-25.7398361"));
        assertEquals(-25.7398361, testRoute.getLongitudeValue());
    }

    @Test
    void testToJson(){
        testRoute.buildRoute("pine canyon", "5.14a", "TOP ROPE", "-43.3726817" ,
                "126.4592715", "need to work on finger strength before coming back to this route - " +
                        "the crux is very crimp heavy");
        JSONObject route = testRoute.toJson();
        assertEquals(testRoute.getName(), route.getString("name"));
        assertEquals(testRoute.getRating(), route.getString("rating"));
        assertEquals(testRoute.getClimbType(), route.getString("climbType"));
        assertEquals(Double.toString(testRoute.getLatitudeValue()), route.getString("latitude"));
        assertEquals(Double.toString(testRoute.getLongitudeValue()), route.getString("longitude"));
        assertEquals(testRoute.getNotes(), route.getString("notes"));
    }
}
