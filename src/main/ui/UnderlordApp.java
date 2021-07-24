package ui;

import model.Board;
import model.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// UI Functionality and methods are implemented from Teller App. Link below:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java

// Dota Underlord Application
public class UnderlordApp {

    private Scanner input;
    private Board board;
    private List<String[]> possibleHeroes; // String[] format {name, ability, passive, tier,
                                           //                  ability1, ability2, ability3}
                                           // null if there is no ability, passive, or ability3

    // EFFECTS: runs the Underlord application
    public UnderlordApp() {
        runUnderlord();
    }

    private void runUnderlord() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    private void init() {
        input = new Scanner(System.in);
        board = new Board();
        possibleHeroes = new ArrayList<>();
        setUpTier1Heroes();
        setUpTier2Heroes();
        setUpTier3Heroes();
        setUpTier4Heroes();
        setUpTier5Heroes();
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t");
    }

    private void processCommand(String command) {

    }

    private void setUpTier1Heroes() {
        possibleHeroes.add(new String[]{"Anti-Mage", null, "Mana Break", "1", "Rogue", "Hunter", null});
        possibleHeroes.add(new String[]{"Batrider", "Sticky Napalm", null, "1", "Troll", "Knight", null});
        possibleHeroes.add(new String[]{"Bounty Hunter", "Shuriken Toss", null, "1", "Rogue", "Assassin", null});
        possibleHeroes.add(new String[]{"Crystal Maiden", "Frostbite", null, "1", "Human", "Mage", null});
        possibleHeroes.add(new String[]{"Dazzle", "Shadow Wave", null, "1", "Troll", "Healer", "Poisoner"});
        possibleHeroes.add(new String[]{"Drow Ranger", null, "Precision Aura", "1", "Heartless", "Vigilant", "Hunter"});
        possibleHeroes.add(new String[]{"Enchantress", "Nature's Attendants", null, "1", "Shaman", "Healer", null});
        possibleHeroes.add(new String[]{"Lich", "Frost Shield", null, "1", "Fallen", "Mage", null});
        possibleHeroes.add(new String[]{"Magnus", "Empower", null, "1", "Savage", "Shaman", null});
        possibleHeroes.add(new String[]{"Phantom Assassin", null, "Coup De Grace", "1", "Assassin", "Swordsman", null});
        possibleHeroes.add(new String[]{"Shadow Demon", "Demonic Purge", null, "1", "Heartless", "Demon", null});
        possibleHeroes.add(new String[]{"Slardar", "Corrosive Haze", null, "1", "Scaled", "Warrior", null});
        possibleHeroes.add(new String[]{"Snapfire", "Ol'Scatterblast", null, "1", "Brawny", "Dragon", null});
        possibleHeroes.add(new String[]{"Tusk", "Walrus Punch!", null, "1", "Savage", "Warrior", null});
        possibleHeroes.add(new String[]{"Vengeful Spirit", "Wave of Terror", null, "1", "Fallen", "Heartless", null});
        possibleHeroes.add(new String[]{"Venomancer", "Plague Ward", null, "1", "Scaled", "Summoner", "Poisoner"});
    }

    private void setUpTier2Heroes() {
        possibleHeroes.add(new String[]{"Bristleback", null, "Quill Spray", "2", "Brawny", "Savage", null});
        possibleHeroes.add(new String[]{"Chaos Knight", "Chaos Bolt", null, "2", "Demon", "Knight", null});
        possibleHeroes.add(new String[]{"Earth Spirit", "Geomagnetic Grip", "Earth Elemental", "2", "Spirit",
                "Warrior", null});
        possibleHeroes.add(new String[]{"Juggernaut", "Blade Fury", null, "2", "Brawny", "Swordsman", null});
        possibleHeroes.add(new String[]{"Kunkka", "Ghostship", null, "2", "Human", "Warrior", "Swordsman"});
        possibleHeroes.add(new String[]{"Legion Commander", "Duel", "Elemental Charge", "2", "Human",
                "Champion", null});
        possibleHeroes.add(new String[]{"Luna", null, "Moon Glaives", "2", "Vigilant", "Knight", null});
        possibleHeroes.add(new String[]{"Meepo", "Poof", "Divide We Stand", "2", "Rogue", "Summoner", null});
        possibleHeroes.add(new String[]{"Nature's Prophet", "Nature's Call", null, "2", "Shaman", "Summoner", null});
        possibleHeroes.add(new String[]{"Pudge", "Meat Hook", null, "2", "Heartless", "Warrior", null});
        possibleHeroes.add(new String[]{"Queen of Pain", "Scream of Pain", null, "2", "Demon", "Assassin",
                "Poisoner"});
        possibleHeroes.add(new String[]{"Spirit Breaker", "Charge of Darkness", null, "2", "Savage", "Brute", null});
        possibleHeroes.add(new String[]{"Storm Spirit", "Ball Lightning", "Storm Elemental", "2", "Spirit", "Mage",
                null});
        possibleHeroes.add(new String[]{"Windranger", "Powershot", null, "2", "Vigilant", "Hunter", null});
    }

