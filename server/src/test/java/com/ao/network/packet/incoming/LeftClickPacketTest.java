package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LeftClickPacketTest {

    @Test
    void handle_sufficientBytes_readsTwoBytesAndReturnsTrue() {
        DataBuffer buffer = mock(DataBuffer.class);
        Connection connection = mock(Connection.class);
        when(buffer.getReadableBytes()).thenReturn(2);
        when(buffer.get()).thenReturn((byte) 5).thenReturn((byte) 10);

        boolean result = new LeftClickPacket().handle(buffer, connection);

        assertThat(result).isTrue();
        verify(buffer, times(2)).get();
    }

    @Test
    void handle_insufficientBytes_returnsFalseWithoutReading() {
        DataBuffer buffer = mock(DataBuffer.class);
        Connection connection = mock(Connection.class);
        when(buffer.getReadableBytes()).thenReturn(1);

        boolean result = new LeftClickPacket().handle(buffer, connection);

        assertThat(result).isFalse();
        verify(buffer, never()).get();
    }

}
