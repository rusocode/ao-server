package com.ao.network.packet.outgoing;

import com.ao.model.character.UserCharacter;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record UpdateUserStatsPacket(UserCharacter user) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort((short) user.getMaxHitPoints());
        buffer.putShort((short) user.getHitPoints());
        buffer.putShort((short) user.getMaxMana());
        buffer.putShort((short) user.getMana());
        buffer.putShort((short) user.getMaxStamina());
        buffer.putShort((short) user.getStamina());
        buffer.putInt(user.getMoney());
        buffer.put(user.getLevel());
        buffer.putInt(user.getExperienceForLeveUp());
        buffer.putInt(user.getExperience());
    }

}
