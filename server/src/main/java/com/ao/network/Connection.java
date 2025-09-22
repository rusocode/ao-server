package com.ao.network;

import com.ao.model.user.ConnectedUser;
import com.ao.model.user.User;
import com.ao.network.packet.OutgoingPacket;
import io.netty.channel.Channel;

/**
 * Connection class. A simple DTO to wrap the user and connection together.
 */

public class Connection {

    private final Channel socket;
    private User user;

    public Connection(Channel socket) {
        this.socket = socket;
        user = new ConnectedUser(this);
    }

    public User getUser() {
        return user;
    }

    public void disconnect() {
        socket.close();
    }

    public void send(OutgoingPacket packet) {
        socket.writeAndFlush(packet);
    }

    /**
     * Changes the current connection's user model.
     *
     * @param user new user model to be used
     */
    public void changeUser(User user) {
        this.user = user;
    }

}
