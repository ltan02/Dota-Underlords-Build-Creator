package model;

// Represents a placeable object on a board
public class Placeable {

    protected String name;
    protected int row;
    protected int column;
    protected Board bd;

    // EFFECTS: constructs a placeable object with a name, row, and column
    public Placeable(String name, int row, int column, Board bd) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.bd = bd;
    }

    // EFFECTS: returns the name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the row
    public int getRow() {
        return this.row;
    }

    // EFFECTS: returns the col
    public int getColumn() {
        return this.column;
    }

    // REQUIRES: 0 <= newRow <= 3 and 0 <= newCol <= 7 and no Placeable on the new spot
    // MODIFIES: this
    // EFFECTS: moves the placeable object to a new tile with given row and column
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.column = newCol;
    }

}
