package com.ao.network.packet.outgoing;

import com.ao.model.character.Character;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class CharacterCreatePacket implements OutgoingPacket {

    private final Character character;

    /**
     * Create character packages.
     */
    public CharacterCreatePacket(Character character) {
        this.character = character;
    }

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(character.getCharIndex());
        buffer.putShort((short) character.getBody());
        buffer.putShort((short) character.getHead());
        buffer.put((byte) character.getHeading().ordinal());
        buffer.put(character.getPosition().getX());
        buffer.put(character.getPosition().getY());
        // Verifica null antes de acceder a getId()
        buffer.putShort(character.getWeapon() != null ? (short) character.getWeapon().getId() : (short) 0);
        buffer.putShort(character.getShield() != null ? (short) character.getShield().getId() : (short) 0);
        buffer.putShort(character.getHelmet() != null ? (short) character.getHelmet().getId() : (short) 0);

        // Para Fx también verifica null
        if (character.getFx() != null) {
            buffer.putShort((short) character.getFx().getId());
            buffer.putShort((short) character.getFx().getLoops());
        } else {
            buffer.putShort((short) 0); // Sin efecto
            buffer.putShort((short) 0); // 0 loops
        }

        buffer.putUnicodeString(character.getName());
        buffer.put((byte) character.getNickColor());
        buffer.put((byte) character.getPrivileges().getPrivilegesFlags());
    }

}
