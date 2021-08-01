package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a Dota hero that can be placed on a board
public class Hero extends Placeable {

    private String ability;
    private String passive;
    private int tier; // 1 <= tier <= 5

    private List<String> alliances;

    // EFFECTS: creates a new Hero
    public Hero(String name, int row, int column, String ability, String passive, int tier,
                List<String> alliances) {
        super(name, row, column);
        this.ability = ability;
        this.passive = passive;
        this.tier = tier;
        this.alliances = alliances;
    }

    // EFFECTS: returns the alliances of the hero
    public List<String> getAlliances() {
        return this.alliances;
    }

    // EFFECTS: returns the ability of the hero
    public String getAbility() {
        return this.ability;
    }

    // EFFECTS: returns the passive of the hero
    public String getPassive() {
        return this.passive;
    }

    // EFFECTS: returns the tier of the hero
    public int getTier() {
        return this.tier;
    }

    // EFFECTS: returns the fields and type of Hero as a jsonObject
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        if (this.ability == null) {
            jsonObject.put("ability", this.ability);
        } else {
            jsonObject.put("ability", "");
        }
        if (this.passive == null) {
            jsonObject.put("passive", "");
        } else {
            jsonObject.put("passive", this.passive);
        }
        jsonObject.put("tier", this.tier);
        jsonObject.put("alliances", alliancesToJson());
        jsonObject.put("type", "Hero");
        return jsonObject;
    }

    // EFFECTS: returns the alliances to jsonArray
    private JSONArray alliancesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String alliance : this.alliances) {
            jsonArray.put(alliance);
        }
        return jsonArray;
    }
}
