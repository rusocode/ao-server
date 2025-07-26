package com.ao.model.character;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReputationImplTest {

    private static final int ASSASIN_POINTS = 500;
    private static final int BANDIT_POINTS = 500;
    private static final int BURGEOIS_POINTS = 500;
    private static final int THIEFF_POINTS = 500;
    private static final int NOBLE_POINTS = 500;
    private static final boolean BELONGS_TO_FACTION = false;

    private Reputation rep;

    @Before
    public void setUp() {
        rep = new ReputationImpl(ASSASIN_POINTS, BANDIT_POINTS, BURGEOIS_POINTS, THIEFF_POINTS, NOBLE_POINTS, BELONGS_TO_FACTION);
    }

    @Test
    public void testAddToAssassin() {
        rep.addToAssassin(10);
        assertEquals(ASSASIN_POINTS + 10, rep.getAssassin());
    }

    @Test
    public void testAddToBandit() {
        rep.addToBandit(10);
        assertEquals(BANDIT_POINTS + 10, rep.getBandit());
    }

    @Test
    public void testAddToBourgeois() {
        rep.addToBourgeois(10);
        assertEquals(BURGEOIS_POINTS + 10, rep.getBourgeois());
    }

    @Test
    public void testAddToNoblePoints() {
        rep.addToNoble(10);
        assertEquals(NOBLE_POINTS + 10, rep.getNoble());
    }

    @Test
    public void testAddToThief() {
        rep.addToThief(10);
        assertEquals(THIEFF_POINTS + 10, rep.getThief());
    }

    @Test
    public void testGetAlignment() {
        assertSame(Alignment.CRIMINAL, rep.getAlignment());
        rep.addToNoble(600);
        assertSame(Alignment.CITIZEN, rep.getAlignment());
    }

    @Test
    public void testGetAssassin() {
        assertEquals(ASSASIN_POINTS, rep.getAssassin());
    }

    @Test
    public void testGetBandit() {
        assertEquals(BANDIT_POINTS, rep.getBandit());
    }

    @Test
    public void testGetBourgeois() {
        assertEquals(BURGEOIS_POINTS, rep.getBourgeois());
    }

    @Test
    public void testGetThief() {
        assertEquals(THIEFF_POINTS, rep.getThief());
    }

    @Test
    public void testGetNoble() {
        assertEquals(NOBLE_POINTS, rep.getNoble());
    }

    @Test
    public void testBelongsToFaction() {
        assertFalse(rep.belongsToFaction());
    }

    @Test
    public void testSetBelongsToFaction() {
        rep.setBelongsToFaction(!BELONGS_TO_FACTION);
        assertEquals(!BELONGS_TO_FACTION, rep.belongsToFaction());
    }

}
