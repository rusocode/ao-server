package com.ao.model.character;

/**
 * Default implementation of the reputation system.
 */

public class ReputationImpl implements Reputation {

    private int assassinPoints;
    private int banditPoints;
    private int bourgeoisPoints;
    private int thiefPoints;
    private int noblePoints;
    private boolean belongsToFaction;

    /**
     * Creates a new reputation instance.
     *
     * @param assassinPoints   assassin points
     * @param banditPoints     bandit points
     * @param bourgeoisPoints  bourgeois points
     * @param thieffPoints     thieff points
     * @param noblePoints      noble points
     * @param belongsToFaction wether the character belongs to a faction or not
     */
    public ReputationImpl(int assassinPoints, int banditPoints, int bourgeoisPoints, int thieffPoints, int noblePoints,
                          boolean belongsToFaction) {
        this.assassinPoints = assassinPoints;
        this.banditPoints = banditPoints;
        this.bourgeoisPoints = bourgeoisPoints;
        this.thiefPoints = thieffPoints;
        this.noblePoints = noblePoints;
        this.belongsToFaction = belongsToFaction;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#addToAssassin(int)
     */
    @Override
    public void addToAssassin(int points) {
        assassinPoints += points;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#addToBandit(int)
     */
    @Override
    public void addToBandit(int points) {
        banditPoints += points;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#addToBourgeois(int)
     */
    @Override
    public void addToBourgeois(int points) {
        bourgeoisPoints += points;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#addToNoble(int)
     */
    @Override
    public void addToNoble(int points) {
        noblePoints += points;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#addToThief(int)
     */
    @Override
    public void addToThief(int points) {
        thiefPoints += points;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getAlignment()
     */
    @Override
    public Alignment getAlignment() {
        long ret = bourgeoisPoints + noblePoints - assassinPoints - banditPoints - thiefPoints;
        return ret < 0 ? Alignment.CRIMINAL : Alignment.CITIZEN;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getAssassin()
     */
    @Override
    public int getAssassin() {
        return assassinPoints;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getBandit()
     */
    @Override
    public int getBandit() {
        return banditPoints;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getBourgeois()
     */
    @Override
    public int getBourgeois() {
        return bourgeoisPoints;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getThief()
     */
    @Override
    public int getThief() {
        return thiefPoints;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#getNoble()
     */
    @Override
    public int getNoble() {
        return noblePoints;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#belongsToFaction()
     */
    @Override
    public boolean belongsToFaction() {
        return belongsToFaction;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.character.Reputation#setBelongsToFaction(boolean)
     */
    @Override
    public void setBelongsToFaction(boolean belongs) {
        belongsToFaction = belongs;
    }

}
