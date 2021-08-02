package ui;

import exceptions.InvalidTileException;
import exceptions.TileOccupiedException;
import exceptions.UnitAlreadyOnBoardException;
import model.Board;
import model.Hero;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// UI Functionality and methods are implemented from Teller App. Link below:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java

// Dota Underlord Application
public class UnderlordApp {

    private static final String JSON_STORE = "./data/json/board.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;
    private Board board;
    private List<String[]> possibleHeroes; // String[] format {name, ability, passive, tier,
                                           //                  alliance1, alliance2, alliance3}
                                           // null if there is no ability, passive, or alliance3
    private List<String> possibleItems;

    // EFFECTS: runs the Underlord application
    public UnderlordApp() {
        runUnderlord();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runUnderlord() {
        boolean keepGoing = true;
        String command;

        try {
            init();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to run application: file not found");
        }

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                doSaveBoardOption();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the UnderlordApp
    private void init() throws FileNotFoundException {
        input = new Scanner(System.in);
        board = new Board();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        possibleHeroes = new ArrayList<>();
        possibleItems = new ArrayList<>();
        setUpTier1Heroes();
        setUpTier2Heroes();
        setUpTier3Heroes();
        setUpTier4Heroes();
        setUpTier5Heroes();
        setUpPossibleItems();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\th -> View all possible heroes");
        System.out.println("\tt -> View possible heroes by tier");
        System.out.println("\tv -> View hero information");
        System.out.println("\ti -> View possible items");
        System.out.println("\tb -> View board");
        System.out.println("\ta -> Add Unit");
        System.out.println("\tr -> Remove Unit");
        System.out.println("\tm -> Move Unit");
        System.out.println("\ts -> Save Board");
        System.out.println("\tl -> Load Board");
        System.out.println("\tq -> Quit Application");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("h")) {
            doViewPossibleHeroes();
        } else if (command.equals("t")) {
            doViewPossibleHeroesByTier();
        } else if (command.equals("v")) {
            doHeroInformation();
        } else if (command.equals("i")) {
            doViewItems();
        } else if (command.equals("b")) {
            doViewBoard();
        } else if (command.equals("a")) {
            doAddUnit();
        } else if (command.equals("r")) {
            doRemoveUnit();
        } else if (command.equals("m")) {
            doMoveUnit();
        } else if (command.equals("s")) {
            saveBoard();
        } else if (command.equals("l")) {
            loadBoard();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: gives user the option to save board
    private void doSaveBoardOption() {
        System.out.println("Do you want to save your board before quitting? Yes (Y) or No (N)?");
        String command = input.nextLine();
        if (command.equalsIgnoreCase("Y")) {
            saveBoard();
        }
    }

    // EFFECTS: processes user command for displaying hero information
    private void doHeroInformation() {
        System.out.println("Choose a hero from the list for more information about it: ");
        doViewPossibleHeroes();
        int command = input.nextInt();
        input.nextLine();
        String[] heroInformation = possibleHeroes.get(command - 1);
        displayHeroInformation(heroInformation);
    }

    // EFFECTS: prints out the information of the given hero
    private void displayHeroInformation(String[] heroInformation) {
        System.out.println("Hero name: " + heroInformation[0]);
        if (heroInformation[1] != null) {
            System.out.println("Ability: " + heroInformation[1]);
        }
        if (heroInformation[2] != null) {
            System.out.println("Passive: " + heroInformation[2]);
        }
        System.out.println("Tier: " + heroInformation[3]);
        System.out.println("Alliance 1: " + heroInformation[4]);
        System.out.println("Alliance 2: " + heroInformation[5]);
        if (heroInformation[6] != null) {
            System.out.println("Alliance 3: " + heroInformation[6]);
        }
    }

    // EFFECTS: prints out the heroes that are on the board
    private void showHeroesOnBoard() {
        List<Hero> heroes = board.getHeroes();
        int counter = 1;
        for (Hero hero : heroes) {
            System.out.println("\t" + counter + ") " + hero.getName() + " [row: " + (hero.getRow() + 1) + ", col: "
                    + (hero.getColumn() + 1) + "]");
            counter++;
        }
    }

    // EFFECTS: prints out the items that are on the board
    private void showItemsOnBoard() {
        List<Item> items = board.getItems();
        int counter = 1;
        for (Item item : items) {
            System.out.println("\t" + counter + ") " + item.getName() + " [row: " + (item.getRow() + 1) + ", col: "
                    + (item.getColumn() + 1) + "]");
            counter++;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for moving either a hero or an item
    private void doMoveUnit() {
        if (board.getHeroes().size() == 0 && board.getItems().size() == 0) {
            System.out.println("There are no heroes and items to move");
        } else {
            int command = getInputHeroOrItem();
            if (command == 1) {
                if (board.getHeroes().size() == 0) {
                    System.out.println("There are no heroes to move");
                } else {
                    moveHero();
                }
            } else {
                if (board.getItems().size() == 0) {
                    System.out.println("There are no items to move");
                } else {
                    moveItem();
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the chosen hero to the user's given row and column
    private void moveHero() {
        int command;
        System.out.println("Select a hero on the board from the list:");
        showHeroesOnBoard();
        command = input.nextInt();
        input.nextLine();

        Hero hero = board.getHeroes().get(command - 1);

        System.out.println("Please put the new row for " + hero.getName() + " (between 1 and " + Board.MAX_ROWS
                + " inclusive):");
        int newRow = input.nextInt();
        input.nextLine();
        System.out.println("Please put the new column for " + hero.getName() + " (between 1 and " + Board.MAX_COLUMNS
                + " inclusive):");
        int newCol = input.nextInt();
        input.nextLine();

        if (board.getTiles()[newRow - 1][newCol - 1] != null) {
            System.out.println("Unable to place hero in this spot as it is already occupied");
        } else {
            board.moveUnit(hero, newRow - 1, newCol - 1);
            doViewBoard();
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the chosen item to the user's given row and column
    private void moveItem() {
        int command;
        System.out.println("Select an item on the board from the list:");
        showItemsOnBoard();
        command = input.nextInt();
        input.nextLine();

        Item item = board.getItems().get(command - 1);

        System.out.println("Please put the new row for " + item.getName() + " (between 1 and " + Board.MAX_ROWS
                + " inclusive):");
        int newRow = input.nextInt();
        input.nextLine();
        System.out.println("Please put the new column for " + item.getName() + " (between 1 and " + Board.MAX_COLUMNS
                + " inclusive):");
        int newCol = input.nextInt();
        input.nextLine();

        if (board.getTiles()[newRow - 1][newCol - 1] != null) {
            System.out.println("Unable to place hero in this spot as it is already occupied");
        } else {
            board.moveUnit(item, newRow - 1, newCol - 1);
            doViewBoard();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for removing a hero or an item
    private void doRemoveUnit() {
        if (board.getHeroes().size() == 0 && board.getItems().size() == 0) {
            System.out.println("There are no heroes and items to remove");
        } else {
            int command = getInputHeroOrItem();
            if (command == 1) {
                if (board.getHeroes().size() == 0) {
                    System.out.println("There are no heroes to remove");
                } else {
                    removeHero();
                }
            } else {
                if (board.getItems().size() == 0) {
                    System.out.println("There are no items to remove");
                } else {
                    removeItem();
                }
            }
        }
    }

    // EFFECTS: returns whether the user chooses hero or item
    private int getInputHeroOrItem() {
        int command;
        do {
            System.out.println("Select if you want to remove a hero (1) or an item (2): ");
            command = input.nextInt();
            input.nextLine();
        } while (command != 1 && command != 2);
        return command;
    }

    // MODIFIES: this
    // EFFECTS: removes the chosen hero from the board
    private void removeHero() {
        int command;
        System.out.println("Select a hero on the board from the list:");
        showHeroesOnBoard();
        command = input.nextInt();
        input.nextLine();
        Hero hero = board.getHeroes().get(command - 1);
        try {
            board.removeHero(hero.getRow(), hero.getColumn());
        } catch (InvalidTileException e) {
            System.err.println("Hero is in invalid tile");
        }
        doViewBoard();
    }

    // MODIFIES: this
    // EFFECTS: removes the chosen item from the board
    private void removeItem() {
        int command;
        System.out.println("Select an item on the board from the list:");
        showItemsOnBoard();
        command = input.nextInt();
        input.nextLine();
        Item item = board.getItems().get(command - 1);
        board.removeItem(item.getRow(), item.getColumn());
        doViewBoard();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's command on whether to add a hero or an item
    private void doAddUnit() {
        int command = getInputHeroOrItem();
        if (command == 1) {
            doAddHero();
        } else {
            doAddItem();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the chosen hero to the board
    private void doAddHero() {
        if (board.isFullHeroes()) {
            System.out.println("The board is already full");
        } else {
            doViewPossibleHeroes();
            System.out.println("Select the number corresponding to the hero: ");
            int possibleHeroesIndex = input.nextInt();
            input.nextLine();
            String[] heroInformation = possibleHeroes.get(possibleHeroesIndex - 1);
            Hero hero = createHero(heroInformation);
            try {
                board.addHero(hero);
                System.out.println("Hero was successfully added");
                doViewBoard();
            } catch (TileOccupiedException e) {
                System.out.println("The tile is already occupied");
            } catch (UnitAlreadyOnBoardException e) {
                System.out.println("The hero is already on the board");
            }
        }
    }

    // EFFECTS: creates a Hero instance at the user's given row and column
    private Hero createHero(String[] heroInformation) {
        System.out.println("Please put the row to place it in (between 1 and " + Board.MAX_ROWS + " inclusive):");
        int row = input.nextInt();
        input.nextLine();
        System.out.println("Please put the column to place it in (between 1 and " + Board.MAX_COLUMNS
                + " inclusive):");
        int column = input.nextInt();
        input.nextLine();
        List<String> alliances = new ArrayList<>();
        for (int i = 4; i <= 6; i++) {
            if (heroInformation[i] != null) {
                alliances.add(heroInformation[i]);
            }
        }
        return new Hero(heroInformation[0], row - 1, column - 1, heroInformation[1],
                heroInformation[2], Integer.parseInt(heroInformation[3]), alliances);
    }

    // MODIFIES: this
    // EFFECTS: adds the chosen item to the board
    private void doAddItem() {
        if (board.isFullItems()) {
            System.out.println("The board is already full");
        } else {
            doViewItems();
            System.out.println("Select the letter corresponding to the item: ");
            int possibleItemIndex = input.nextInt();
            input.nextLine();
            String itemName = possibleItems.get(possibleItemIndex - 1);
            Item item = createItem(itemName);
            try {
                board.addItem(item);
                System.out.println("Item was successfully added");
                doViewBoard();
            } catch (TileOccupiedException e) {
                System.out.println("The tile is already occupied");
            } catch (UnitAlreadyOnBoardException e) {
                System.out.println("The hero is already on the board");
            }
        }
    }

    // REQUIRES: itemName is not null or length of 0
    // EFFECTS: Creates an Item instance with the user's given row and column
    private Item createItem(String itemName) {
        System.out.println("Please put the row to place it in (between 1 and " + Board.MAX_ROWS + " inclusive):");
        int row = input.nextInt();
        input.nextLine();
        System.out.println("Please put the column to place it in (between 1 and " + Board.MAX_COLUMNS
                + " inclusive):");
        int column = input.nextInt();
        input.nextLine();
        return new Item(itemName, row - 1, column - 1);
    }

    // EFFECTS: prints a list of all the items that the user can choose
    private void doViewItems() {
        System.out.println("Here are all the possible items:");
        for (int i = 0; i < possibleItems.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + possibleItems.get(i));
        }

    }

    // EFFECTS: prints all the information for the board including: key to read the board, the board itself, and the
    //          alliances
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
        if (board.getItems().size() == 0) {
            System.out.println("\t\tNo items on the board");
        } else {
            List<Item> items = board.getItems();
            for (Item item : items) {
                System.out.println("\t\t" + item.getName().substring(0, 1).toLowerCase() + " - " + item.getName());
            }
        }
        System.out.println(board.toString());
        List<String> alliances = board.getAlliances();
        System.out.println("Alliances: " + alliances.toString());
    }

    // EFFECTS: prints out all the possible heroes that can be chosen
    private void doViewPossibleHeroes() {
        System.out.println("Here are all the possible heroes:");
        int counter = 1;
        for (String[] heroInfo : possibleHeroes) {
            System.out.println("\t" + counter + ") " + heroInfo[0]);
            counter++;
        }
    }

    // EFFECTS: processes user command for viewing heroes by their tier
    private void doViewPossibleHeroesByTier() {
        int currentTier = 1;
        boolean keepGoing = true;
        String command;

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

    // EFFECTS: prints commands that user can use to navigate between tiers
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

    // EFFECTS: prints out the heroes based on their tier
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

    // EFFECTS: saves the board to file
    private void saveBoard() {
        try {
            System.out.println("Please enter a name for the board: ");
            board.setBoardName(input.nextLine());

            jsonWriter.open();
            jsonWriter.write(board);
            jsonWriter.close();
            System.out.println("Saved " + board.getBoardName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads board from file
    private void loadBoard() {
        try {
            board = jsonReader.read();
            System.out.println("Loaded " + board.getBoardName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all the tier 1 heroes to the possibleHeroes
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

    // MODIFIES: this
    // EFFECTS: adds all the tier 2 heroes to the possibleHeroes
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

    // MODIFIES: this
    // EFFECTS: adds all the tier 3 heroes to the possibleHeroes
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

    // MODIFIES: this
    // EFFECTS: adds all the tier 4 heroes to the possibleHeroes
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

    // MODIFIES: this
    // EFFECTS: adds all the tier 5 heroes to the possibleHeroes
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

    // MODIFIES: this
    // EFFECTS: adds all the items to possibleItems
    private void setUpPossibleItems() {
        possibleItems.add("Barricade");
        possibleItems.add("Mango Tree");
        possibleItems.add("Target Buddy");
    }

}
