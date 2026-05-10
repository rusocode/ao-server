package com.ao.network;

import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.incoming.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager for client-side packets.
 */

public class ClientPacketsManager {

    private static final Map<Byte, ClientPackets> PACKETS_BY_ID = new HashMap<>();

    static {
        for (ClientPackets packet : ClientPackets.values()) {
            PACKETS_BY_ID.put(packet.id, packet);
        }
    }

    /**
     * Handles new data in the connection's incoming buffer.
     *
     * @param connection connection's container
     * @param buffer     buffer from which to read data
     * @return true if a packet could be processed, false otherwise
     */
    public static boolean handle(DataBuffer buffer, Connection connection)
            throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException {
        // Leer el ID del paquete sin avanzar el índice del buffer
        // byte packetId = buffer.getBuffer().getByte(buffer.getBuffer().readerIndex());
        // System.out.println("Client packet: " + packetId);

        byte id = buffer.get();
        System.out.println("Client id: " + id);

        return PACKETS_BY_ID.get(id).handler.handle(buffer, connection);
    }

    /**
     * Enumerates client packets.
     */
    private enum ClientPackets {
        LOGIN_EXISTING_CHARACTER(0, LoginExistingCharacterPacket.class),
        THROW_DICE(1, ThrowDicesPacket.class),
        LOGIN_NEW_CHARACTER(2, LoginNewCharacterPacket.class),
        TALK(3, TalkPacket.class),
        YELL(4, YellPacket.class),
        WHISPER(5, WhisperPacket.class),
        WALK(6, WalkPacket.class);

        private final IncomingPacket handler;
        private final byte id;

        ClientPackets(int id, Class<? extends IncomingPacket> handler) {

            try {
                this.handler = handler.getConstructor().newInstance();
                this.id = (byte) id;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}