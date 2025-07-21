package com.ao.network;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class DataBuffer {

    private static final String ASCII_FORMAT = "ASCII";
    private static final String UNICODE_FORMAT = "UTF8";
    protected ByteBuf buffer; // ChannelBuffer -> ByteBuf

    public DataBuffer(ByteBuf buffer) { // ChannelBuffer -> ByteBuf
        this.buffer = buffer;
    }

    /**
     * Retrieves a byte array from the buffer and removes it.
     *
     * @param length The array length.
     * @return The requested byte array.
     */
    public byte[] getBlock(int length) {
        byte[] ret = new byte[length];
        buffer.readBytes(ret);
        return ret;
    }

    /**
     * @see java.nio.ByteBuffer#get()
     */
    public byte get() {
        return buffer.readByte();
    }

    public boolean getBoolean() {
        byte b = buffer.readByte();
        return b == 1;
    }

    /**
     * @see java.nio.ByteBuffer#getChar()
     */
    public char getChar() {
        return buffer.readChar();
    }

    /**
     * @see java.nio.ByteBuffer#getDouble()
     */
    public double getDouble() {
        return buffer.readDouble();
    }

    /**
     * @see java.nio.ByteBuffer#getFloat()
     */
    public float getFloat() {
        return buffer.readFloat();
    }

    /**
     * @see java.nio.ByteBuffer#getInt()
     */
    public int getInt() {
        return buffer.readInt();
    }

    /**
     * @see java.nio.ByteBuffer#getLong()
     */
    public long getLong() {
        return buffer.readLong();
    }

    /**
     * @see java.nio.ByteBuffer#getShort()
     */
    public short getShort() {
        return buffer.readShort();
    }

    /**
     * @see java.nio.ByteBuffer#put(byte)
     */
    public DataBuffer put(byte b) {
        buffer.writeByte(b);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#put(byte[])
     */
    public final DataBuffer put(byte[] src) {
        buffer.writeBytes(src);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#put(byte)
     */
    public DataBuffer putBoolean(boolean value) {
        buffer.writeByte(value ? 1 : 0);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putChar(char)
     */
    public DataBuffer putChar(char value) {
        buffer.writeChar(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putDouble(double)
     */
    public DataBuffer putDouble(double value) {
        buffer.writeDouble(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putFloat(float)
     */
    public DataBuffer putFloat(float value) {
        buffer.writeFloat(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putInt(int)
     */
    public DataBuffer putInt(int value) {
        buffer.writeInt(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putLong(long)
     */
    public DataBuffer putLong(long value) {
        buffer.writeLong(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#putShort(short)
     */
    public DataBuffer putShort(short value) {
        buffer.writeShort(value);
        return this;
    }

    /**
     * @see java.nio.ByteBuffer#toString()
     */
    @Override
    public String toString() {
        return buffer.toString();
    }

    /**
     * Reads an ASCII string from the buffer.
     *
     * @return The ASCII String.
     */
    public String getASCIIString() {
        String str = null;
        short length = buffer.readShort();
        byte[] strBytes = new byte[length];

        buffer.readBytes(strBytes);

        try {
            str = new String(strBytes, ASCII_FORMAT);
        } catch (UnsupportedEncodingException e) {
            // ASCII should always be supported
            throw new RuntimeException("ASCII encoding not supported", e);
        }

        return str;
    }

    /**
     * Reads a unicode string from the buffer.
     *
     * @return The Unicode String.
     */
    public String getUnicodeString() {
        String str = null;
        short length = buffer.readShort();
        byte[] strBytes = new byte[length];

        buffer.readBytes(strBytes);

        try {
            str = new String(strBytes, UNICODE_FORMAT);
        } catch (UnsupportedEncodingException e) {
            // UTF-8 should always be supported
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }

        return str;
    }

    /**
     * Writes an ASCII string to the buffer.
     *
     * @param str The ASCII string to be written.
     * @return This DataBuffer instance for chaining.
     */
    public DataBuffer putASCIIString(String str) {
        try {
            byte[] strBytes = str.getBytes(ASCII_FORMAT);
            buffer.writeShort(strBytes.length);
            buffer.writeBytes(strBytes);
        } catch (UnsupportedEncodingException e) {
            // ASCII should always be supported
            throw new RuntimeException("ASCII encoding not supported", e);
        }

        return this;
    }

    /**
     * Writes a unicode string to the buffer.
     *
     * @param str The Unicode string to be written.
     * @return This DataBuffer instance for chaining.
     */
    public DataBuffer putUnicodeString(String str) {
        try {
            byte[] strBytes = str.getBytes(UNICODE_FORMAT);
            buffer.writeShort(strBytes.length);
            buffer.writeBytes(strBytes);
        } catch (UnsupportedEncodingException e) {
            // UTF-8 should always be supported
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }

        return this;
    }

    /**
     * Returns the underlying ByteBuf for direct access if needed.
     *
     * @return The underlying ByteBuf.
     */
    public ByteBuf getBuffer() {
        return buffer;
    }
}