package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a journal containing a list of climbed routes,
// a list of routes to climb, and a list of routes in progress
public class Journal implements Writable {
    private ArrayList<Route> climbedRoutes;
    private ArrayList<Route> routesToClimb;
    private ArrayList<Route> routesInProgress;

    // EFFECTS: creates a new Journal
    public Journal() {
        climbedRoutes = new ArrayList<>();
        routesToClimb = new ArrayList<>();
        routesInProgress = new ArrayList<>();
    }


    // REQUIRES: listIndex is one of "climbed", "toClimb", "inProgress"
    // MODIFIES: this
    // EFFECTS: adds the given route to the list indicated by listIndex, unless it is already in the list
    public void addToList(String listIndex, Route routeToAdd) {
        if (listIndex.equals("climbed")) {
            if (!climbedRoutes.contains(routeToAdd)) {
                climbedRoutes.add(routeToAdd);
            }
        }
        if (listIndex.equals("toClimb")) {
            if (!routesToClimb.contains(routeToAdd)) {
                routesToClimb.add(routeToAdd);
            }
        }
        if (listIndex.equals("inProgress")) {
            if (!routesInProgress.contains(routeToAdd)) {
                routesInProgress.add(routeToAdd);
            }
        }
    }

    // REQUIRES: listIndex is one of "climbed", "toClimb", "inProgress"
    // MODIFIES: this
    // EFFECTS: adds the given route to the list indicated by listIndex, unless it is already in the list
    public void addToListWhileRunning(String listIndex, Route routeToAdd) {
        addToList(listIndex, routeToAdd);
        EventLog.getInstance().logEvent(new Event("Route (name: " + routeToAdd.getName()
                + ") added to Journal (listIndex: " + listIndex + ")"));
    }

    // REQUIRES: listIndex is one of "climbed", "toClimb", "inProgress"
    // MODIFIES: this
    // EFFECTS: removes the given route from the list indicated by listIndex if it is in the list
    public void removeFromList(String listIndex, Route routeToRemove) {
        if (listIndex.equals("climbed")) {
            climbedRoutes.remove(routeToRemove);
        }
        if (listIndex.equals("toClimb")) {
            routesToClimb.remove(routeToRemove);
        }
        if (listIndex.equals("inProgress")) {
            routesInProgress.remove(routeToRemove);
        }
        EventLog.getInstance().logEvent(new Event("Route (name: " + routeToRemove.getName()
                + ") removed from Journal (listIndex: " + listIndex + ")"));
    }

    // REQUIRES: routes in all lists do not have null fields
    // EFFECTS: returns the route that matches the description provided by the inputs for this function, if one exists,
    //          else returns null
    public Route findByNameRatingAndList(String list, String name, String rating, String type) {
        ArrayList<Route> listToSearch = new ArrayList<>();
        if (list.equals("climbed")) {
            listToSearch = climbedRoutes;
        }
        if (list.equals("toClimb")) {
            listToSearch = routesToClimb;
        }
        if (list.equals("inProgress")) {
            listToSearch = routesInProgress;
        }
        for (Route r: listToSearch) {
            if (r.getName().equals(name) && r.getRating().equals(rating) && r.getClimbType().equals(type)) {
                return r;
            }
        }
        return null;
    }

    // EFFECTS: returns climbedRoutes
    public ArrayList<Route> getClimbedRoutes() {
        return climbedRoutes;
    }

    // MODIFIES: this
    // EFFECTS: sets this.climbedRoutes to climbedRoutes
    public void setClimbedRoutes(ArrayList<Route> climbedRoutes) {
        this.climbedRoutes = climbedRoutes;
    }

    // EFFECTS: returns routesToClimb
    public ArrayList<Route> getRoutesToClimb() {
        return routesToClimb;
    }

    // MODIFIES: this
    // EFFECTS: sets this.routesToClimb to routesToClimb
    public void setRoutesToClimb(ArrayList<Route> routesToClimb) {
        this.routesToClimb = routesToClimb;
    }

    // EFFECTS: returns routesInProgress
    public ArrayList<Route> getRoutesInProgress() {
        return routesInProgress;
    }

    // MODIFIES: this
    // EFFECTS: sets this.routesInProgress to routesInProgress
    public void setRoutesInProgress(ArrayList<Route> routesInProgress) {
        this.routesInProgress = routesInProgress;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("climbed", climbedToJson());
        json.put("toClimb", toClimbToJson());
        json.put("inProgress", inProgressToJson());
        return json;
    }

    // EFFECTS: returns Routes in Journal's climbedRoutes as a JSON array
    private JSONArray climbedToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Route r : climbedRoutes) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns Routes in Journal's routesToClimb as a JSON array
    private JSONArray toClimbToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Route r : routesToClimb) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns Routes in Journal's climbedRoutes as a JSON array
    private JSONArray inProgressToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Route r : routesInProgress) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
