package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.properties.ParchmentProperties;

public class Parchment extends ConsumableItem {

    public Parchment(ParchmentProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Parchment((ParchmentProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        Spell spell = ((ParchmentProperties) properties).getSpell();
        Spell[] spells = character.getSpells();

        boolean hasSpell = false;

        for (int i = 0; i < spells.length; i++) {
            if (spell.equals(spells[i])) {
                hasSpell = true;
                break;
            }
        }

        if (!hasSpell) {
            super.use(character);
            character.addSpell(((ParchmentProperties) properties).getSpell());
        }

    }

    /**
     * Retrieves the parchment's spell.
     *
     * @return the parchment's spell
     */
    public Spell getSpell() {
        return ((ParchmentProperties) properties).getSpell();
    }

}
