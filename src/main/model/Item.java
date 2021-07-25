package model;

// Represents an item that can be placed on a board
public class Item extends Placeable {

    // EFFECTS: creates a new Item
    public Item(String name, int row, int column, Board bd) {
        super(name, row, column, bd);
    }


}
