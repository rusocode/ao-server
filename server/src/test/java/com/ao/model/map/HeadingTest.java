package com.ao.model.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeadingTest {

    @Test
    void get_zeroIndex_returnsNorth() {
        assertThat(Heading.get((byte) 0)).isEqualTo(Heading.NORTH);
    }

    @Test
    void get_oneIndex_returnsEast() {
        assertThat(Heading.get((byte) 1)).isEqualTo(Heading.EAST);
    }

    @Test
    void get_twoIndex_returnsSouth() {
        assertThat(Heading.get((byte) 2)).isEqualTo(Heading.SOUTH);
    }

    @Test
    void get_threeIndex_returnsWest() {
        assertThat(Heading.get((byte) 3)).isEqualTo(Heading.WEST);
    }

    @Test
    void get_negativeIndex_returnsNull() {
        assertThat(Heading.get((byte) -1)).isNull();
    }

    @Test
    void get_indexOutOfBounds_returnsNull() {
        assertThat(Heading.get((byte) 4)).isNull();
    }

}