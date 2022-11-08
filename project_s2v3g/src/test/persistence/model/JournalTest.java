package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JournalTest {
    private Journal testJournal;
    private ArrayList<Route> testList;
    private ArrayList<Route> keyList1;
    private ArrayList<Route> keyList2;
    private final Route testRoute1 = new Route();
    private final Route testRoute2 = new Route();
    private final Route testRoute3 = new Route();

    @BeforeEach
    void beforeEach() {
        testJournal = new Journal();
        testList = new ArrayList<>();
        keyList1 = new ArrayList<>();
        keyList2 = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testJournal.getClimbedRoutes().size());
        assertEquals(0, testJournal.getRoutesToClimb().size());
        assertEquals(0, testJournal.getRoutesInProgress().size());
    }

    @Test
    void testSetClimbedRoutes(){
        testList.add(testRoute1);
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);

        testJournal.setClimbedRoutes(testList);
        assertEquals(keyList1, testJournal.getClimbedRoutes());
        testList.add(testRoute2);
        testJournal.setClimbedRoutes(testList);
        assertEquals(keyList2, testJournal.getClimbedRoutes());
    }

    @Test
    void testSetRoutesToClimb(){
        testList.add(testRoute1);
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);

        testJournal.setRoutesToClimb(testList);
        assertEquals(keyList1, testJournal.getRoutesToClimb());
        testList.add(testRoute2);
        testJournal.setRoutesToClimb(testList);
        assertEquals(keyList2, testJournal.getRoutesToClimb());
    }

    @Test
    void testSetRoutesInProgress(){
        testList.add(testRoute1);
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);

        testJournal.setRoutesInProgress(testList);
        assertEquals(keyList1, testJournal.getRoutesInProgress());
        testList.add(testRoute2);
        testJournal.setRoutesInProgress(testList);
        assertEquals(keyList2, testJournal.getRoutesInProgress());
    }

    @Test
    void testAddToClimbedRoutesList() {
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);
        // tests basic functionality
        testJournal.addToList("climbed", testRoute1);
        assertEquals(keyList1, testJournal.getClimbedRoutes());
        // tests case where routeToAdd is already in the list
        testJournal.addToList("climbed", testRoute1);
        assertEquals(keyList1, testJournal.getClimbedRoutes());
        // tests iteration
        testJournal.addToList("climbed", testRoute2);
        assertEquals(keyList2, testJournal.getClimbedRoutes());
    }

    @Test
    void testAddToRoutesToClimbList(){
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);
        // tests basic functionality
        testJournal.addToList("toClimb", testRoute1);
        assertEquals(keyList1, testJournal.getRoutesToClimb());
        // tests case where routeToAdd is already in the list
        testJournal.addToList("toClimb", testRoute1);
        assertEquals(keyList1, testJournal.getRoutesToClimb());
        // tests iteration
        testJournal.addToList("toClimb", testRoute2);
        assertEquals(keyList2, testJournal.getRoutesToClimb());
    }

    @Test
    void testAddToRoutesInProgressList(){
        keyList1.add(testRoute1);
        keyList2.add(testRoute1);
        keyList2.add(testRoute2);
        // tests basic functionality
        testJournal.addToList("inProgress", testRoute1);
        assertEquals(keyList1, testJournal.getRoutesInProgress());
        // tests case where routeToAdd is already in the list
        testJournal.addToList("inProgress", testRoute1);
        assertEquals(keyList1, testJournal.getRoutesInProgress());
        // tests iteration
        testJournal.addToList("inProgress", testRoute2);
        assertEquals(keyList2, testJournal.getRoutesInProgress());
    }

    @Test
    void testRemoveFromClimbedRoutesList() {
        testList.add(testRoute1);
        testList.add(testRoute2);
        testJournal.setClimbedRoutes(testList);
        keyList1.add(testRoute1);
        // tests basic functionality
        testJournal.removeFromList("climbed", testRoute2);
        assertEquals(keyList1, testJournal.getClimbedRoutes());
        // tests case where routeToRemove is not in list
        testJournal.removeFromList("climbed", testRoute2);
        assertEquals(keyList1, testJournal.getClimbedRoutes());
        // tests iteration
        testJournal.removeFromList("climbed", testRoute1);
        assertEquals(keyList2, testJournal.getClimbedRoutes());
    }

    @Test
    void testRemoveFromRoutesToClimbList() {
        testList.add(testRoute1);
        testList.add(testRoute2);
        testJournal.setRoutesToClimb(testList);
        keyList1.add(testRoute1);
        // tests basic functionality and iteration (remove 2 routes)
        testJournal.removeFromList("toClimb", testRoute2);
        assertEquals(keyList1, testJournal.getRoutesToClimb());
        // tests case where route to remove is not in list
        testJournal.removeFromList("toClimb", testRoute2);
        assertEquals(keyList1, testJournal.getRoutesToClimb());
        // tests iteration
        testJournal.removeFromList("toClimb", testRoute1);
        assertEquals(keyList2, testJournal.getRoutesToClimb());
    }

    @Test
    void testRemoveFromRoutesInProgressList() {
        testList.add(testRoute1);
        testList.add(testRoute2);
        testJournal.setRoutesInProgress(testList);
        keyList1.add(testRoute1);
        // tests basic functionality and iteration (remove 2 routes)
        testJournal.removeFromList("inProgress", testRoute2);
        assertEquals(keyList1, testJournal.getRoutesInProgress());
        // tests case where route to remove is not in list
        testJournal.removeFromList("inProgress", testRoute2);
        assertEquals(keyList1, testJournal.getRoutesInProgress());
        // tests iteration
        testJournal.removeFromList("inProgress", testRoute1);
        assertEquals(keyList2, testJournal.getRoutesInProgress());
    }

    @Test
    void testToJson(){
        testRoute1.buildRoute("needle", "V6", "TRADITIONAL", "85.3726817", "-28.4592715",
                "none");
        testRoute2.buildRoute("pine canyon", "5.14a", "TOP ROPE", "-43.3726817" ,
                "126.4592715", "need to work on finger strength before coming back to this route - " +
                        "the crux is very crimp heavy");
        testJournal.addToList("climbed", testRoute1);
        testJournal.addToList("inProgress", testRoute2);
        testJournal.addToList("toClimb", testRoute3);
        JSONObject journal = testJournal.toJson();
        assertEquals(1, journal.getJSONArray("climbed").length());
        assertEquals(1, journal.getJSONArray("toClimb").length());
        assertEquals(1, journal.getJSONArray("inProgress").length());
    }

    @Test
    void testFindByNameRatingType() {
        testRoute1.buildRoute("needle", "V6", "TRADITIONAL", "85.3726817",
                "-28.4592715", "none");
        testRoute2.buildRoute("pine canyon", "5.14a", "TOP ROPE", "-43.3726817" ,
                "126.4592715", "need to work on finger strength before coming back to this route - " +
                        "the crux is very crimp heavy");
        testRoute3.buildRoute("funName", "V9", "SPORT", "67.4758375", "67.4738271",
                "");

        testJournal.addToList("climbed", testRoute1);
        testJournal.addToList("inProgress", testRoute2);
        testJournal.addToList("toClimb", testRoute3);

        // name , rating, type => route
        assertEquals(testRoute1, testJournal.findByNameRatingAndList("climbed", "needle", "V6",
                "TRADITIONAL"));
        // name, rating, !type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "needle", "V6",
                "BOULDERING"));
        // name !rating, type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "needle", "5.15a",
                "TRADITIONAL"));
        // name, !rating, !type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "needle", "V9",
                "TOP ROPE"));
        // !name , rating, type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "need all", "V6",
                "TRADITIONAL"));
        // !name, rating, !type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "chimney", "V6",
                "BOULDERING"));
        // !name !rating, type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "slide whistle sound effect", "V9",
                "TRADITIONAL"));
        // !name, !rating, !type => null
        assertNull(testJournal.findByNameRatingAndList("climbed", "going all out", "(0_0)",
                "unknown...??"));

        // testing other list index if statements
        assertNull(testJournal.findByNameRatingAndList("toClimb", "pine canyon",
                "5.14a", "TOP ROPE"));
        assertNull(testJournal.findByNameRatingAndList("inProgress", "pine canyon",
                "5.14b", "TOP ROPE"));
    }
}