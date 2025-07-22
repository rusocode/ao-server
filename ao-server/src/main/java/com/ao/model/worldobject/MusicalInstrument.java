package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.MusicalInstrumentProperties;

import java.util.List;

/**
 * A musical instrument.
 */

public class MusicalInstrument extends AbstractItem {

    /**
     * Creates a new MusicalInstrument instance.
     *
     * @param properties The item's properties.
     * @param amount     The item's amount.
     */
    public MusicalInstrument(MusicalInstrumentProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new MusicalInstrument((MusicalInstrumentProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Instruments do nothing to the character when used.
    }

    /**
     * Retrieves a random sound to be played.
     *
     * @return a random sound to play
     */
    public int getSoundToPlay() {
        List<Integer> sounds = ((MusicalInstrumentProperties) properties).getSounds();
        return sounds.get((int) Math.floor(Math.random() * sounds.size()));
    }

}
