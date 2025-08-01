package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.spell.Spell;
import com.ao.model.spell.effect.Effect;
import com.ao.model.worldobject.properties.ParchmentProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ParchmentTest extends AbstractItemTest {

    private Parchment parchment1;
    private Parchment parchment2;
    private Spell spell1;
    private Spell spell2;

    @BeforeEach
    public void setUp() throws Exception {
        spell1 = new Spell(1, new Effect[]{}, 0, 0, 0, "foo", "foo", false, 1, 2, "OHL VOR PEK");
        final ParchmentProperties props1 = new ParchmentProperties(WorldObjectType.PARCHMENT, 1, "Dardo Mágico", 1, 300, null, null, false, false, false, false, spell1);
        parchment1 = new Parchment(props1, 1);

        spell2 = new Spell(2, new Effect[]{}, 0, 0, 0, "foo", "foo", false, 1, 2, "Rahma Nañarak O'al");
        final ParchmentProperties props2 = new ParchmentProperties(WorldObjectType.PARCHMENT, 1, "Terrible hambre de Igor", 100, 200, null, null, false, false, false, false, spell2);
        parchment2 = new Parchment(props2, 5);

        object = parchment1;
        objectProps = props1;
        ammount = 1;
    }

    @Test
    public void testGetSpell() {
        assertThat(spell1).isEqualTo(parchment1.getSpell());
        assertThat(spell2).isEqualTo(parchment2.getSpell());
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        final Spell[] spells = new Spell[]{};
        when(character.getInventory()).thenReturn(inventory);
        when(character.getSpells()).thenReturn(spells);

        parchment1.use(character);

        // Consumption of parchment1 requires these 2 calls
        verify(inventory).cleanup();
        verify(character).addSpell(spell1);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        final Spell[] spells = new Spell[]{};
        when(character.getInventory()).thenReturn(inventory);
        when(character.getSpells()).thenReturn(spells);

        parchment2.use(character);

        // Consumption of parchment1 requires just a call to addSpell
        verify(character).addSpell(spell2);
    }

    @Test
    public void testUseWithASpellAlreadyLearnt() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        final Spell[] spells = new Spell[]{spell1};
        when(character.getInventory()).thenReturn(inventory);
        when(character.getSpells()).thenReturn(spells);

        parchment1.use(character);

        // Consumption of parchment1 already learnt doesn't require any call
        verify(character).getSpells();
        verifyNoMoreInteractions(character, inventory);
    }

    @Test
    public void testClone() {
        final Parchment clone1 = (Parchment) parchment1.clone();

        assertThat(parchment1.getAmount()).isEqualTo(clone1.getAmount());
        assertThat(parchment1.getSpell()).isEqualTo(clone1.getSpell());
        assertThat(parchment1.properties).isEqualTo(clone1.properties);
        assertThat(parchment1).isNotSameAs(clone1);

        final Parchment clone2 = (Parchment) parchment2.clone();

        assertThat(parchment2.getAmount()).isEqualTo(clone2.getAmount());
        assertThat(parchment2.getSpell()).isEqualTo(clone2.getSpell());
        assertThat(parchment2.properties).isEqualTo(clone2.properties);
        assertThat(parchment2).isNotSameAs(clone2);
    }

}
