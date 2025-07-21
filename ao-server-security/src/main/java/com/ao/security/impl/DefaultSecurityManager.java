package com.ao.security.impl;

import com.ao.security.Hashing;
import com.ao.security.SecurityManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * Default SecurityManager implementation, not so secure.
 */

public class DefaultSecurityManager implements SecurityManager {

    @Override
    public int getClientHashLength() {
        // No client hash for default.
        return 0;
    }

    @Override
    public int getPasswordHashLength() {
        return Hashing.MD5_ASCII_LENGTH;
    }

    @Override
    public String[] getValidClientHashes() {
        return new String[]{};
    }

    @Override
    public void decrypt(ByteBuf buffer, Channel sc) {
        // Do nothing, no encryption for default.
    }

    @Override
    public void encrypt(ByteBuf buffer, Channel sc) {
        // Do nothing, no encryption for default.
    }

}
