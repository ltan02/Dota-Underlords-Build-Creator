package persistence;

import model.Hero;
import model.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkHero(Hero hero, String name, int row, int column, String ability, String passive, int tier,
                             List<String> alliances) {
        assertEquals(name, hero.getName());
        assertEquals(row, hero.getRow());
        assertEquals(column, hero.getColumn());
        assertEquals(ability, hero.getAbility());
        assertEquals(passive, hero.getPassive());
        assertEquals(tier, hero.getTier());
        assertEquals(alliances.size(), hero.getAlliances().size());
        for (int i = 0; i < alliances.size(); i++) {
            assertEquals(alliances.get(i), hero.getAlliances().get(i));
        }
    }

    protected void checkItem(Item item, String name, int row, int column) {
        assertEquals(name, item.getName());
        assertEquals(row, item.getRow());
        assertEquals(column, item.getColumn());
    }

}
