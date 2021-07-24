package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    Board board;

    @BeforeEach
    public void runBefore() {
        board = new Board();
    }

    @Test
    public void testGetUnitWithUnit() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));
        assertEquals(board.getUnit(2, 3), testUnit);
    }

    @Test
    public void testGetUnitWithoutUnit() {
        assertEquals(board.getUnit(2, 3), null);
    }

    @Test
    public void testRemoveUnit() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));
        assertEquals(board.removeUnit(2, 3), testUnit);
        assertEquals(board.getUnit(2, 3), null);
    }

    @Test
    public void testAddHeroSuccessful() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));
    }

    @Test
    public void testAddHeroFailureAlreadyOccupied() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));
        assertFalse(board.addHero(testUnit));
    }

    @Test
    public void testAddHeroFailureDuplicate() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));

        Hero testUnitDuplicate = new Hero("test", 3, 5, "test ability", "test passive",
                2, alliances);
        assertFalse(board.addHero(testUnitDuplicate));
    }
    @Test
    public void testAddItemSuccessful() {
        Item testUnit = new Item("test", 2, 3);
        assertTrue(board.addItem(testUnit));
    }

    @Test
    public void testAddItemFailureAlreadyOccupied() {
        Item testUnit = new Item("test", 2, 3);
        assertTrue(board.addItem(testUnit));
        assertFalse(board.addItem(testUnit));
    }

    @Test
    public void testAddItemFailureDuplicate() {
        Item testUnit = new Item("test", 2, 3);
        assertTrue(board.addItem(testUnit));

        Item testUnitDuplicate = new Item("test", 3, 5);
        assertFalse(board.addItem(testUnitDuplicate));
    }

    @Test
    public void testGetAlliancesEmpty() {
        assertEquals(board.getAlliances().size(), 0);
    }

    @Test
    public void testGetAlliancesOneHero() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(testUnit));

        List<String> receivedAlliances = board.getAlliances();
        assertEquals(receivedAlliances.size(), 2);
        assertEquals(receivedAlliances.get(0), "Vigilant");
        assertEquals(receivedAlliances.get(1), "Knight");
    }

    @Test
    public void testGetAlliancesManyHeroesWithDuplicateAlliances() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Troll");
        alliances2.add("Knight");
        Hero batrider = new Hero("Batrider", 3, 5, "test ability", "test passive",
                2, alliances2);
        assertTrue(board.addHero(batrider));

        List<String> receivedAlliances = board.getAlliances();
        assertEquals(receivedAlliances.size(), 3);
        assertEquals(receivedAlliances.get(0), "Vigilant");
        assertEquals(receivedAlliances.get(1), "Knight");
        assertEquals(receivedAlliances.get(2), "Troll");
    }

    @Test
    public void testGetAlliancesManyHeroesWithoutDuplicateAlliances() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        assertTrue(board.addHero(bristle));

        List<String> receivedAlliances = board.getAlliances();
        assertEquals(receivedAlliances.size(), 4);
        assertEquals(receivedAlliances.get(0), "Vigilant");
        assertEquals(receivedAlliances.get(1), "Knight");
        assertEquals(receivedAlliances.get(2), "Brawny");
        assertEquals(receivedAlliances.get(3), "Savage");
    }

    @Test
    public void testIsFullHeroes() {
        int row = 0;
        int column = 0;
        for(int i = 0; i < Board.MAX_HEROES; i++) {
            Hero testUnit = new Hero("test" + i, row, column, "test ability", "test passive",
                    1, null);
            assertTrue(board.addHero(testUnit));
            column++;
            if (column == Board.MAX_COLUMNS) {
                row++;
                column = 0;
            }

        }
        assertTrue(board.isFullHeroes());
    }

    @Test
    public void testIsNotFullHeroes() {
        assertFalse(board.isFullHeroes());
    }

    @Test
    public void testIsFullItems() {
        int row = 0;
        int column = 0;
        for(int i = 0; i < Board.MAX_ITEMS; i++) {
            Item testUnit = new Item("test" + i, row, column);
            assertTrue(board.addItem(testUnit));
            if (column == Board.MAX_COLUMNS) {
                row++;
                column = 0;
            }
            column++;
        }
        assertTrue(board.isFullItems());
    }

    @Test
    public void testIsNotFullItems() {
        assertFalse(board.isFullItems());
    }

    @Test
    public void testGetTilesWithoutUnits() {
        Placeable[][] tiles = new Placeable[Board.MAX_ROWS][Board.MAX_COLUMNS];
        Placeable[][] receivedTiles = board.getTiles();

        for(int row = 0; row < Board.MAX_ROWS; row++) {
            for(int col = 0; col < Board.MAX_COLUMNS; col++) {
                assertEquals(tiles[row][col], receivedTiles[row][col]);
            }
        }
    }

    @Test
    public void testGetTilesWithUnits() {
        Placeable[][] tiles = new Placeable[Board.MAX_ROWS][Board.MAX_COLUMNS];

        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);

        assertTrue(board.addHero(luna));
        tiles[2][3] = luna;

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);

        assertTrue(board.addHero(bristle));
        tiles[3][5] = bristle;

        Placeable[][] receivedTiles = board.getTiles();

        for(int row = 0; row < Board.MAX_ROWS; row++) {
            for(int col = 0; col < Board.MAX_COLUMNS; col++) {
                assertEquals(tiles[row][col], receivedTiles[row][col]);
            }
        }
    }

    @Test
    public void testGetHeroesEmpty() {
        assertEquals(board.getHeroes().size(), 0);
    }

    @Test
    public void testGetHeroesOne() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances);
        assertTrue(board.addHero(luna));

        List<Hero> heroes = board.getHeroes();
        assertEquals(heroes.size(), 1);
        assertEquals(heroes.get(0), luna);
    }

    @Test
    public void testGetHeroesMany() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        assertTrue(board.addHero(bristle));

        List<Hero> heroes = board.getHeroes();
        assertEquals(heroes.size(), 2);
        assertEquals(heroes.get(0), luna);
        assertEquals(heroes.get(1), bristle);
    }

    @Test
    public void testGetItemsEmpty() {
        assertEquals(board.getItems().size(), 0);
    }

    @Test
    public void testGetItemsOne() {
        Item item1 = new Item("test item", 2, 3);
        assertTrue(board.addItem(item1));

        List<Item> items = board.getItems();
        assertEquals(items.size(), 1);
        assertEquals(items.get(0), item1);
    }

    @Test
    public void testGetItemsMany() {
        Item item1 = new Item("test item", 2, 3);
        assertTrue(board.addItem(item1));

        Item item2 = new Item("test item 2", 3, 5);
        assertTrue(board.addItem(item2));

        List<Item> items = board.getItems();
        assertEquals(items.size(), 2);
        assertEquals(items.get(0), item1);
        assertEquals(items.get(1), item2);
    }

    @Test
    public void testToStringEmptyBoard() {
        String boardString = "x x x x x x x x \n" +
                       "x x x x x x x x \n" +
                       "x x x x x x x x \n" +
                       "x x x x x x x x \n";
        assertEquals(board.toString(), boardString);
    }

    @Test
    public void testToStringOneUnit() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        String boardString = "x x x x x x x x \n" +
                "x x x x x x x x \n" +
                "x x x 0 x x x x \n" +
                "x x x x x x x x \n";
        assertEquals(board.toString(), boardString);
    }

    @Test
    public void testToStringMultipleHeroes() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        assertTrue(board.addHero(bristle));

        String boardString = "x x x x x x x x \n" +
                "x x x x x x x x \n" +
                "x x x 0 x x x x \n" +
                "x x x x x 1 x x \n";
        assertEquals(board.toString(), boardString);
    }

    @Test
    public void testToStringMultipleHeroesAndItems() {
        Item item1 = new Item("Barricade", 0, 1);
        assertTrue(board.addItem(item1));

        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        assertTrue(board.addHero(luna));

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        assertTrue(board.addHero(bristle));

        String boardString = "x b x x x x x x \n" +
                "x x x x x x x x \n" +
                "x x x 0 x x x x \n" +
                "x x x x x 1 x x \n";
        assertEquals(board.toString(), boardString);
    }

}
