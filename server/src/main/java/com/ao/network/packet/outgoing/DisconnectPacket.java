package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Paquete enviado al cliente para indicarle que debe desconectarse
 * (ID 4 - FINOK en Protocol.bas).
 */
public class DisconnectPacket implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) {
        // Este paquete no tiene payload.
        // El ID del paquete (4) es escrito automáticamente por el ServerPacketsManager.
    }

}
