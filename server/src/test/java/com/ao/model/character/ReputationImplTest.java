package com.ao.model.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReputationImplTest {

    private static final int ASSASIN_POINTS = 500;
    private static final int BANDIT_POINTS = 500;
    private static final int BURGEOIS_POINTS = 500;
    private static final int THIEFF_POINTS = 500;
    private static final int NOBLE_POINTS = 500;
    private static final boolean BELONGS_TO_FACTION = false;

    private Reputation rep;

    @BeforeEach
    public void setUp() {
        rep = new ReputationImpl(ASSASIN_POINTS, BANDIT_POINTS, BURGEOIS_POINTS, THIEFF_POINTS, NOBLE_POINTS, BELONGS_TO_FACTION);
    }

    @Test
    public void testAddToAssassin() {
        rep.addToAssassin(10);
        assertThat(rep.getAssassin()).isEqualTo(ASSASIN_POINTS + 10);
    }

    @Test
    public void testAddToBandit() {
        rep.addToBandit(10);
        assertThat(rep.getBandit()).isEqualTo(BANDIT_POINTS + 10);
    }

    @Test
    public void testAddToBourgeois() {
        rep.addToBourgeois(10);
        assertThat(rep.getBourgeois()).isEqualTo(BURGEOIS_POINTS + 10);
    }

    @Test
    public void testAddToNoblePoints() {
        rep.addToNoble(10);
        assertThat(rep.getNoble()).isEqualTo(NOBLE_POINTS + 10);
    }

    @Test
    public void testAddToThief() {
        rep.addToThief(10);
        assertThat(rep.getThief()).isEqualTo(THIEFF_POINTS + 10);
    }

    @Test
    public void testGetAlignment() {
        assertThat(rep.getAlignment()).isSameAs(Alignment.CRIMINAL);
        rep.addToNoble(600);
        assertThat(rep.getAlignment()).isSameAs(Alignment.CITIZEN);
    }

    @Test
    public void testGetAssassin() {
        assertThat(rep.getAssassin()).isEqualTo(ASSASIN_POINTS);
    }

    @Test
    public void testGetBandit() {
        assertThat(rep.getBandit()).isEqualTo(BANDIT_POINTS);
    }

    @Test
    public void testGetBourgeois() {
        assertThat(rep.getBourgeois()).isEqualTo(BURGEOIS_POINTS);
    }

    @Test
    public void testGetThief() {
        assertThat(rep.getThief()).isEqualTo(THIEFF_POINTS);
    }

    @Test
    public void testGetNoble() {
        assertThat(rep.getNoble()).isEqualTo(NOBLE_POINTS);
    }

    @Test
    public void testBelongsToFaction() {
        assertThat(rep.belongsToFaction()).isFalse();
    }

    @Test
    public void testSetBelongsToFaction() {
        rep.setBelongsToFaction(!BELONGS_TO_FACTION);
        assertThat(rep.belongsToFaction()).isEqualTo(!BELONGS_TO_FACTION);
    }

}
