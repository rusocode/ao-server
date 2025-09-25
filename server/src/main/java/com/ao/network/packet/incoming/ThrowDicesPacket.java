package com.ao.network.packet.incoming;

import com.ao.model.character.Attribute;
import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.DiceRollPacket;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ThrowDicesPacket implements IncomingPacket {

    private final Random random = new Random();

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        // Minimum attributes values
        final int MIN_STRENGTH = 15;
        final int MIN_DEXTERITY = 15;
        final int MIN_INGELLIGENCE = 16;
        final int MIN_CHARISMA = 15;
        final int MIN_CONSTITUTION = 16;

        byte strength = (byte) Math.max(MIN_STRENGTH, 13 + random.nextInt(4) + random.nextInt(3));
        byte dexterity = (byte) Math.max(MIN_DEXTERITY, 12 + random.nextInt(4) + random.nextInt(4));
        byte intelligence = (byte) Math.max(MIN_INGELLIGENCE, 13 + random.nextInt(4) + random.nextInt(3));
        byte charisma = (byte) Math.max(MIN_CHARISMA, 12 + random.nextInt(4) + random.nextInt(4));
        byte constitution = (byte) Math.max(MIN_CONSTITUTION, 16 + random.nextInt(2) + random.nextInt(2));

        ConnectedUser user = (ConnectedUser) connection.getUser();

        user.setAttribute(Attribute.STRENGTH, strength);
        user.setAttribute(Attribute.DEXTERITY, dexterity);
        user.setAttribute(Attribute.INTELLIGENCE, intelligence);
        user.setAttribute(Attribute.CHARISMA, charisma);
        user.setAttribute(Attribute.CONSTITUTION, constitution);

        connection.send(new DiceRollPacket(strength, dexterity, intelligence, charisma, constitution));

        return true;

    }

}
