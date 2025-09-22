package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.Reputation;
import com.ao.model.character.archetype.UserArchetype;

public interface Item extends Object, Cloneable {

    /**
     * Retrieves the item's amount.
     *
     * @return the amount of items put together
     */
    int getAmount();

    /**
     * Attempts to use the item.
     *
     * @param character the character using this item
     */
    void use(Character character);

    /**
     * Adds (or subtracts if the given amount is negative) an amount of the item
     *
     * @param amount references to the amount to be added (negative to subtract)
     * @return the new amount of the item
     */
    int addAmount(int amount);

    /**
     * Checks if the item can be used/equipped given a race, gender, archetype and reputation.
     *
     * @param race       race attempting to use the item
     * @param gender     gender of the character attempting to use the item
     * @param archetype  archetype attempting to use the item
     * @param reputation reputation of the character attempting to use the item
     * @return true if the item can be used, false otherwise
     */
    boolean canBeUsedBy(Race race, Gender gender, UserArchetype archetype, Reputation reputation);

    /**
     * Gets a deep clone of the item
     *
     * @return a copy of the item
     */
    Item clone();

    /**
     * Retrieves the item's value
     *
     * @return the item's value
     */
    int getValue();

    /**
     * Checks if the item is newbie.
     *
     * @return whether the item is newbie or not
     */
    boolean isNewbie();

    /**
     * Checks if the item can be stolen.
     *
     * @return true if the item can be stolen, false otherwise
     */
    boolean canBeStolen();

}
