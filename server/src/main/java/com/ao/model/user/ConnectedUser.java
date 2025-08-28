package com.ao.model.user;

import com.ao.model.character.Attribute;
import com.ao.network.Connection;

import java.util.HashMap;
import java.util.Map;

public class ConnectedUser implements User {

    private final Connection connection;
    private final Map<Attribute, Byte> attributes = new HashMap<>();
    private Account account;

    /**
     * Creates a new ConnectedUser.
     *
     * @param connection channel over which to communicate with the client
     */
    public ConnectedUser(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Retrieves the asked attribute.
     *
     * @param dice attribute
     * @return the attribute value
     */
    public Byte getAttribute(Attribute dice) {
        return attributes.get(dice);
    }

    /**
     * Sets the given attribute with the given value.
     *
     * @param dice   attribute to set
     * @param points attribute's points
     */
    public void setAttribute(Attribute dice, byte points) {
        attributes.put(dice, points);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ConnectedUser other = (ConnectedUser) obj;
        if (account == null) {
            if (other.account != null) return false;
        } else if (!account.equals(other.account)) return false;
        return true;
    }

}
