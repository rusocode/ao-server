package com.ao.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    private static final byte X_POSITION = 50;
    private static final byte Y_POSITION = 50;

    private Position pos;

    @BeforeEach
    public void setUp() {
        pos = new Position(X_POSITION, Y_POSITION, 1);
    }

    @Test
    public void testAddToX() {
        pos.addToX(7);
        assertThat(pos.getX()).isEqualTo(X_POSITION + 7);
        pos.addToX(-6);
        assertThat(pos.getX()).isEqualTo(X_POSITION + 1);
    }

    @Test
    public void testAddToY() {
        pos.addToY(7);
        assertThat(pos.getY()).isEqualTo(Y_POSITION + 7);
        pos.addToY(-6);
        assertThat(pos.getY()).isEqualTo(Y_POSITION + 1);
    }

    @Test
    public void testGetDistance() {
        Position anotherPos = new Position((byte) (X_POSITION + 20), (byte) (Y_POSITION + 20), 1);
        assertThat(pos.getDistance(anotherPos)).isEqualTo(40);
    }

    @Test
    public void testInVisionRange() {
        Position anotherPos = new Position((byte) (X_POSITION + 20), (byte) (Y_POSITION + 20), pos.getMap());
        assertThat(pos.inVisionRange(anotherPos)).isFalse();
        anotherPos.setX((byte) (X_POSITION + 5));
        anotherPos.setY((byte) (Y_POSITION + 5));
        assertThat(pos.inVisionRange(anotherPos)).isTrue();
        anotherPos.setMap(2);
        assertThat(pos.inVisionRange(anotherPos)).isFalse();
    }

}
