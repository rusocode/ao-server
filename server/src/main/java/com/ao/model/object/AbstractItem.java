package com.ao.model.object;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.Reputation;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.properties.ItemProperties;

import java.util.List;

/**
 * Abstract implementation of an item provides most functionality.
 */

public abstract class AbstractItem extends AbstractObject implements Item {

    protected static final int MAX_STACKED_ITEMS = 10000;

    protected int amount;

    public AbstractItem(final ItemProperties properties, final int amount) {
        super(properties);
        this.amount = amount;
        if (this.amount > MAX_STACKED_ITEMS) this.amount = MAX_STACKED_ITEMS;
        else if (this.amount < 0) this.amount = 0;
    }

    @Override
    public int addAmount(final int amount) {
        if (this.amount + amount <= MAX_STACKED_ITEMS) this.amount += amount;
        else this.amount = MAX_STACKED_ITEMS;
        return this.amount;
    }

    @Override
    public boolean canBeUsedBy(final Race race, final Gender gender, final UserArchetype archetype, final Reputation reputation) {

        // Check if the archetype can use this item
        List<UserArchetype> forbiddenArchetypes = ((ItemProperties) properties).getForbiddenArchetypes();

        if (forbiddenArchetypes != null && forbiddenArchetypes.contains(archetype)) return false;

        // Check if the race can use this item
        List<Race> forbiddenRaces = ((ItemProperties) properties).getForbiddenRaces();

        // TODO Aunque mejor seria verificar si la lista de razas prohibidas esta vacia y no si es distinta a null
        if (forbiddenRaces != null && forbiddenRaces.contains(race)) return false;

        // TODO Check by gender and reputation

        return true;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getValue() {
        return ((ItemProperties) properties).getValue();
    }

    @Override
    public boolean isNewbie() {
        return ((ItemProperties) properties).isNewbie();
    }

    @Override
    public boolean canBeStolen() {
        return true; // Everything can be stolen by default
    }

    @Override
    public abstract Item clone();

    public boolean isFalls() {
        return ((ItemProperties) properties).isFalls();
    }

    public boolean isNoLog() {
        return ((ItemProperties) properties).isNoLog();
    }

    public boolean isRespawnable() {
        return ((ItemProperties) properties).isRespawnable();
    }

}
