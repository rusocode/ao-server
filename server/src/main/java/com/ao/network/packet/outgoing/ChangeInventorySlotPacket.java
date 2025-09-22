package com.ao.network.packet.outgoing;

import com.ao.data.dao.ini.LegacyObjectType;
import com.ao.model.character.UserCharacter;
import com.ao.model.inventory.Inventory;
import com.ao.model.object.DefensiveItem;
import com.ao.model.object.Item;
import com.ao.model.object.Weapon;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class ChangeInventorySlotPacket implements OutgoingPacket {

    private final byte slot;
    private final UserCharacter userCharacter;
    private final Item item;

    public ChangeInventorySlotPacket(UserCharacter character, byte slot) {
        Inventory inventory = character.getInventory();
        userCharacter = character;
        this.slot = slot;
        item = inventory.getItem(slot);
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(slot);
        buffer.putShort((short) item.getId());
        buffer.putASCIIString(item.getName());
        buffer.putShort((short) item.getAmount());
        buffer.putBoolean(userCharacter.isEquipped(item));
        buffer.putShort((short) item.getGraphic());

        buffer.putShort((short) LegacyObjectType.findByType(item.getObjectType()).getId());

        if (item instanceof Weapon) {
            buffer.putShort((short) ((Weapon) item).getMaxHit());
            buffer.putShort((short) ((Weapon) item).getMinHit());
        } else {
            buffer.putShort((short) 0);
            buffer.putShort((short) 0);
        }

        if (item instanceof DefensiveItem) {
            buffer.putShort((short) ((DefensiveItem) item).getMaxDef());
            buffer.putShort((short) ((DefensiveItem) item).getMinDef());
        } else {
            buffer.putShort((short) 0);
            buffer.putShort((short) 0);
        }

        // TODO we want to send the sale price
        buffer.putFloat(item.getValue());
    }

}
