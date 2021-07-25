package ui;

import exceptions.TileOccupiedException;
import exceptions.UnitAlreadyOnBoardException;
import model.Board;
import model.Hero;
import model.Item;

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
                                           //                  alliance1, alliance2, alliance3}
                                           // null if there is no ability, passive, or alliance3

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
        System.out.println("\th -> View all possible heroes");
        System.out.println("\tt -> View possible heroes by tier");
        System.out.println("\tv -> View board");
        System.out.println("\ta -> Add Hero");
    }

    private void processCommand(String command) {
        if (command.equals("h")) {
            doViewPossibleHeroes();
        } else if (command.equals("t")) {
            doViewPossibleHeroesByTier();
        } else if (command.equals("v")) {
            doViewBoard();
        } else if (command.equals("a")) {
            doAddHero();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void doAddHero() {
        System.out.println("Select the number corresponding to the hero: ");
        int possibleHeroesIndex = input.nextInt();
        String[] heroInformation = possibleHeroes.get(possibleHeroesIndex - 1);
        System.out.println("Please select the row to place it in (between 1 and " + Board.MAX_ROWS + " inclusive):");
        int row = input.nextInt();
        System.out.println("Please select the column to place it in (between 1 and " + Board.MAX_COLUMNS
                + " inclusive):");
        int column = input.nextInt();
        List<String> alliances = new ArrayList<>();
        for (int i = 4; i <= 6; i++) {
            if (heroInformation[i] != null) {
                alliances.add(heroInformation[i]);
            }
        }
        Hero hero = new Hero(heroInformation[0], row - 1, column - 1, heroInformation[1], heroInformation[2],
                Integer.parseInt(heroInformation[3]), alliances);
        try {
            board.addHero(hero);
        } catch (TileOccupiedException e) {
            System.out.println("The tile is already occupied");
        } catch (UnitAlreadyOnBoardException e) {
            System.out.println("The hero is already on the board");
        }
    }

    private void doViewBoard() {
        System.out.println("Key (used to read the board): ");

        System.out.println("\tHeroes:");
        if (board.getHeroes().size() == 0) {
            System.out.println("\t\tNo heroes on the board");
        } else {
            List<Hero> heroes = board.getHeroes();
            for (int i = 0; i < heroes.size(); i++) {
                System.out.println("\t\t" + i + " - " + heroes.get(i).getName());
            }
        }

        System.out.println("\tItems:");
        if (board.getHeroes().size() == 0) {
            System.out.println("\t\tNo items on the board");
        } else {
            List<Item> items = board.getItems();
            for (int i = 0; i < items.size(); i++) {
                System.out.println("\t\t" + i + " - " + items.get(i).getName());
            }
        }
        System.out.println(board.toString());
    }

    private void doViewPossibleHeroes() {
        System.out.println("Here are all the possible heroes:");
        int counter = 1;
        for (String[] heroInfo : possibleHeroes) {
            System.out.println("\t" + counter + ") " + heroInfo[0]);
            counter++;
        }
    }

    private void doViewPossibleHeroesByTier() {
        int currentTier = 1;
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            printHeroesBasedOnTier(currentTier);
            displayHeroByTierMenu(currentTier);
            command = input.nextLine();

            if (command.equals("b")) {
                keepGoing = false;
            } else if (command.equals("p") && currentTier != 1) {
                currentTier--;
            } else if (command.equals("n") &&  currentTier != 5) {
                currentTier++;
            } else {
                System.out.println("Selection not valid...");
            }

        }
    }

    private void displayHeroByTierMenu(int tier) {
        System.out.println("\nSelect from:");
        if (tier != 1) {
            System.out.println("\tp -> Previous Tier");
        }
        if (tier != 5) {
            System.out.println("\tn -> Next Tier");
        }
        System.out.println("\tb -> Return to Menu");
    }

    private void printHeroesBasedOnTier(int tier) {
        System.out.println("Here are all the possible heroes that are tier " + tier + ":");

        int counter;
        if (tier == 1) {
            counter = 1;
        } else if (tier == 2) {
            counter = 17;
        } else if (tier == 3) {
            counter = 31;
        } else if (tier == 4) {
            counter = 44;
        } else {
            counter = 56;
        }

        while (counter <= possibleHeroes.size() && Integer.parseInt(possibleHeroes.get(counter - 1)[3]) == tier) {
            System.out.println("\t" + counter + ") " + possibleHeroes.get(counter - 1)[0]);
            counter++;
        }
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
        possibleHeroes.add(new String[]{"Void Spirit", "Dissimilate", "Void Elemental", "4", "Void", "Spirit", null});
    }

    private void setUpTier5Heroes() {
        possibleHeroes.add(new String[]{"Axe", "Culling Blade", "Counter Helix", "5", "Brawny", "Brute", null});
        possibleHeroes.add(new String[]{"Dragon Knight", "Breathe Fire", null, "5", "Human", "Dragon", "Knight"});
        possibleHeroes.add(new String[]{"Faceless Void", "Chronocube", null, "5", "Void", "Assassin", null});
        possibleHeroes.add(new String[]{"Keeper of the Light", "Illuminate", null, "5", "Human", "Mage", null});
        possibleHeroes.add(new String[]{"Medusa", "Stone Gaze", "Split Shot", "5", "Scaled", "Hunter", null});
        possibleHeroes.add(new String[]{"Troll Warlord", null, "Fervor", "5", "Troll", "Warrior", null});
        possibleHeroes.add(new String[]{"Wraith King", "Wraithfire Blast", "Raise Dead", "5", "Fallen", "Swordsman",
                null});
    }

}
