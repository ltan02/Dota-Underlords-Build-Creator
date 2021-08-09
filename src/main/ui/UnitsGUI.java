package ui;

import exceptions.TileOccupiedException;
import exceptions.UnitAlreadyOnBoardException;
import model.Board;
import model.Hero;
import model.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

// Graphical User Interface for Units Menu
public class UnitsGUI extends JFrame implements ActionListener {

    private List<String[]> possibleUnits = new ArrayList<>();
    private ButtonGroup bg = new ButtonGroup();
    private Board board;
    private UnderlordBoardGUI underlordGui;

    // EFFECTS: Constructs a new Units menu
    public UnitsGUI(Board board, UnderlordBoardGUI underlordGui) {
        this.board = board;
        this.underlordGui = underlordGui;
        setUpUnits();
        setUpButtons();
        JButton addUnitButton = new JButton("Add Unit");
        addUnitButton.addActionListener(this);
        addUnitButton.setBounds(400, 900, 150, 50);
        this.add(addUnitButton);
        this.setSize(800, 1000);
        this.setLayout(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: gets the selected unit that will be added
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedUnit = getSelectedUnitName();
        String[] information = possibleUnits.get(getUnitInformationIndex(selectedUnit));
        addUnit(information);
        underlordGui.drawBoard();
        this.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: adds the given unit with its information to the board
    private void addUnit(String[] information) {
        if (information.length == 1) {
            if (!board.isFullItems()) {
                addItem(information, 0, 0);
            } else {
                JOptionPane.showMessageDialog(underlordGui, "The board has maximum items", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (!board.isFullHeroes()) {
                addHero(information, 0, 0);
            } else {
                JOptionPane.showMessageDialog(underlordGui, "The board has maximum heroes", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds item to the board at the first empty tile starting at 0,0
    private void addItem(String[] information, int row, int column) {
        try {
            board.addItem(new Item(information[0], row, column));
        } catch (TileOccupiedException ex) {
            if (column != 0 && column % (Board.MAX_COLUMNS - 1) == 0) {
                addItem(information, row + 1, 0);
            } else {
                addItem(information, row, column + 1);
            }
        } catch (UnitAlreadyOnBoardException ex) {
            JOptionPane.showMessageDialog(this, "Item Already On Board", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds hero to the board at the first empty tile starting at 0,0
    private void addHero(String[] information, int row, int column) {
        List<String> alliances = new ArrayList<>();
        alliances.add(information[4]);
        alliances.add(information[5]);
        alliances.add(information[6]);
        try {
            board.addHero(new Hero(information[0], row, column, information[1], information[2],
                        Integer.parseInt(information[3]), alliances));
        } catch (TileOccupiedException ex) {
            if (column != 0 && column % (Board.MAX_COLUMNS - 1) == 0) {
                addHero(information, row + 1, 0);
            } else {
                addHero(information, row, column + 1);
            }
        } catch (UnitAlreadyOnBoardException ex) {
            JOptionPane.showMessageDialog(this, "Hero Already On Board", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: returns text of the radio button that is selected
    private String getSelectedUnitName() {
        String selectedUnit = null;
        for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                selectedUnit = button.getText();
            }
        }
        return selectedUnit;
    }

    // EFFECTS: returns the information of the chosen unit
    private int getUnitInformationIndex(String selectedUnit) {
        int location = -1;
        for (int i = 0; i < possibleUnits.size(); i++) {
            if (possibleUnits.get(i)[0].equals(selectedUnit)) {
                location = i;
            }
        }
        return location;
    }

    // MODIFIES: this
    // EFFECTS: adds all the radio buttons for each hero and item
    private void setUpButtons() {
        int x = 25;
        int y = 10;
        for (int i = 0; i < possibleUnits.size(); i++) {
            JRadioButton radioButton = new JRadioButton(possibleUnits.get(i)[0]);
            if (i % 33 == 0) {
                x += 225;
                y = 10;
            } else {
                y += 25;
            }
            radioButton.setBounds(x, y, 200, 25);
            bg.add(radioButton);
            this.add(radioButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all the heroes and items to the possibleUnits
    private void setUpUnits() {
        setUpTier1Heroes();
        setUpTier2Heroes();
        setUpTier3Heroes();
        setUpTier4Heroes();
        setUpTier5Heroes();
        setUpPossibleItems();
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 1 heroes to the possibleUnits
    private void setUpTier1Heroes() {
        possibleUnits.add(new String[]{"Anti-Mage", null, "Mana Break", "1", "Rogue", "Hunter", null});
        possibleUnits.add(new String[]{"Batrider", "Sticky Napalm", null, "1", "Troll", "Knight", null});
        possibleUnits.add(new String[]{"Bounty Hunter", "Shuriken Toss", null, "1", "Rogue", "Assassin", null});
        possibleUnits.add(new String[]{"Crystal Maiden", "Frostbite", null, "1", "Human", "Mage", null});
        possibleUnits.add(new String[]{"Dazzle", "Shadow Wave", null, "1", "Troll", "Healer", "Poisoner"});
        possibleUnits.add(new String[]{"Drow Ranger", null, "Precision Aura", "1", "Heartless", "Vigilant", "Hunter"});
        possibleUnits.add(new String[]{"Enchantress", "Nature's Attendants", null, "1", "Shaman", "Healer", null});
        possibleUnits.add(new String[]{"Lich", "Frost Shield", null, "1", "Fallen", "Mage", null});
        possibleUnits.add(new String[]{"Magnus", "Empower", null, "1", "Savage", "Shaman", null});
        possibleUnits.add(new String[]{"Phantom Assassin", null, "Coup De Grace", "1", "Assassin", "Swordsman", null});
        possibleUnits.add(new String[]{"Shadow Demon", "Demonic Purge", null, "1", "Heartless", "Demon", null});
        possibleUnits.add(new String[]{"Slardar", "Corrosive Haze", null, "1", "Scaled", "Warrior", null});
        possibleUnits.add(new String[]{"Snapfire", "Ol'Scatterblast", null, "1", "Brawny", "Dragon", null});
        possibleUnits.add(new String[]{"Tusk", "Walrus Punch!", null, "1", "Savage", "Warrior", null});
        possibleUnits.add(new String[]{"Vengeful Spirit", "Wave of Terror", null, "1", "Fallen", "Heartless", null});
        possibleUnits.add(new String[]{"Venomancer", "Plague Ward", null, "1", "Scaled", "Summoner", "Poisoner"});
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 2 heroes to the possibleUnits
    private void setUpTier2Heroes() {
        possibleUnits.add(new String[]{"Bristleback", null, "Quill Spray", "2", "Brawny", "Savage", null});
        possibleUnits.add(new String[]{"Chaos Knight", "Chaos Bolt", null, "2", "Demon", "Knight", null});
        possibleUnits.add(new String[]{"Earth Spirit", "Geomagnetic Grip", "Earth Elemental", "2", "Spirit",
                "Warrior", null});
        possibleUnits.add(new String[]{"Juggernaut", "Blade Fury", null, "2", "Brawny", "Swordsman", null});
        possibleUnits.add(new String[]{"Kunkka", "Ghostship", null, "2", "Human", "Warrior", "Swordsman"});
        possibleUnits.add(new String[]{"Legion Commander", "Duel", "Elemental Charge", "2", "Human",
                "Champion", null});
        possibleUnits.add(new String[]{"Luna", null, "Moon Glaives", "2", "Vigilant", "Knight", null});
        possibleUnits.add(new String[]{"Meepo", "Poof", "Divide We Stand", "2", "Rogue", "Summoner", null});
        possibleUnits.add(new String[]{"Nature's Prophet", "Nature's Call", null, "2", "Shaman", "Summoner", null});
        possibleUnits.add(new String[]{"Pudge", "Meat Hook", null, "2", "Heartless", "Warrior", null});
        possibleUnits.add(new String[]{"Queen of Pain", "Scream of Pain", null, "2", "Demon", "Assassin",
                "Poisoner"});
        possibleUnits.add(new String[]{"Spirit Breaker", "Charge of Darkness", null, "2", "Savage", "Brute", null});
        possibleUnits.add(new String[]{"Storm Spirit", "Ball Lightning", "Storm Elemental", "2", "Spirit", "Mage",
                null});
        possibleUnits.add(new String[]{"Windranger", "Powershot", null, "2", "Vigilant", "Hunter", null});
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 3 heroes to the possibleUnits
    private void setUpTier3Heroes() {
        possibleUnits.add(new String[]{"Abaddon", "Aphotic Shield", null, "3", "Fallen", "Knight", null});
        possibleUnits.add(new String[]{"Alchemist", "Acid Spray", null, "3", "Brute", "Rogue", "Poisoner"});
        possibleUnits.add(new String[]{"Beastmaster", "Wild Axes", null, "3", "Brawny", "Hunter", "Shaman"});
        possibleUnits.add(new String[]{"Ember Spirit", "Sleight of Fist", "Fire Elemental", "3", "Spirit",
                "Assassin", "Swordsman"});
        possibleUnits.add(new String[]{"Lifestealer", null, "Feast", "3", "Heartless", "Brute", null});
        possibleUnits.add(new String[]{"Lycan", "Shapeshift", null, "3", "Human", "Savage", "Summoner"});
        possibleUnits.add(new String[]{"Omniknight", "Purification", null, "3", "Human", "Knight", "Healer"});
        possibleUnits.add(new String[]{"Puck", "Illusory Orb", "Phase Shift", "3", "Dragon", "Mage", null});
        possibleUnits.add(new String[]{"Shadow Shaman", "Mass Serpant Ward", null, "3", "Troll", "Knight", null});
        possibleUnits.add(new String[]{"Slark", "Pounce", "Essence Shift", "3", "Scaled", "Assassin", null});
        possibleUnits.add(new String[]{"Spectre", "Spectral Dagger", null, "3", "Void", "Demon", null});
        possibleUnits.add(new String[]{"Terrorblade", "Metamorphosis", null, "3", "Demon", "Hunter", "Fallen"});
        possibleUnits.add(new String[]{"Treant Protector", "Leech Seed", null, "3", "Shaman", "Healer", null});
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 4 heroes to the possibleUnits
    private void setUpTier4Heroes() {
        possibleUnits.add(new String[]{"Death Prophet", "Exorcism", null, "4", "Fallen", "Heartless", null});
        possibleUnits.add(new String[]{"Doom", "Doom", null, "4", "Demon", "Brute", null});
        possibleUnits.add(new String[]{"Lina", "Laguna Blade", null, "4", "Human", "Mage", null});
        possibleUnits.add(new String[]{"Lone Druid", "Summon Spirit Bear", null, "4", "Savage", "Shaman",
                "Summoner"});
        possibleUnits.add(new String[]{"Mirana", "Sacred Arrow", null, "4", "Vigilant", "Hunter", null});
        possibleUnits.add(new String[]{"Pangolier", "Shield Crash", null, "4", "Savage", "Swordsman", null});
        possibleUnits.add(new String[]{"Rubick", "Fade Bolt", "Spell Steal", "4", "Mage", "Magus", null});
        possibleUnits.add(new String[]{"Sven", "God's Strength", "Great Cleave", "4", "Rogue", "Knight",
                "Swordsman"});
        possibleUnits.add(new String[]{"Templar Assassin", "Refraction", "Meld", "4", "Vigilant", "Void",
                "Assassin"});
        possibleUnits.add(new String[]{"Tidehunter", "Ravage", null, "4", "Scaled", "Warrior", null});
        possibleUnits.add(new String[]{"Viper", "Nethertoxin", "Corrosive Skin", "4", "Dragon", "Poisoner", null});
        possibleUnits.add(new String[]{"Void Spirit", "Dissimilate", "Void Elemental", "4", "Void", "Spirit", null});
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 5 heroes to the possibleUnits
    private void setUpTier5Heroes() {
        possibleUnits.add(new String[]{"Axe", "Culling Blade", "Counter Helix", "5", "Brawny", "Brute", null});
        possibleUnits.add(new String[]{"Dragon Knight", "Breathe Fire", null, "5", "Human", "Dragon", "Knight"});
        possibleUnits.add(new String[]{"Faceless Void", "Chronocube", null, "5", "Void", "Assassin", null});
        possibleUnits.add(new String[]{"Keeper of the Light", "Illuminate", null, "5", "Human", "Mage", null});
        possibleUnits.add(new String[]{"Medusa", "Stone Gaze", "Split Shot", "5", "Scaled", "Hunter", null});
        possibleUnits.add(new String[]{"Troll Warlord", null, "Fervor", "5", "Troll", "Warrior", null});
        possibleUnits.add(new String[]{"Wraith King", "Wraithfire Blast", "Raise Dead", "5", "Fallen", "Swordsman",
                null});
    }

    // MODIFIES: this
    // EFFECTS: adds all the items to possibleUnits
    private void setUpPossibleItems() {
        possibleUnits.add(new String[]{"Barricade"});
        possibleUnits.add(new String[]{"Mango Tree"});
        possibleUnits.add(new String[]{"Target Buddy"});
    }

}
