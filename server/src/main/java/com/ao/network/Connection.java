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

    /**
     * Creates a new Connection.
     *
     * @param socket channel over which to communicate with the client
     */
    public Connection(final Channel socket) {
        this.socket = socket;
        user = new ConnectedUser(this);
    }

    /**
     * Retrieves the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Closes the connection.
     */
    public void disconnect() {
        socket.close();
    }

    /**
     * Sends the given packet to the client.
     *
     * @param packet packet being sent
     */
    public void send(final OutgoingPacket packet) {
        socket.writeAndFlush(packet);
    }

    /**
     * Changes the current connection's user model.
     *
     * @param user new user model to be used
     */
    public void changeUser(final User user) {
        this.user = user;
    }

}
