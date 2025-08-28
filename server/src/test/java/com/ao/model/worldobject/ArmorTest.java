package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ArmorTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Armor armor1;
    private Armor armor2;

    @BeforeEach
    public void setUp() throws Exception {
        DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.ARMOR, 1, "Leather Armor", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        armor1 = new Armor(props1, 5);

        DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.ARMOR, 1, "Leather Armor", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        armor2 = new Armor(props2, 1);

        object = armor1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        Armor clone = (Armor) armor1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(armor1.amount);
        assertThat(clone.properties).isEqualTo(armor1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(armor1);


        Armor clone2 = (Armor) armor2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(armor2.amount);
        assertThat(clone2.properties).isEqualTo(armor2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(armor2);
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);

        // nothing should happen
        armor1.use(character);
        armor2.use(character);
    }

}
