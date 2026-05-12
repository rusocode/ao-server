package com.ao.network.packet.incoming;

import com.ao.model.map.Heading;
import com.ao.model.user.LoggedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChangeHeadingPacketTest {

    private ChangeHeadingPacket packet;
    private DataBuffer buffer;
    private Connection connection;
    private LoggedUser user;

    @BeforeEach
    void setUp() {
        packet = new ChangeHeadingPacket();
        buffer = mock(DataBuffer.class);
        user = mock(LoggedUser.class);
        connection = mock(Connection.class);
        when(connection.getUser()).thenReturn(user);
    }

    @Test
    void handle_rawHeadingOne_setsNorth() {
        when(buffer.get()).thenReturn((byte) 1); // VB6 base-1: 1 = Norte → index 0 = NORTH
        boolean result = packet.handle(buffer, connection);
        verify(user).setHeading(Heading.NORTH);
        assertThat(result).isTrue();
    }

    @Test
    void handle_rawHeadingTwo_setsEast() {
        when(buffer.get()).thenReturn((byte) 2);
        packet.handle(buffer, connection);
        verify(user).setHeading(Heading.EAST);
    }

    @Test
    void handle_rawHeadingThree_setsSouth() {
        when(buffer.get()).thenReturn((byte) 3);
        packet.handle(buffer, connection);
        verify(user).setHeading(Heading.SOUTH);
    }

    @Test
    void handle_rawHeadingFour_setsWest() {
        when(buffer.get()).thenReturn((byte) 4);
        packet.handle(buffer, connection);
        verify(user).setHeading(Heading.WEST);
    }

    @Test
    void handle_rawHeadingZero_invalidDoesNotSetHeading() {
        // VB6 base-1: 0 → index -1 → null → invalid
        when(buffer.get()).thenReturn((byte) 0);
        boolean result = packet.handle(buffer, connection);
        verify(user, never()).setHeading(any());
        assertThat(result).isTrue();
    }

    @Test
    void handle_rawHeadingOutOfRange_doesNotSetHeading() {
        when(buffer.get()).thenReturn((byte) 5); // 5 - 1 = 4 → out of bounds
        boolean result = packet.handle(buffer, connection);
        verify(user, never()).setHeading(any());
        assertThat(result).isTrue();
    }

}
