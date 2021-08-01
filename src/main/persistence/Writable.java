package persistence;

import org.json.JSONObject;

// Class taken from JsonSerializationDemo. Link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/tree/master/src/main/persistence

// Writable interface
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
