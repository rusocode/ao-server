package com.ao.mock;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.model.worldobject.AbstractItem;
import com.ao.model.worldobject.Item;
import com.ao.model.worldobject.WorldObject;
import com.ao.network.Connection;
import com.ao.service.timedevents.TimedEvent;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

/**
 * Centralizes the common mocks creation.
 */

public class MockFactory {

    public static Item mockItem(final int id, final int initialAmount) {
        final Item item = mock(AbstractItem.class);
        when(item.getId()).thenReturn(id);
        when(item.getAmount()).thenCallRealMethod();
        when(item.addAmount(anyInt())).thenCallRealMethod();
        when(item.clone()).thenAnswer((Answer<Item>) invocation -> mockItem(item.getId(), item.getAmount()));

        // initialize amount
        item.addAmount(initialAmount);

        return item;
    }

    /**
     * Creates a new Connection mock.
     *
     * @param user User object to be retrieved by getUser
     * @return the mock
     */
    public static Connection mockConnection(final User user) {
        final Connection conn = mock(Connection.class);
        when(conn.getUser()).thenReturn(user);
        return conn;
    }

    /**
     * Creates a new Connection mock.
     *
     * @return the mock
     */
    public static Connection mockConnection() {
        final ConnectedUser user = mock(ConnectedUser.class);
        final Connection connection = mockConnection(user);
        when(user.getConnection()).thenReturn(connection);
        return connection;
    }

    /**
     * Creates a new effect mock.
     *
     * @param appliesToChar        whether the effect to be mocked should apply to characters
     * @param appliesToWorldObject whether the effect to be mocked should apply to world objects
     * @return the created mock
     */
    public static Effect mockEffect(final boolean appliesToChar, final boolean appliesToWorldObject) {
        final Effect effect = mock(Effect.class);
        when(effect.appliesTo(any(Character.class), any(Character.class))).thenReturn(appliesToChar);
        when(effect.appliesTo(any(Character.class), any(WorldObject.class))).thenReturn(appliesToWorldObject);
        if (!appliesToChar) doThrow(InvalidTargetException.class).when(effect).apply(any(Character.class), any(Character.class));
        if (!appliesToWorldObject)
            doThrow(InvalidTargetException.class).when(effect).apply(any(Character.class), any(WorldObject.class));
        return effect;
    }

    /**
     * Creates a new character mock.
     *
     * @return the created mock
     */
    public static Character mockCharacter() {
        final Character character = mock(Character.class);
        // TODO Fill this in as needed
        return character;
    }

    /**
     * Creates a new world object mock.
     *
     * @return the created mock
     */
    public static WorldObject mockWorldObject() {
        final WorldObject worldObject = mock(WorldObject.class);
        // TODO Fill this in as needed
        return worldObject;
    }

    /**
     * Creates a new TimedEvent mock.
     *
     * @param chara event's Character
     * @return the created mock
     */
    public static TimedEvent mockTimedEvent(final Character chara) {
        final TimedEvent event = mock(TimedEvent.class);
        when(event.getCharacter()).thenReturn(chara);
        return event;
    }

    /**
     * Creates a new TimedEvent mock with a default mocked Character.
     * <p>
     * // @param executions The amount of the executions the event would have
     *
     * @return the created mock
     */
    public static TimedEvent mockTimedEvent() {
        return mockTimedEvent(mockCharacter());
    }

}
