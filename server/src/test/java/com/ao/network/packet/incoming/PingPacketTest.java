package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.outgoing.PongPacket;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PingPacketTest {

    @Test
    void handle_sendsPongPacket() {
        Connection connection = mock(Connection.class);
        DataBuffer buffer = mock(DataBuffer.class);

        boolean result = new PingPacket().handle(buffer, connection);

        verify(connection).send(any(PongPacket.class));
        assertThat(result).isTrue();
    }

}
