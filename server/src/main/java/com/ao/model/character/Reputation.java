package com.ao.model.character;

public interface Reputation {

    Alignment getAlignment();

    int getBandit();

    void addToBandit(int points);

    int getAssassin();

    void addToAssassin(int points);

    int getNoble();

    void addToNoble(int points);

    int getBourgeois();

    void addToBourgeois(int points);

    int getThief();

    void addToThief(int points);

    /**
     * Checks if the reputation belongs to any faction.
     *
     * @return True if the reputation belongs to a faction, false otherwise
     */
    boolean belongsToFaction();

    /**
     * Sets whether the reputation belongs to a faction or not.
     *
     * @param belongs Determines if the reputation belongs to a faction
     */
    void setBelongsToFaction(boolean belongs);

}
