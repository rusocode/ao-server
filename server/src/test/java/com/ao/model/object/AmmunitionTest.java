package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.AmmunitionProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AmmunitionTest extends AbstractEquipableItemTest {

    protected static final int MIN_HIT = 1;
    protected static final int MAX_HIT = 5;

    protected Ammunition ammunition1;
    protected Ammunition ammunition2;

    @BeforeEach
    public void setUp() throws Exception {
        AmmunitionProperties props1 = new AmmunitionProperties(ObjectType.AMMUNITION, 1, "Arrow", 1, 1, null, null, false, false, false, false, 1, MIN_HIT, MAX_HIT);
        ammunition1 = new Ammunition(props1, 5);

        AmmunitionProperties props2 = new AmmunitionProperties(ObjectType.AMMUNITION, 1, "Fire Arrow", 1, 1, null, null, false, false, false, false, 1, MAX_HIT, MAX_HIT);
        ammunition2 = new Ammunition(props2, 1);

        object = ammunition1;
        ammount = 5;
        objectProps = props1;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        Ammunition clone = (Ammunition) ammunition1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(ammunition1.amount); // Se asegura que el valor actual sea igual al valor esperado
        assertThat(clone.properties).isEqualTo(ammunition1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(ammunition1);

        Ammunition clone2 = (Ammunition) ammunition2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(ammunition2.amount);
        assertThat(clone2.properties).isEqualTo(ammunition2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(ammunition2);
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);
        // nothing should happen
        ammunition1.use(character);
        ammunition2.use(character);
    }

    @Test
    public void testGetMinHit() {
        assertThat(ammunition1.getMinHit()).isEqualTo(MIN_HIT);
        assertThat(ammunition2.getMinHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetMaxHit() {
        assertThat(ammunition1.getMaxHit()).isEqualTo(MAX_HIT);
        assertThat(ammunition2.getMaxHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetDamage() {
        int damage = ammunition1.getDamage();
        assertThat(damage).isBetween(MIN_HIT, MAX_HIT);
        assertThat(ammunition2.getDamage()).isEqualTo(MAX_HIT);
    }

}
