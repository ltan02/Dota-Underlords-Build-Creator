package persistence;

// Methods taken from JsonSerializationDemo. Link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java

import model.Board;
import model.Hero;
import model.Item;
import model.Placeable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads board from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Board read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses board from JSON object and returns it
    private Board parseBoard(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Placeable[][] tiles = getUnits(jsonObject);
        Board b = new Board(name, tiles);
        b.setUpUnits();
        return b;
    }

    // EFFECTS: returns the board with units added in it from jsonObject
    private Placeable[][] getUnits(JSONObject jsonObject) {
        JSONArray tiles = jsonObject.getJSONArray("units");
        Placeable[][] board = new Placeable[Board.MAX_ROWS][Board.MAX_COLUMNS];
        int count = 0;
        for (Object t : tiles) {
            int row = count / Board.MAX_COLUMNS;
            int column = count % Board.MAX_COLUMNS;
            if (t.equals("null")) {
                board[row][column] = null;
            } else {
                JSONObject json = (JSONObject) t;
                board[row][column] = addUnit(json);
            }
            count++;
        }
        return board;
    }

    // EFFECTS: parses a unit from JSON object and adds it to the board
    private Placeable addUnit(JSONObject j) {
        String type = j.getString("type");
        String name = j.getString("name");
        int row = j.getInt("row");
        int column = j.getInt("column");
        if (type.equals("Hero")) {
            String ability = j.getString("ability");
            if (ability.equals("")) {
                ability = null;
            }
            String passive = j.getString("passive");
            if (passive.equals("")) {
                passive = null;
            }
            int tier = j.getInt("tier");
            JSONArray alliancesJson = j.getJSONArray("alliances");
            List<String> alliances = new ArrayList<>();
            for (Object a : alliancesJson) {
                alliances.add((String) a);
            }
            return new Hero(name, row, column, ability, passive, tier, alliances);
        } else {
            return new Item(name, row, column);
        }
    }

}
