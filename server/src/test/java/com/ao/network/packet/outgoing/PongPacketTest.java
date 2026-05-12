package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class PongPacketTest {

    @Test
    void write_writesNothingToBuffer() {
        DataBuffer buffer = mock(DataBuffer.class);
        new PongPacket().write(buffer);
        verifyNoInteractions(buffer);
    }

}