package com.ao.network.packet.outgoing;

import com.ao.model.message.Message;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet used to tell the client to display one of several pre-formatted messages.
 */

public class MultiMessagePacket implements OutgoingPacket {

    private final Message message;
    private byte byte1;
    private short short1, short2;
    private int int1;
    private String string1;

    /**
     * Creates a console message.
     */
    public MultiMessagePacket(Message message) {
        if (message == Message.NPC_HIT_USER || message == Message.USER_HIT_NPC
                || message == Message.USER_HITTED_BY_USER || message == Message.USER_HITTED_USER
                || message == Message.WORK_REQUEST_TARGET || message == Message.USER_ATTACKED_SWING
                || message == Message.USER_KILL || message == Message.HAVE_KILLED_USER
                || message == Message.HOME) {
            throw new IllegalArgumentException("This constructor is invalid for the given type: " + message);
        }
        this.message = message;
    }

    public MultiMessagePacket(Message message, byte arg1, short arg2) {
        if (message != Message.NPC_HIT_USER)
            throw new IllegalArgumentException("This constructor is only valid for NPC_HIT_USER message type");
        this.message = message;
        byte1 = arg1;
        short1 = arg2;
    }

    public MultiMessagePacket(Message message, int arg1) {
        if (message != Message.USER_HIT_NPC)
            throw new IllegalArgumentException("This constructor is only valid for USER_HIT_NPC message type");
        this.message = message;
        int1 = arg1;
    }

    public MultiMessagePacket(Message message, short arg1, byte arg2, short arg3) {
        if (message != Message.USER_HITTED_BY_USER && message != Message.USER_HITTED_USER)
            throw new IllegalArgumentException("This constructor is only valid for USER_HITTED_BY_USER and USER_HITTED_USER message types");
        this.message = message;
        short1 = arg1;
        byte1 = arg2;
        short2 = arg3;
    }

    public MultiMessagePacket(Message message, byte arg1) {
        if (message != Message.WORK_REQUEST_TARGET)
            throw new IllegalArgumentException("This constructor is only valid for WORK_REQUEST_TARGET message type");
        this.message = message;
        byte1 = arg1;
    }

    public MultiMessagePacket(Message message, short arg1) {
        if (message != Message.USER_ATTACKED_SWING && message != Message.USER_KILL)
            throw new IllegalArgumentException("This constructor is only valid for USER_ATTACKED_SWING and USER_KILL message types");
        this.message = message;
        short1 = arg1;
    }

    public MultiMessagePacket(Message message, short arg1, int arg2) {
        if (message != Message.HAVE_KILLED_USER)
            throw new IllegalArgumentException("This constructor is only valid for HAVE_KILLED_USER message type");
        this.message = message;
        short1 = arg1;
        int1 = arg2;
    }

    public MultiMessagePacket(Message message, byte arg1, short arg2, String arg3) {
        if (message != Message.HOME)
            throw new IllegalArgumentException("This constructor is only valid for HOME message type");
        this.message = message;
        byte1 = arg1;
        short1 = arg2;
        string1 = arg3;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put((byte) message.ordinal());
        switch (message) {
            case NPC_HIT_USER:
                buffer.put(byte1);
                buffer.putShort(short1);
                break;
            case USER_HIT_NPC:
                buffer.putInt(int1);
                break;
            case USER_ATTACKED_SWING:
            case USER_KILL:
                buffer.putShort(short1);
                break;
            case USER_HITTED_BY_USER:
            case USER_HITTED_USER:
                buffer.putShort(short1);
                buffer.put(byte1);
                buffer.putShort(short2);
                break;
            case WORK_REQUEST_TARGET:
                buffer.put(byte1);
                break;
            case HAVE_KILLED_USER:
                buffer.putShort(short1);
                buffer.putInt(int1);
                break;
            case HOME:
                buffer.put(byte1);
                buffer.putShort(short1);
                buffer.putASCIIString(string1);
                break;
            default:
                break;
        }
    }

}
