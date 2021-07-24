package model;

import java.util.ArrayList;
import java.util.List;

// Represents a Dota Underlords board
public class Board {

    public static final int MAX_HEROES = 10;
    public static final int MAX_ITEMS = 3;
    public static final int MAX_COLUMNS = 7; // uses 0-indexing
    public static final int MAX_ROWS = 3; // uses 0-indexing

    private Placeable[][] tiles;
    private List<Hero> heroes;
    private List<Item> items;

    // EFFECTS: creates a Board object with empty tiles
    public Board() {
        // stub
    }

    // REQUIRES: 0 <= row <= 3 and 0 <= column <= 7
    // EFFECTS: returns the unit at the given row and column
    public Placeable getUnit(int row, int column) {
        return new Placeable("", 0, 0); // stub
    }

    // REQUIRES: 0 <= row <= 3 and 0 <= column <= 7 and there is a unit at that row and column
    // MODIFIES: this
    // EFFECTS: returns and removes the unit at the given row and column
    public Placeable removeUnit(int row, int column) {
        return new Placeable("", 0, 0); // stub
    }

    // REQUIRES: unit is not null
    // MODIFIES: this
    // EFFECTS: returns true if it adds the unit to the board at the given row and column, otherwise returns false
    //          if the tile already has a unit or the unit is already on the board
    public boolean addUnit(Placeable unit) {
        return false; // stub
    }

    // EFFECTS: returns a list of the alliances on the board
    public List<String> getAlliances() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns true if the board has 10 heroes, otherwise return false
    public boolean isFullHeroes() {
        return false; // stub
    }

    // EFFECTS: returns true if the board has 3 items, otherwise return false
    public boolean isFullItems() {
        return false; // stub
    }

    // EFFECTS: returns all the tiles on the board
    public Placeable[][] getTiles() {
        return new Placeable[1][1]; // stub
    }

    // EFFECTS: returns the heroes on the board
    public List<Hero> getHeroes() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns the items on the board
    public List<Item> getItems() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns a String version of the tiles on the board
    @Override
    public String toString() {
        return ""; // stub
    }

}
