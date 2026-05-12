package com.ao.network;

import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.incoming.*;

import org.tinylog.Logger;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager para los paquetes entrantes del cliente.
 */
public class ClientPacketsManager {

    private static final Map<Byte, ClientPackets> PACKETS_BY_ID = new HashMap<>();

    static {
        for (ClientPackets packet : ClientPackets.values()) {
            PACKETS_BY_ID.put(packet.id, packet);
        }
    }

    /**
     * Indica si el ID de paquete es conocido por el servidor.
     *
     * @param id ID del paquete a verificar
     * @return true si el ID está registrado
     */
    public static boolean isKnownPacket(byte id) {
        return PACKETS_BY_ID.containsKey(id);
    }

    /**
     * Devuelve el tamaño del payload del paquete (sin contar el byte de ID).
     *
     * @param id ID del paquete
     * @return tamaño en bytes del payload, o -1 si es de longitud variable
     */
    public static int getPayloadSize(byte id) {
        ClientPackets packet = PACKETS_BY_ID.get(id);
        return packet != null ? packet.payloadSize : -1;
    }

    /**
     * Procesa un único paquete entrante. El decoder de Netty garantiza que
     * los bytes necesarios están disponibles antes de llamar a este método.
     *
     * @param id         ID del paquete a procesar (ya consumido del buffer)
     * @param buffer     buffer desde el que leer el payload del paquete
     * @param connection conexión del cliente
     */
    public static void handle(byte id, DataBuffer buffer, Connection connection)
            throws UnsupportedEncodingException {
        ClientPackets packet = PACKETS_BY_ID.get(id);
        if (packet != null) {
            Logger.debug("Procesando paquete entrante ID: {} ({})", id & 0xFF, packet.name());
            packet.handler.handle(buffer, connection);
        }
    }

    /**
     * Enumeración de los paquetes entrantes del cliente.
     * payloadSize indica los bytes del payload sin contar el ID (-1 = longitud variable).
     */
    private enum ClientPackets {
        LOGIN_EXISTING_CHARACTER(0,   LoginExistingCharacterPacket.class, -1),
        THROW_DICE(1,                 ThrowDicesPacket.class,             0),
        LOGIN_NEW_CHARACTER(2,        LoginNewCharacterPacket.class,      -1),
        TALK(3,                       TalkPacket.class,                   -1),
        YELL(4,                       YellPacket.class,                   -1),
        WHISPER(5,                    WhisperPacket.class,                -1),
        WALK(6,                       WalkPacket.class,                   1),
        LEFT_CLICK(26,                LeftClickPacket.class,              2),
        CHANGE_HEADING(37,            ChangeHeadingPacket.class,          1),
        PING(119,                     PingPacket.class,                   0);

        private final IncomingPacket handler;
        private final byte id;
        /** Bytes del payload sin contar el ID. -1 indica longitud variable. */
        private final int payloadSize;

        ClientPackets(int id, Class<? extends IncomingPacket> handler, int payloadSize) {
            try {
                this.handler = handler.getConstructor().newInstance();
                this.id = (byte) id;
                this.payloadSize = payloadSize;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}