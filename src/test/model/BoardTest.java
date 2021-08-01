package model;

import exceptions.*;
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
        Hero testUnit = new Hero("test", 2, 3,"test ability", "test passive",
                2, alliances);
        try {
            board.addHero(testUnit);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }
        try {
            assertEquals(board.getUnit(2, 3), testUnit);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testGetUnitWithoutUnit() {
        try {
            assertEquals(board.getUnit(2, 3), null);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testGetUnitOutsideColumnRange() {
        try {
            assertEquals(board.getUnit(2, 8), null);
        } catch (InvalidRowException e) {
            fail("Unexpected InvalidColumnException");
        } catch (InvalidColumnException e) {
            // do nothing
        }
        try {
            assertEquals(board.getUnit(2, -1), null);
        } catch (InvalidRowException e) {
            fail("Unexpected InvalidColumnException");
        } catch (InvalidColumnException e) {
            // do nothing
        }
    }

    @Test
    public void testGetUnitOutsideRowRange() {
        try {
            assertEquals(board.getUnit(4, 3), null);
        } catch (InvalidRowException e) {
            // do nothing
        } catch (InvalidColumnException e) {
            fail("Unexpected InvalidColumnException");
        }
        try {
            assertEquals(board.getUnit(-1, 3), null);
        } catch (InvalidRowException e) {
            // do nothing
        } catch (InvalidColumnException e) {
            fail("Unexpected InvalidColumnException");
        }
    }

    @Test
    public void testRemoveHero() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        try {
            board.addHero(testUnit);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }
        try {
            assertEquals(board.removeHero(2, 3), testUnit);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
        try {
            assertEquals(board.getUnit(2, 3), null);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testRemoveHeroWithNoHeroes() {
        try {
            assertEquals(board.removeHero(2, 3), null);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testNotRemoveHeroWithHeroes() {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        try {
            assertEquals(board.removeHero(3, 5), bristle);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testRemoveUnitOutsideColumnRange() {
        try {
            assertEquals(board.removeHero(2, 8), null);
        } catch (InvalidRowException e) {
            fail("Unexpected InvalidColumnException");
        } catch (InvalidColumnException e) {
            // do nothing
        }
        try {
            assertEquals(board.removeHero(2, -1), null);
        } catch (InvalidRowException e) {
            fail("Unexpected InvalidColumnException");
        } catch (InvalidColumnException e) {
            // do nothing
        }
    }

    @Test
    public void testRemoveUnitOutsideRowRange() {
        try {
            assertEquals(board.removeHero(4, 3), null);
        } catch (InvalidRowException e) {
            // do nothing
        } catch (InvalidColumnException e) {
            fail("Unexpected InvalidColumnException");
        }
        try {
            assertEquals(board.removeHero(-1, 3), null);
        } catch (InvalidRowException e) {
            // do nothing
        } catch (InvalidColumnException e) {
            fail("Unexpected InvalidColumnException");
        }
    }

    @Test
    public void testRemoveItem() {
        Item testItem = new Item("test", 2, 3);
        try {
            board.addItem(testItem);
        } catch (AddUnitException e) {
            fail("Unexpected error with adding unit");
        }
        assertEquals(board.removeItem(2, 3), testItem);
        try {
            assertEquals(board.getUnit(2, 3), null);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testRemoveItemWithNoItems() {
        assertEquals(board.removeItem(2, 3), null);
    }

    @Test
    public void testNotRemoveItemWithItems() {
        Item item1 = new Item("test item", 2, 3);
        try {
            board.addItem(item1);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        Item item2 = new Item("test item 2", 3, 5);
        try {
            board.addItem(item2);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        assertEquals(board.removeItem(3, 5), item2);
    }

    @Test
    public void testMoveUnit() {
        Item testItem = new Item("test", 2, 3);
        try {
            board.addItem(testItem);
        } catch (AddUnitException e) {
            fail("Unexpected error with adding unit");
        }
        try {
            assertEquals(board.getUnit(2, 3), testItem);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }

        board.moveUnit(testItem, 3, 5);
        try {
            assertEquals(board.getUnit(3, 5), testItem);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
        try {
            assertEquals(board.getUnit(2, 3), null);
        } catch (InvalidTileException e) {
            fail("Unexpected InvalidTileException");
        }
    }

    @Test
    public void testAddHeroSuccessful() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        try {
            board.addHero(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }
    }

    @Test
    public void testAddHeroFailureAlreadyOccupied() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);

        try {
            board.addHero(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }

        try {
            board.addHero(testUnit);
            fail("No exception was thrown");
        } catch (TileOccupiedException e) {
            // do nothing
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }
    }

    @Test
    public void testAddHeroFailureDuplicate() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Vigilant");
        alliances.add("Knight");
        Hero testUnit = new Hero("test", 2, 3, "test ability", "test passive",
                2, alliances);
        try {
            board.addHero(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }

        Hero testUnitDuplicate = new Hero("test", 3, 5, "test ability", "test passive",
                2, alliances);
        try {
            board.addHero(testUnitDuplicate);
            fail("No exception was thrown");
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            // do nothing
        }
    }
    @Test
    public void testAddItemSuccessful() {
        Item testUnit = new Item("test", 2, 3);
        try {
            board.addItem(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }
    }

    @Test
    public void testAddItemFailureAlreadyOccupied() {
        Item testUnit = new Item("test", 2, 3);
        try {
            board.addItem(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }

        try {
            board.addItem(testUnit);
            fail("No exception was thrown");
        } catch (TileOccupiedException e) {
            // do nothing
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }
    }

    @Test
    public void testAddItemFailureDuplicate() {
        Item testUnit = new Item("test", 2, 3);
        try {
            board.addItem(testUnit);
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            fail("Caught UnitAlreadyOnBoardException");
        }

        Item testUnitDuplicate = new Item("test", 3, 5);
        try {
            board.addItem(testUnitDuplicate);
            fail("No exception was thrown");
        } catch (TileOccupiedException e) {
            fail("Caught TileOccupiedException");
        } catch (UnitAlreadyOnBoardException e) {
            // do nothing
        }
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
        try {
            board.addHero(testUnit);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Troll");
        alliances2.add("Knight");
        Hero batrider = new Hero("Batrider", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(batrider);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
            try {
                board.addHero(testUnit);
            } catch (AddUnitException e) {
                fail("Unexcepted error with adding unit");
            }
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
            try {
                board.addItem(testUnit);
            } catch (AddUnitException e) {
                fail("Unexcepted error with adding unit");
            }
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

        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }
        tiles[2][3] = luna;

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);

        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }
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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addItem(item1);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<Item> items = board.getItems();
        assertEquals(items.size(), 1);
        assertEquals(items.get(0), item1);
    }

    @Test
    public void testGetItemsMany() {
        Item item1 = new Item("test item", 2, 3);
        try {
            board.addItem(item1);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        Item item2 = new Item("test item 2", 3, 5);
        try {
            board.addItem(item2);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

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
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        String boardString = "x x x x x x x x \n" +
                "x x x x x x x x \n" +
                "x x x 0 x x x x \n" +
                "x x x x x 1 x x \n";
        assertEquals(board.toString(), boardString);
    }

    @Test
    public void testToStringMultipleHeroesAndItems() {
        Item item1 = new Item("Barricade", 0, 1);
        try {
            board.addItem(item1);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Vigilant");
        alliances1.add("Knight");
        Hero luna = new Hero("Luna", 2, 3, "test ability", "test passive",
                2, alliances1);
        try {
            board.addHero(luna);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Brawny");
        alliances2.add("Savage");
        Hero bristle = new Hero("Bristle", 3, 5, "test ability", "test passive",
                2, alliances2);
        try {
            board.addHero(bristle);
        } catch (AddUnitException e) {
            fail("Unexcepted error with adding unit");
        }

        String boardString = "x b x x x x x x \n" +
                "x x x x x x x x \n" +
                "x x x 0 x x x x \n" +
                "x x x x x 1 x x \n";
        assertEquals(board.toString(), boardString);
    }

}
