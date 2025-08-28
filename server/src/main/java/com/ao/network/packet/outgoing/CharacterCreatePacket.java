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
        buffer.putShort((short) character.getWeapon().getId());
        buffer.putShort((short) character.getShield().getId());
        buffer.putShort((short) character.getHelmet().getId());
        buffer.putShort((short) character.getFx().getId());
        buffer.putShort((short) character.getFx().getLoops());
        buffer.putASCIIString(character.getName());
        buffer.put((byte) character.getNickColor());
        buffer.put((byte) character.getPrivileges().getPrivilegesFlags());
    }

}
