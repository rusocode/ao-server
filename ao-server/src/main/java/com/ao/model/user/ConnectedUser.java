package com.ao.model.user;

import java.util.HashMap;
import java.util.Map;

import com.ao.model.character.Attribute;
import com.ao.network.Connection;

public class ConnectedUser implements User {
	private final Connection conn;

	private Account account;
	private Map<Attribute, Byte> attributes = new HashMap<Attribute, Byte>();

	/**
	 * Creates a new ConnectedUser.
	 * @param socket The channel over which to communicate with the client.
	 */
	public ConnectedUser(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Retrieves the user's account.
	 * @return The account.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the user's account.
	 * @param account The account.
	 */
	public void setAccount(final Account account) {
		this.account = account;
	}

	/**
	 * Retrieves the asked attribute.
	 * @param dice The attribute.
	 * @return The attribute value.
	 */
	public Byte getAttribute(final Attribute dice) {
		return attributes.get(dice);
	}

	/**
	 * Sets the given attribute with the given value.
	 * @param dice The attribute to set.
	 * @param points The attribute's points.
	 */
	public void setAttribute(final Attribute dice, final byte points) {
		attributes.put(dice, points);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConnectedUser other = (ConnectedUser) obj;
		if (account == null) {
			if (other.account != null) {
				return false;
			}
		} else if (!account.equals(other.account)) {
			return false;
		}
		return true;
	}

}
