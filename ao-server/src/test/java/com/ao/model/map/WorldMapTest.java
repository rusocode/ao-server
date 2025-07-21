

package com.ao.model.map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;

public class WorldMapTest {

	private WorldMap map;

	@Before
	public void setUp() {
		map = new WorldMap("foo", 1, (short) 1, new Tile[] {});
	}

	@Test
	public void testGetTile() {
		final Tile t = new Tile(true, true, false, Trigger.NONE, null, null, null);
		final Tile t2 = new Tile(false, true, false, Trigger.NONE, null, null, null);
		final Tile t3 = new Tile(true, true, false, Trigger.NONE, null, null, null);

		map = new WorldMap("foo", 1, (short) 1, new Tile[] {t, t2, t3});

		assertEquals(map.getTile(0, 0), t);
		assertEquals(map.getTile(1, 0), t2);
		assertEquals(map.getTile(2, 0), t3);
	}

	@Test
	public void testEquals() {
		final WorldMap map2 = new WorldMap("foo", 1, (short) 1, new Tile[] {});
		final WorldMap map3 = new WorldMap("foo", 2, (short) 1, new Tile[] {});
		final WorldMap map4 = new WorldMap("asd", 1, (short) 1, new Tile[] {});
		final WorldMap map5 = new WorldMap("asd", 3, (short) 1, new Tile[] {null});
		final WorldMap map6 = new WorldMap(null, 4, (short) 1, new Tile[] {});

		assertEquals(map, map);
		assertEquals(map2, map);
		assertThat(map, not(equalTo(map3)));
		assertThat(map, not(equalTo(map4)));
		assertThat(map, not(equalTo(map5)));
		assertThat(map6, not(equalTo(map)));
		assertThat(map, not(equalTo(null)));
		assertThat(map, not(equalTo(new String())));
	}

	@Test
	public void testGetName() {
		assertEquals("foo", map.getName());
	}

	@Test
	public void testGetId() {
		assertEquals(1, map.getId());
	}

	@Test
	public void testGetCharactersNearby() {
		final Tile[] tiles = new Tile[2600];
		for (int i = 0; i < 2600; i++) {
			tiles[i] = new Tile(true, true, false, Trigger.NONE, null, null, null);
		}

		map = new WorldMap("foo", 1, (short) 1, tiles);

		map.getTile(8, 8).setCharacter(mock(Character.class));
		map.getTile(15, 8).setCharacter(mock(Character.class));
		map.getTile(8, 15).setCharacter(mock(Character.class));
		map.getTile(15, 15).setCharacter(mock(Character.class));
		map.getTile(5, 5).setCharacter(mock(Character.class));
		map.getTile(25, 5).setCharacter(mock(Character.class)); // This one shouldn't be in the return.

		final List<Character> chars = map.getCharactersNearby(7, 7);

		assertEquals(5, chars.size());
		assertThat(chars, hasItem(map.getTile(8, 8).getCharacter()));
		assertThat(chars, hasItem(map.getTile(15, 8).getCharacter()));
		assertThat(chars, hasItem(map.getTile(8, 15).getCharacter()));
		assertThat(chars, hasItem(map.getTile(15, 15).getCharacter()));
		assertThat(chars, hasItem(map.getTile(5, 5).getCharacter()));
	}

}
