package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a route, has fields corresponding to its name,
// its rating (either V or Yosemite), the climb type,
// a notes section, the climb's latitude and longitude, the
// status of the climb, and any user-added pictures of the climb
public class Route implements Writable {
    private static final String validClimbTypes = "/TRADITIONAL/SPORT/TOP ROPE/BOULDERING/OTHER/";

    // represents the number of decimals latitude and longitude must be precise up to
    // NOTE: I did not design my tests in a way that they will run if this value is changed, as 7 decimal places
    //       provides the correct granularity (<1 meter resolution) - no other value will or should ever be given to
    //       requiredLocationPrecision
    private static final int requiredLocationPrecision = 7;


    private String name;
    // INVARIANT: rating will be null or match the following regex:
    //            "(V((0\\+?)|([1-9]|1[0-3])))|(5.(9|1[0-4][a-d]|15))"
    private String rating;
    // INVARIANT: will be null or one of the allowedClimbTypes
    private String climbType;
    // INVARIANT: longitude and latitude will either be null or be in the correct bounds and be as precise as
    //            requiredLocationPrecision specifies
    private String latitude;
    private String longitude;
    private String notes;


    // EFFECTS: creates a new route with 2 "default" field values, and no other information
    public Route() {
        name = "Unnamed Route";
        notes = "No Notes";
        // FIX LATER
        // pictures = new ArrayList<Image>();
    }

    // REQUIRES: rating, climbType, latitude, and longitude are all valid values for their given fields
    // MODIFIES: this
    // EFFECTS: alters route to have the given name, rating, climb type, location, and notes
    public void buildRoute(String name, String rating, String climbType, String latitude, String longitude,
                            String notes) {
        setName(name);
        setRating(rating);
        setClimbType(climbType);
        setLatitude(latitude);
        setLongitude(longitude);
        setNotes(notes);
    }

    // EFFECTS: returns location in String form
    public String getLocation() {
        return (latitude + ", " + longitude);
    }

    // EFFECTS: returns value of latitude
    public double getLatitudeValue() {
        return Double.parseDouble(latitude);
    }

    // EFFECTS: returns value of longitude
    public double getLongitudeValue() {
        return Double.parseDouble(longitude);
    }

    // MODIFIES: this
    // EFFECTS: sets this.latitude to latitude as long as latitude is a valid value for latitude and return true,
    // else return false. i.e. latitude must be in [-90, 90], and of the form 12.3456789
    public boolean setLatitude(String latitude) {
        int decimalIndex = latitude.indexOf(".") + 1;
        String decimalPlaces = latitude.substring(decimalIndex);
        int locationPrecision = decimalPlaces.length();
        double lat = Double.parseDouble(latitude);
        boolean inBounds = lat <= 90 && lat >= -90;
        boolean rightLength = (locationPrecision == requiredLocationPrecision);
        if (inBounds && rightLength) {
            this.latitude = latitude;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: sets this.longitude to longitude
    public boolean setLongitude(String longitude) {
        int decimalIndex = longitude.indexOf(".") + 1;
        String decimalPlaces = longitude.substring(decimalIndex);
        int locationPrecision = decimalPlaces.length();
        double lon = Double.parseDouble(longitude);
        boolean inBounds = lon <= 180 && lon >= -180;
        boolean rightLength = (locationPrecision == requiredLocationPrecision);
        if (inBounds && rightLength) {
            this.longitude = longitude;
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns this.rating
    public String getRating() {
        return rating;
    }

    // REQUIRES: rating is valid rating (matches regex "(V((0\\+?)|([1-9]|1[0-3])))|(5.(9|1[0-4][a-d]|15))"
    // MODIFIES: this
    // EFFECTS: sets this.rating to rating if it matches the necessary regex and return true, else return false
    public void setRating(String rating) {
        this.rating = rating;
    }

    // EFFECTS: returns this.climbType
    public String getClimbType() {
        return climbType;
    }

    // REQUIRES: climbType input is one of the allowedCLimbTypes
    // MODIFIES: this
    // EFFECTS: sets this.climbType to climbType if it is one of the validClimbTypes and returns true, else return false
    public boolean setClimbType(String climbType) {
        if (validClimbTypes.contains("/" + climbType + "/")) {
            this.climbType = climbType;
            return true;
        }
        return false;
    }

    // EFFECTS: returns this.notes
    public String getNotes() {
        return notes;
    }

    // MODIFIES: this
    // EFFECTS: sets this.notes to notes
    public void setNotes(String notes) {
        this.notes = notes;
    }

    // EFFECTS: returns this.name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets this.name to name
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("rating", rating);
        json.put("climbType", climbType);
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        json.put("notes", notes);
        return json;
    }
}
