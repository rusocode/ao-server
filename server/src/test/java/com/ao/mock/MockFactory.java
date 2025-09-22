package com.ao.mock;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.model.object.AbstractItem;
import com.ao.model.object.Item;
import com.ao.model.object.Object;
import com.ao.network.Connection;
import com.ao.service.timedevents.TimedEvent;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

/**
 * Centralizes the common mocks creation.
 */

public class MockFactory {

    public static Item mockItem(int id, int initialAmount) {
        Item item = mock(AbstractItem.class);
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
    public static Connection mockConnection(User user) {
        Connection conn = mock(Connection.class);
        when(conn.getUser()).thenReturn(user);
        return conn;
    }

    /**
     * Creates a new Connection mock.
     *
     * @return the mock
     */
    public static Connection mockConnection() {
        ConnectedUser user = mock(ConnectedUser.class);
        Connection connection = mockConnection(user);
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
    public static Effect mockEffect(boolean appliesToChar, boolean appliesToWorldObject) {
        Effect effect = mock(Effect.class);
        when(effect.appliesTo(any(Character.class), any(Character.class))).thenReturn(appliesToChar);
        when(effect.appliesTo(any(Character.class), any(Object.class))).thenReturn(appliesToWorldObject);
        if (!appliesToChar) doThrow(InvalidTargetException.class).when(effect).apply(any(Character.class), any(Character.class));
        if (!appliesToWorldObject)
            doThrow(InvalidTargetException.class).when(effect).apply(any(Character.class), any(Object.class));
        return effect;
    }

    /**
     * Creates a new character mock.
     *
     * @return the created mock
     */
    public static Character mockCharacter() {
        // TODO Fill this in as needed
        return mock(Character.class);
    }

    /**
     * Creates a new world object mock.
     *
     * @return the created mock
     */
    public static Object mockWorldObject() {
        // TODO Fill this in as needed
        return mock(Object.class);
    }

    /**
     * Creates a new TimedEvent mock.
     *
     * @param chara event's Character
     * @return the created mock
     */
    public static TimedEvent mockTimedEvent(Character chara) {
        TimedEvent event = mock(TimedEvent.class);
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
