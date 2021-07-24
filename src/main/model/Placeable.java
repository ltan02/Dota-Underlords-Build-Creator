package model;

// Represents a placeable object on a board
public class Placeable {

    protected String name;

    protected int row;
    protected int column;

    // EFFECTS: constructs a placeable object with a name, row, and column
    public Placeable(String name, int row, int column) {
        // stub
    }

    // EFFECTS: returns the name
    public String getName() {
        return ""; // stub
    }

    // EFFECTS: returns the row
    public int getRow() {
        return 0; // stub
    }

    // EFFECTS: returns the col
    public int getColumn() {
        return 0; // stub
    }

    // REQUIRES: 0 <= newRow <= 3 and 0 <= newCol <= 7 and no Placeable on the new spot
    // MODIFIES: this
    // EFFECTS: returns true if the piece moved to the new spot, otherwise return false
    public void move(int newRow, int newCol) {
        // stub
    }

}
