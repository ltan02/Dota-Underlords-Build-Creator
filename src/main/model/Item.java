package model;

import org.json.JSONObject;

// Represents an item that can be placed on a board
public class Item extends Placeable {

    // EFFECTS: creates a new Item
    public Item(String name, int row, int column) {
        super(name, row, column);
    }

    // EFFECTS: returns the fields and type of Item as jsonObject
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("type", "Item");
        return jsonObject;
    }

}
