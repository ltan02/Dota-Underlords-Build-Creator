package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {

    Hero stormSpirit;

    @BeforeEach
    public void runBefore() {
        List<String> alliances = new ArrayList<>();
        alliances.add("Spirit");
        alliances.add("Mage");
        stormSpirit = new Hero("Storm Spirit", 1, 2, "Ball Lightning",
                "Storm Elemental", 2, alliances);
    }

    @Test
    public void testGetAlliances() {
        List<String> alliances = stormSpirit.getAlliances();
        assertEquals(alliances.size(), 2);
        assertEquals(alliances.get(0), "Spirit");
        assertEquals(alliances.get(1), "Mage");
    }

    @Test
    public void testGetAbility() {
        assertEquals(stormSpirit.getAbility(), "Ball Lightning");
    }

    @Test
    public void testGetPassive() {
        assertEquals(stormSpirit.getPassive(), "Storm Elemental");
    }

    @Test
    public void testGetTier() {
        assertEquals(stormSpirit.getTier(), 2);
    }

}
