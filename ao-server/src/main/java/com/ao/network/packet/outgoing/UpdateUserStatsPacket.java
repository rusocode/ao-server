package com.ao.network.packet.outgoing;

import com.ao.model.character.UserCharacter;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class UpdateUserStatsPacket implements OutgoingPacket {

    private final UserCharacter user;

    public UpdateUserStatsPacket(final UserCharacter user) {
        this.user = user;
    }

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
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
