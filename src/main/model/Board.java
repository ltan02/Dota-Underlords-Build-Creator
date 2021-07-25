package model;

import exceptions.TileOccupiedException;
import exceptions.UnitAlreadyOnBoardException;

import java.util.ArrayList;
import java.util.List;

// Represents a Dota Underlords board
public class Board {

    public static final int MAX_HEROES = 10;
    public static final int MAX_ITEMS = 3;
    public static final int MAX_COLUMNS = 8;
    public static final int MAX_ROWS = 4;

    private Placeable[][] tiles;
    private List<Hero> heroes;
    private List<Item> items;

    // EFFECTS: creates a Board object with empty tiles
    public Board() {
        this.tiles = new Placeable[MAX_ROWS][MAX_COLUMNS];
        this.heroes = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    // REQUIRES: 0 <= row <= 3 and 0 <= column <= 7
    // EFFECTS: returns the unit at the given row and column
    public Placeable getUnit(int row, int column) {
        return this.tiles[row][column];
    }

    // REQUIRES: 0 <= row <= 3 and 0 <= column <= 7 and there is a hero at that row and column
    // MODIFIES: this
    // EFFECTS: returns and removes the hero at the given row and column
    public Hero removeHero(int row, int column) {
        Placeable removedUnit = this.tiles[row][column];
        Hero removedHero = null;

        for (int i = 0; i < heroes.size(); i++) {
            if (removedUnit.name.equals(heroes.get(i).getName())) {
                removedHero = heroes.get(i);
                heroes.remove(i);
            }
        }

        this.tiles[row][column] = null;
        return removedHero;
    }

    // REQUIRES: 0 <= row <= 3 and 0 <= column <= 7 and there is a item at that row and column
    // MODIFIES: this
    // EFFECTS: returns and removes the item at the given row and column
    public Item removeItem(int row, int column) {
        Placeable removedUnit = this.tiles[row][column];
        Item removedItem = null;

        for (int i = 0; i < items.size(); i++) {
            if (removedUnit.name.equals(items.get(i).getName())) {
                removedItem = items.get(i);
                items.remove(i);
            }
        }

        this.tiles[row][column] = null;
        return removedItem;
    }

    // REQUIRES: 0 <= newRow <= 3 and 0 <= newCol <= 7 and there is no unit at that newRow and newCol
    // MODIFIES: this
    // EFFECTS: moves the unit to the new location at newRow, newCol
    public void moveUnit(Placeable unit, int newRow, int newCol) {
        tiles[unit.getRow()][unit.getColumn()] = null;
        unit.move(newRow, newCol);
        tiles[newRow][newCol] = unit;
    }

    // MODIFIES: this
    // EFFECTS: adds the hero to the board at the given row and column
    public void addHero(Hero hero) throws TileOccupiedException, UnitAlreadyOnBoardException {
        if (this.tiles[hero.getRow()][hero.getColumn()] != null) {
            throw new TileOccupiedException();
        } else if (!inHeroes(hero)) {
            throw new UnitAlreadyOnBoardException();
        } else {
            this.tiles[hero.getRow()][hero.getColumn()] = hero;
            this.heroes.add(hero);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the item to the board at the given row and column
    public void addItem(Item item) throws TileOccupiedException, UnitAlreadyOnBoardException {
        if (this.tiles[item.getRow()][item.getColumn()] != null) {
            throw new TileOccupiedException();
        } else if (!inItems(item)) {
            throw new UnitAlreadyOnBoardException();
        } else {
            this.tiles[item.getRow()][item.getColumn()] = item;
            this.items.add(item);
        }
    }

    // EFFECTS: returns a list of the alliances on the board
    public List<String> getAlliances() {
        List<String> allAlliances = new ArrayList<>();
        for (Hero h : this.heroes) {
            List<String> heroAlliances = h.getAlliances();
            for (String alliance : heroAlliances) {
                if (!allAlliances.contains(alliance)) {
                    allAlliances.add(alliance);
                }
            }
        }
        return allAlliances;
    }

    // EFFECTS: returns true if the board has 10 heroes, otherwise return false
    public boolean isFullHeroes() {
        return this.heroes.size() == MAX_HEROES;
    }

    // EFFECTS: returns true if the board has 3 items, otherwise return false
    public boolean isFullItems() {
        return this.items.size() == MAX_ITEMS;
    }

    // EFFECTS: returns all the tiles on the board
    public Placeable[][] getTiles() {
        return this.tiles;
    }

    // EFFECTS: returns the heroes on the board
    public List<Hero> getHeroes() {
        return this.heroes;
    }

    // EFFECTS: returns the items on the board
    public List<Item> getItems() {
        return this.items;
    }

    // EFFECTS: returns a String version of the tiles on the board
    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLUMNS; col++) {
                if (this.tiles[row][col] instanceof Hero) {
                    int index = this.heroes.indexOf(this.tiles[row][col]);
                    outputString.append(index + " ");
                } else if (this.tiles[row][col] instanceof Item) {
                    outputString.append(this.tiles[row][col].getName().substring(0, 1).toLowerCase() + " ");
                } else {
                    outputString.append("x ");
                }
            }
            outputString.append("\n");
        }
        return outputString.toString();
    }

    // EFFECTS: returns true if hero is in the list of heroes, otherwise return false
    private boolean inHeroes(Hero hero) {
        for (Hero h : this.heroes) {
            if (h.getName().equalsIgnoreCase(hero.getName())) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns true if item is in the list of items, otherwise return false
    private boolean inItems(Item item) {
        for (Item i : this.items) {
            if (i.getName().equalsIgnoreCase(item.getName())) {
                return false;
            }
        }
        return true;
    }

}
