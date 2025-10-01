package com.ao.network;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class DataBuffer {

    protected ByteBuf buffer;

    public DataBuffer(ByteBuf buffer) { // ChannelBuffer -> ByteBuf
        this.buffer = buffer;
    }

    public byte get() {
        return buffer.readByte();
    }

    public ByteBuf getBuffer() {
        return buffer;
    }

    /**
     * Retrieves a byte array from the buffer and removes it.
     *
     * @param length length
     * @return the requested byte array
     */
    public byte[] getBlock(int length) {
        return buffer.readBytes(new byte[length]).array();
    }

    public boolean getBoolean() {
        return buffer.readByte() == 1;
    }

    public char getChar() {
        return buffer.readChar();
    }

    public double getDouble() {
        return buffer.readDouble();
    }

    public float getFloat() {
        return buffer.readFloat();
    }

    public int getInt() {
        return buffer.readInt();
    }

    public long getLong() {
        return buffer.readLong();
    }

    public short getShort() {
        return buffer.readShort();
    }

    /**
     * Reads an ASCII string from the buffer.
     *
     * @return the ASCII String
     */
    public String getASCIIString() {
        byte[] bytes = new byte[buffer.readShort()];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * Reads an ASCII string with a fixed length from the buffer.
     *
     * @param length length of the string to be read
     * @return String at the buffer's current position
     */
    public String getASCIIStringFixed(int length) {
        byte[] str = new byte[length];
        buffer.readBytes(str);
        return new String(str, StandardCharsets.US_ASCII);
    }

    /**
     * Reads a UTF8 string from the buffer.
     *
     * @return the UTF8 String
     */
    public String getUTF8String() {
        short length = buffer.readShort();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Lee una cadena UTF-8 de longitud fija del buffer.
     *
     * @param fixedLength longitud fija a leer en bytes
     * @return cadena UTF-8 sin padding de bytes nulos
     */
    public String getUTF8StringFixed(int fixedLength) {
        byte[] stringBytes = new byte[fixedLength];
        buffer.readBytes(stringBytes);

        // Encontrar el final real de la cadena (quitar padding de zeros)
        int actualLength = fixedLength;
        for (int i = fixedLength - 1; i >= 0; i--) {
            if (stringBytes[i] != 0) {
                actualLength = i + 1;
                break;
            }
        }

        if (actualLength == 0) return "";

        return new String(stringBytes, 0, actualLength, StandardCharsets.UTF_8);
    }

    /**
     * Gets the amount readable bytes in the buffer.
     *
     * @return the amount readable bytes in the buffer
     */
    public int getReadableBytes() {
        return buffer.readableBytes();
    }

    public DataBuffer put(byte b) {
        buffer.writeByte(b);
        return this;
    }

    public final DataBuffer put(byte[] src) {
        buffer.writeBytes(src);
        return this;
    }

    public DataBuffer putBoolean(boolean value) {
        buffer.writeByte(value ? 1 : 0);
        return this;
    }

    public DataBuffer putChar(char value) {
        buffer.writeChar(value);
        return this;
    }

    public DataBuffer putDouble(double value) {
        buffer.writeDouble(value);
        return this;
    }

    public DataBuffer putFloat(float value) {
        buffer.writeFloat(value);
        return this;
    }

    public DataBuffer putInt(int value) {
        buffer.writeInt(value);
        return this;
    }

    public DataBuffer putLong(long value) {
        buffer.writeLong(value);
        return this;
    }

    public DataBuffer putShort(short value) {
        buffer.writeShort(value);
        return this;
    }

    /**
     * Writes an ASCII string to the buffer.
     *
     * @param str ASCII string to be written
     * @return this DataBuffer instance for chaining
     */
    public DataBuffer putASCIIString(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
        buffer.writeShort(bytes.length);
        buffer.writeBytes(bytes);
        return this;
    }

    /**
     * Writes a Unicode string to the buffer.
     *
     * @param str The Unicode string to be written.
     * @return this DataBuffer instance for chaining
     */
    public DataBuffer putUnicodeString(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        buffer.writeShort(bytes.length);
        buffer.writeBytes(bytes);
        return this;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

}