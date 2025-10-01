package com.ao.network;

import com.ao.network.packet.OutgoingPacket;
import com.ao.network.packet.outgoing.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager for server-side packets.
 */

public class ServerPacketsManager {

    /**
     * Maps packets classes to their ids.
     */
    protected static final Map<Class<? extends OutgoingPacket>, Integer> packets = new HashMap<>();

    static {
        for (ServerPackets packet : ServerPackets.values())
            packets.put(packet.packetClass, packet.ordinal());
    }

    /**
     * Writes the given packet in the given buffer.
     *
     * @param packet packet to write
     * @param buffer buffer where to write the packet
     */
    public static void write(OutgoingPacket packet, DataBuffer buffer) throws UnsupportedEncodingException {
        // Put the packet id before the packet itself
        buffer.put(packets.get(packet.getClass()).byteValue());
        packet.write(buffer);
    }

    /**
     * Enumerates server packets.
     */
    private enum ServerPackets {
        LOGGED(null),
        REMOVE_ALL_DIALOGS(null),
        REMOVE_CHR_DIALOG(null),
        TOGGLE_NAVIGATE(null),
        DISCONNECT(null),
        COMMERCE_END(null),
        BANKING_END(null),
        COMMERCE_INIT(null),
        BANK_INIT(null),
        USER_COMMERCE_INIT(null),
        USER_COMMERCE_END(null),
        USER_OFFER_CONFIRM(null),
        COMMERCE_CHAT(null),
        SHOW_BLACKSMITH_FORM(null),
        SHOW_CARPENTER_FORM(null),
        UPDATE_STAMINA(null),
        UPDATE_MANA(null),
        UPDATE_HP(null),
        UPDATE_GOLD(null),
        UPDATE_BANK_GOLD(null),
        UPDATE_EXP(null),
        CHANGE_MAP(ChangeMapPacket.class),
        POSITION_UPDATE(null),
        CHAT_OVER_HEAD(null),
        CONSOLE_MESSAGE(ConsoleMessagePacket.class),
        GUILD_CHAT(GuildChatPacket.class),
        SHOW_MESSAGE_BOX(null),
        USER_INDEX_IN_SERVER(UserIndexInServer.class),
        USER_CHARACTER_INDEX_IN_SERVER(UserCharacterIndexInServerPacket.class),
        CHARACTER_CREATE(CharacterCreatePacket.class),
        CHARACTER_REMOVE(null),
        CHARACTER_CHANGE_NICKNAME(null),
        CHARACTER_MOVE(null),
        CHARACTER_FORCE_MOVE(null),
        CHARACTER_CHANGE(null),
        OBJECT_CREATE(ObjectCreatePacket.class),
        OBJECT_DELETE(null),
        BLOCK_POSITION(BlockPositionPacket.class),
        PLAY_MIDI(PlayMidiPacket.class),
        PLAY_WAVE(PlayWavePacket.class),
        GUILD_LIST(null),
        AREA_CHANGED(AreaChangedPacket.class),
        TOGGLE_PAUSE(null),
        TOGGLE_RAIN(null),
        CREATE_FX(null),
        UPDATE_USER_STATS(UpdateUserStatsPacket.class),
        WORK_REQUEST_TARGET(null),
        CHANGE_INVENTORY_SLOT(null),
        CHANGE_BANK_SLOT(null),
        CHANGE_SPELL_SLOT(ChangeSpellSlotPacket.class),
        ATTRIBUTES(null),
        BLACKSMITH_WEAPONS(null),
        BLACKSMITH_ARMORS(null),
        CARPENTER_OBJECTS(null),
        REST_OK(null),
        ERROR_MESSAGE(ErrorMessagePacket.class),
        BLIND(null),
        DUMB(null),
        SHOW_SIGNAL(null),
        CHANGE_NPC_INVENTORY_SLOT(null),
        UPDATE_HUNGER_AND_THIRST(UpdateHungerAndThirstPacket.class),
        FAME(null),
        MINI_STATS(null),
        LEVEL_UP(null),
        ADD_FORUM_MESSAGE(null),
        SHOW_FORUM_MESSAGE(null),
        SET_INVISIBLE(SetInvisiblePacket.class),
        ROLL_DICE(DiceRollPacket.class),
        MEDITATE_TOGGLE(null),
        BLIND_NO_MORE(null),
        DUMB_NO_MORE(null),
        SEND_SKILLS(null),
        TRAINER_CREATURE_LIST(null),
        GUILD_NEWS(null),
        OFFER_DETAILS(null),
        ALLIANCE_PROPOSALS_LIST(null),
        PEACE_PROPOSALS_LIST(null),
        CHARACTER_INFO(null),
        GUILD_LEADER_INFO(null),
        GUILD_MEMBER_INFO(null),
        GUILD_DETAILS(null),
        SHOW_GUILD_FOUNDATION_FORM(null),
        PARALYZE_OK(ParalyzedPacket.class),
        SHOW_USER_REQUEST(null),
        TRADE_OK(null),
        BANK_OK(null),
        CHANGE_USER_TRADE_SLOT(null),
        SEND_NIGHT(null),
        PONG(null),
        UPDATE_TAG_AND_STATUS(null),
        SPAWN_LIST(null),
        SHOW_SOS_FORM(null),
        SHOW_MOTD_EDITION_FORM(null),
        SHOW_GM_PANEL_FORM(null),
        USER_NAME_LIST(null),
        SHOW_DENOUNCES(null),
        RECORD_LIST(null),
        RECORD_DETAILS(null),
        SHOW_GUILD_ALIGN(null),
        SHOW_PARTY_FORM(null),
        UPDATE_STRENGTH_AND_DEXTERITY(UpdateStrengthAndDexterityPacket.class),
        UPDATE_STRENGTH(UpdateStrengthPacket.class),
        UPDATE_DEXTERITY(UpdateDexterityPacket.class),
        ADD_SLOTS(null),
        MULTI_MESSAGE(null),
        STOP_WORKING(null),
        CANCEL_OFFER_ITEM(null);

        private final Class<? extends OutgoingPacket> packetClass;

        ServerPackets(Class<? extends OutgoingPacket> packet) {
            packetClass = packet;
        }

    }

}
