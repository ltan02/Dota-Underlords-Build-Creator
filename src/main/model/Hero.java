package model;

import java.util.ArrayList;
import java.util.List;

// Represents a Dota hero that can be placed on a board
public class Hero extends Placeable {

    private String ability;
    private String passive;
    private int tier; // 1 <= tier <= 5

    private List<String> alliances;

    // EFFECTS: creates a new Hero
    public Hero(String name, int row, int column, String ability, String passive, int tier, List<String> alliances) {
        super(name, row, column); // stub
    }

    // EFFECTS: returns the alliances of the hero
    public List<String> getAlliances() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns the ability of the hero
    public String getAbility() {
        return ""; // stub
    }

    // EFFECTS: returns the passive of the hero
    public String getPassive() {
        return ""; // stub
    }

    // EFFECTS: returns the tier of the hero
    public int getTier() {
        return 0; // stub
    }

}
