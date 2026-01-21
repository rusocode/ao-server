package com.ao.network.packet.incoming;

import com.ao.model.user.LoggedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;

import java.io.UnsupportedEncodingException;

public class TalkPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws IndexOutOfBoundsException, UnsupportedEncodingException {
        // Verifica que haya al menos 2 bytes para la longitud del string
        if (buffer.getReadableBytes() < 2) return false; // Paquete incompleto

        // Lee el mensaje en formato ASCII con longitud en BIG_ENDIAN (network byte order)
        String message = buffer.getASCIIString();

        // Obtiene el usuario logueado
        LoggedUser user = (LoggedUser) connection.getUser();

        // TODO Implementar la logica para procesar el mensaje de chat
        System.out.println("TALK packet received from [" + user.getName() + "]: " + message);

        // TODO
        // 1. Validar que el usuario no este muerto, paralizado, silenciado, etc.
        // 2. Aplicar filtros anti-spam y validacion de contenido
        // 3. Enviar el mensaje a todos los usuarios cercanos usando ChatOverHeadHandler
        // 4. Guardar el mensaje en logs si es necesario

        return true; // Paquete procesado exitosamente
    }

}
