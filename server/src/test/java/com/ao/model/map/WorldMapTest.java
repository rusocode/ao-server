package com.ao.model.map;

import com.ao.model.character.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

public class WorldMapTest {

    private WorldMap map;

    @BeforeEach
    public void setUp() {
        map = new WorldMap("foo", 1, (short) 1, new Tile[]{});
    }

    @Test
    public void testGetTile() {
        final Tile t = new Tile(true, true, false, Trigger.NONE, null, null, null);
        final Tile t2 = new Tile(false, true, false, Trigger.NONE, null, null, null);
        final Tile t3 = new Tile(true, true, false, Trigger.NONE, null, null, null);

        map = new WorldMap("foo", 1, (short) 1, new Tile[]{t, t2, t3});

        assertThat(t).isEqualTo(map.getTile(0, 0));
        assertThat(t2).isEqualTo(map.getTile(1, 0));
        assertThat(t3).isEqualTo(map.getTile(2, 0));
    }

    @Test
    public void testEquals() {
        final WorldMap map2 = new WorldMap("foo", 1, (short) 1, new Tile[]{});
        final WorldMap map3 = new WorldMap("foo", 2, (short) 1, new Tile[]{});
        final WorldMap map4 = new WorldMap("asd", 1, (short) 1, new Tile[]{});
        final WorldMap map5 = new WorldMap("asd", 3, (short) 1, new Tile[]{null});
        final WorldMap map6 = new WorldMap(null, 4, (short) 1, new Tile[]{});

        assertThat(map).isEqualTo(map);
        assertThat(map).isEqualTo(map2);
        assertThat(map).isNotEqualTo(map3);
        assertThat(map).isNotEqualTo(map3);
        assertThat(map).isNotEqualTo(map4);
        assertThat(map).isNotEqualTo(map5);
        assertThat(map6).isNotEqualTo(map);
        assertThat(map).isNotEqualTo(null);
        assertThat(map).isNotEqualTo(new String());
    }

    @Test
    public void testGetName() {
        assertThat(map.getName()).isEqualTo("foo");
    }

    @Test
    public void testGetId() {
        assertThat(map.getId()).isEqualTo(1);
    }

    @Test
    public void testGetCharactersNearby() {
        final Tile[] tiles = new Tile[2600];
        for (int i = 0; i < 2600; i++)
            tiles[i] = new Tile(true, true, false, Trigger.NONE, null, null, null);

        map = new WorldMap("foo", 1, (short) 1, tiles);

        map.getTile(8, 8).setCharacter(mock(Character.class));
        map.getTile(15, 8).setCharacter(mock(Character.class));
        map.getTile(8, 15).setCharacter(mock(Character.class));
        map.getTile(15, 15).setCharacter(mock(Character.class));
        map.getTile(5, 5).setCharacter(mock(Character.class));
        map.getTile(25, 5).setCharacter(mock(Character.class)); // This one shouldn't be in the return.

        final List<Character> chars = map.getCharactersNearby(7, 7);

        assertThat(chars.size()).isEqualTo(5);
        assertThat(chars).contains(map.getTile(8, 8).getCharacter());
        assertThat(chars).contains(map.getTile(15, 8).getCharacter());
        assertThat(chars).contains(map.getTile(8, 15).getCharacter());
        assertThat(chars).contains(map.getTile(15, 15).getCharacter());
        assertThat(chars).contains(map.getTile(5, 5).getCharacter());
    }

}
