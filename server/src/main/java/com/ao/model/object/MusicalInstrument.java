package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.MusicalInstrumentProperties;

import java.util.List;

public class MusicalInstrument extends AbstractItem {

    public MusicalInstrument(MusicalInstrumentProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new MusicalInstrument((MusicalInstrumentProperties) properties, amount);
    }

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