    private void setUpTier3Heroes() {
        possibleHeroes.add(new String[]{"Abaddon", "Aphotic Shield", null, "3", "Fallen", "Knight", null});
        possibleHeroes.add(new String[]{"Alchemist", "Acid Spray", null, "3", "Brute", "Rogue", "Poisoner"});
        possibleHeroes.add(new String[]{"Beastmaster", "Wild Axes", null, "3", "Brawny", "Hunter", "Shaman"});
        possibleHeroes.add(new String[]{"Ember Spirit", "Sleight of Fist", "Fire Elemental", "3", "Spirit",
                "Assassin", "Swordsman"});
        possibleHeroes.add(new String[]{"Lifestealer", null, "Feast", "3", "Heartless", "Brute", null});
        possibleHeroes.add(new String[]{"Lycan", "Shapeshift", null, "3", "Human", "Savage", "Summoner"});
        possibleHeroes.add(new String[]{"Omniknight", "Purification", null, "3", "Human", "Knight", "Healer"});
        possibleHeroes.add(new String[]{"Puck", "Illusory Orb", "Phase Shift", "3", "Dragon", "Mage", null});
        possibleHeroes.add(new String[]{"Shadow Shaman", "Mass Serpant Ward", null, "3", "Troll", "Knight", null});
        possibleHeroes.add(new String[]{"Slark", "Pounce", "Essence Shift", "3", "Scaled", "Assassin", null});
        possibleHeroes.add(new String[]{"Spectre", "Spectral Dagger", null, "3", "Void", "Demon", null});
        possibleHeroes.add(new String[]{"Terrorblade", "Metamorphosis", null, "3", "Demon", "Hunter", "Fallen"});
        possibleHeroes.add(new String[]{"Treant Protector", "Leech Seed", null, "3", "Shaman", "Healer", null});
    }

    private void setUpTier4Heroes() {
        possibleHeroes.add(new String[]{"Death Prophet", "Exorcism", null, "4", "Fallen", "Heartless", null});
        possibleHeroes.add(new String[]{"Doom", "Doom", null, "4", "Demon", "Brute", null});
        possibleHeroes.add(new String[]{"Lina", "Laguna Blade", null, "4", "Human", "Mage", null});
        possibleHeroes.add(new String[]{"Lone Druid", "Summon Spirit Bear", null, "4", "Savage", "Shaman",
                "Summoner"});
        possibleHeroes.add(new String[]{"Mirana", "Sacred Arrow", null, "4", "Vigilant", "Hunter", null});
        possibleHeroes.add(new String[]{"Pangolier", "Shield Crash", null, "4", "Savage", "Swordsman", null});
        possibleHeroes.add(new String[]{"Rubick", "Fade Bolt", "Spell Steal", "4", "Mage", "Magus", null});
        possibleHeroes.add(new String[]{"Sven", "God's Strength", "Great Cleave", "4", "Rogue", "Knight",
                "Swordsman"});
        possibleHeroes.add(new String[]{"Templar Assassin", "Refraction", "Meld", "4", "Vigilant", "Void",
                "Assassin"});
        possibleHeroes.add(new String[]{"Tidehunter", "Ravage", null, "4", "Scaled", "Warrior", null});
        possibleHeroes.add(new String[]{"Viper", "Nethertoxin", "Corrosive Skin", "4", "Dragon", "Poisoner", null});
        possibleHeroes.add(new String[]{"Void Spirit", "Dissimilate", "Void Elemental", "Void", "Spirit", null});
    }

    private void setUpTier5Heroes() {
        possibleHeroes.add(new String[]{"Axe", "Culling Blade", "Counter Helix", "5", "Brawny", "Brute", null});
        possibleHeroes.add(new String[]{"Dragon Knight", "Breathe Fire", null, "5", "Human", "Dragon", "Knight"});
        possibleHeroes.add(new String[]{"Faceless Void", "Chronocube", null, "5", "Void", "Assassin", null});
        possibleHeroes.add(new String[]{"Keeper of the Light", "Illuminate", null, "5", "Human", "Mage", null});
        possibleHeroes.add(new String[]{"Medusa", "Stone Gaze", "Split Shot", "5", "Scaled", "Hunter", null});
        possibleHeroes.add(new String[]{"Troll Warlord", null, "Fervor", "5", "Troll", "Warrior", null});
        possibleHeroes.add(new String[]{"Wraith King", "Wraithfire Blast", "Raise Dead", "Fallen", "Swordsman", null});
    }

}
