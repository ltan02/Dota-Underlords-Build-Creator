package persistence;

import exceptions.InvalidTileException;
import exceptions.TileOccupiedException;
import exceptions.UnitAlreadyOnBoardException;
import model.Board;
import model.Hero;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test methods taken from JsonSerializationDemo. Code was taken from here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Board b = new Board("Test Board");
            JsonWriter writer = new JsonWriter("./data/json/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        try {
            Board b = new Board("Test Board");
            JsonWriter writer = new JsonWriter("./data/json/testWriterEmptyBoard.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/json/testWriterEmptyBoard.json");
            b = reader.read();
            assertEquals("Test Board", b.getBoardName());
            assertEquals(0, b.getHeroes().size());
            assertEquals(0, b.getItems().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Board b = new Board("Test Board");
            addUnitsToBoard(b);
            JsonWriter writer = new JsonWriter("./data/json/testWriterGeneralBoard.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/json/testWriterGeneralBoard.json");
            b = reader.read();
            assertEquals("Test Board", b.getBoardName());
            checkHeroes(b);
            checkItems(b);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void addUnitsToBoard(Board b) {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Scaled");
        alliances1.add("Assassin");
        Hero slark = new Hero("Slark", 1, 3, null, "Essence Shift", 3, alliances1);

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Troll");
        alliances2.add("Knight");
        Hero batrider = new Hero("Batrider", 0, 4, "Sticky Napalm", null, 1, alliances2);

        Item testItem = new Item("Target Buddy", 3, 7);

        try {
            b.addHero(slark);
            b.addHero(batrider);
            b.addItem(testItem);
        } catch (TileOccupiedException e) {
            fail("TileOccupiedException should not have been thrown");
        } catch (UnitAlreadyOnBoardException e) {
            fail("UnitAlreadyOnBoardException should not have been thrown");
        }
    }

    void checkHeroes(Board b) {
        List<String> alliances1 = new ArrayList<>();
        alliances1.add("Scaled");
        alliances1.add("Assassin");

        List<String> alliances2 = new ArrayList<>();
        alliances2.add("Troll");
        alliances2.add("Knight");

        List<Hero> heroes = b.getHeroes();
        assertEquals(2, heroes.size());
        checkHero((Hero) b.getTiles()[0][4], "Batrider", 0, 4, "Sticky Napalm", null, 1, alliances2);
        checkHero((Hero) b.getTiles()[1][3], "Slark", 1, 3, null, "Essence Shift", 3, alliances1);
        assertEquals(4, b.getAlliances().size());
        assertTrue(b.getAlliances().contains("Scaled"));
        assertTrue(b.getAlliances().contains("Assassin"));
        assertTrue(b.getAlliances().contains("Troll"));
        assertTrue(b.getAlliances().contains("Knight"));
    }

    void checkItems(Board b) {
        assertEquals(1, b.getItems().size());
        checkItem((Item) b.getTiles()[3][7], "Target Buddy", 3, 7);
    }

}
