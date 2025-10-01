package com.ao.network.packet.outgoing;

import com.ao.model.character.Character;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record CharacterCreatePacket(Character character) implements OutgoingPacket {

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(character.getCharIndex());
        buffer.putShort((short) character.getBody());
        buffer.putShort((short) character.getHead());
        buffer.put((byte) character.getHeading().ordinal());
        buffer.put(character.getPosition().getX());
        buffer.put(character.getPosition().getY());
        buffer.putShort(character.getWeapon() != null ? (short) character.getWeapon().getId() : (short) 0);
        buffer.putShort(character.getShield() != null ? (short) character.getShield().getId() : (short) 0);
        buffer.putShort(character.getHelmet() != null ? (short) character.getHelmet().getId() : (short) 0);
        buffer.putShort(character.getFx() != null ? (short) character.getFx().getId() : (short) 0);
        buffer.putShort(character.getFx() != null ? (short) character.getFx().getLoops() : (short) 0);
        buffer.putUnicodeString(character.getName());
        buffer.put((byte) character.getNickColor());
        buffer.put((byte) character.getPrivileges().getPrivilegesFlags());
    }

}
