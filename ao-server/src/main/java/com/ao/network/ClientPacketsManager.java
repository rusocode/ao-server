package com.ao.network;

import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.incoming.LoginExistingCharacterPacket;
import com.ao.network.packet.incoming.LoginNewCharacterPacket;
import com.ao.network.packet.incoming.ThrowDicesPacket;
import com.ao.network.packet.incoming.WalkPacket;

import java.io.UnsupportedEncodingException;

/**
 * Manager for client-side packets.
 */

public class ClientPacketsManager {

    /**
     * Maps packet ids to their classes.
     */
    protected static final ClientPackets[] packets = ClientPackets.values();

    /**
     * Handles new data in the connection's incoming buffer.
     *
     * @param connection connection's container
     * @param buffer     buffer from which to read data
     * @return true if a packet could be processed, false otherwise
     */
    public static boolean handle(DataBuffer buffer, Connection connection) throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException {
        return packets[buffer.get()].handler.handle(buffer, connection);
    }

    /**
     * Enumerates client packets.
     */
    private enum ClientPackets {
        LOGIN_EXISTING_CHARACTER(LoginExistingCharacterPacket.class),
        THROW_DICE(ThrowDicesPacket.class),
        LOGIN_NEW_CHARACTER(LoginNewCharacterPacket.class),
        TALK(null),
        YELL(null),
        WHISPER(null),
        WALK(WalkPacket.class);

        private final IncomingPacket handler;

        ClientPackets(Class<? extends IncomingPacket> handler) {
            try {
                this.handler = handler.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}