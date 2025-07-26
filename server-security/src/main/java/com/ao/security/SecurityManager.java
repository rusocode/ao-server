package com.ao.security;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * Server security manager.
 */

public interface SecurityManager {

    /**
     * Retrieves the passwords' hash's length.
     *
     * @return the hash's length
     */
    int getPasswordHashLength();

    /**
     * Retrieves the client's hash's length.
     *
     * @return the hash's length
     */
    int getClientHashLength();

    /**
     * Retrieves all the valid client's hashes.
     *
     * @return the hashes
     */
    String[] getValidClientHashes();

    /**
     * Encrypts the given buffer.
     *
     * @param buffer buffer to by encrypted
     * @param c      Channel where the data will be sent
     */
    void encrypt(ByteBuf buffer, Channel c);

    /**
     * Decrypts the given buffer starting from the last mark.
     *
     * @param buffer buffer to be decrypted
     * @param c      Channel where the data came from
     */
    void decrypt(ByteBuf buffer, Channel c);

}
