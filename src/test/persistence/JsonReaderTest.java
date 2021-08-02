package persistence;

import model.Board;
import model.Hero;
import model.Item;
import model.Placeable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test methods taken from JsonSerializationDemo. Code was taken from here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/json/noSuchFile.json");
        try {
            Board b = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReader reader = new JsonReader("./data/json/testReaderEmptyBoard.json");
        try {
            Board b = reader.read();
            assertEquals("Test Board", b.getBoardName());
            Placeable[][] tiles = b.getTiles();
            for (Placeable[] row : tiles) {
                for (Placeable unit : row) {
                    assertEquals(null, unit);
                }
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBoard() {
        JsonReader reader = new JsonReader("./data/json/testReaderGeneralBoard.json");
        try {
            Board b = reader.read();
            assertEquals("Test Board", b.getBoardName());
            checkHeroes(b);
            checkItems(b);
        } catch (IOException e) {
            fail("Couldn't read from file");
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
