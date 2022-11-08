package persistence;

import model.Route;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads a Route from JSON data stored in file
// citation: code structure based on example provided on edX
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        Journal j = new Journal();
        addClimbed(j, jsonObject);
        addToClimb(j, jsonObject);
        addInProgress(j, jsonObject);
        return j;
    }

    // MODIFIES: j
    // EFFECTS: parses climbed routes from JSON object and adds them to climbedRoutes section of journal
    private void addClimbed(Journal j, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("climbed");
        for (Object json : jsonArray) {
            JSONObject nextRoute = (JSONObject) json;
            addRoute(j, "climbed", nextRoute);
        }
    }

    // MODIFIES: j
    // EFFECTS: parses toClimb routes from JSON object and adds them to toClimb section of journal
    private void addToClimb(Journal j, JSONObject jsonObject) {
        JSONArray toClimb = jsonObject.getJSONArray("toClimb");
        for (Object json : toClimb) {
            JSONObject nextRoute = (JSONObject) json;
            addRoute(j, "toClimb", nextRoute);
        }
    }

    // MODIFIES: j
    // EFFECTS: parses inProgress routes from JSON object and adds them to inProgress section of journal
    private void addInProgress(Journal j, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inProgress");
        for (Object json : jsonArray) {
            JSONObject nextRoute = (JSONObject) json;
            addRoute(j, "inProgress", nextRoute);
        }
    }

    // MODIFIES: j
    // EFFECTS: parses Route from JSON object and adds it to Journal in appropriate list
    private void addRoute(Journal j, String listToAddTo, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String rating = jsonObject.getString("rating");
        String climbType = jsonObject.getString("climbType");
        String latitude = jsonObject.getString("latitude");
        String longitude = jsonObject.getString("longitude");
        String notes = jsonObject.getString("notes");
        Route r = new Route();
        r.buildRoute(name, rating, climbType, latitude, longitude, notes);
        j.addToList(listToAddTo, r);
    }
}
