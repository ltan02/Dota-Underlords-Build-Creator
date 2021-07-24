package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceableTest {

    Placeable unit;

    @BeforeEach
    public void runBefore() {
        unit = new Placeable("test", 1, 2);
    }

    @Test
    public void testGetName() {
        assertEquals(unit.getName(), "test");
    }

    @Test
    public void testGetRow() {
        assertEquals(unit.getRow(), 1);
    }

    @Test
    public void testGetColumn() {
        assertEquals(unit.getColumn(), 2);
    }

    @Test
    public void testMove() {
        unit.move(2, 4);
        assertEquals(unit.getRow(), 2);
        assertEquals(unit.getRow(), 4);
    }

}
