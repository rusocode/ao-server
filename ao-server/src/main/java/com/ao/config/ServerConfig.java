package com.ao.config;

/**
 * General server configuration loader.
 */
public interface ServerConfig {

	public static final int DEFAULT_LISTENING_PORT = 7666;
	public static final int DEFAULT_BACKLOG = 5;
	
	/**
	 * Retrieves the port in which to listen for incoming connections.
	 * @return The port in which to listen for incoming connections.
	 */
	int getServerListeningPort();
	
	/**
	 * Retrieves the maximum amount of users expected to be logged in at the same time.
	 * @return The maximum amount of users expected to be logged in at the same time
	 */
	int getMaximumConcurrentUsers();
	
	/**
	 * The backlog for the listening socket.
	 * @return The backlog for the listening socket.
	 */
	int getListeningBacklog();
	
	/**
	 * Retrieves the valid client version.
	 * @return The version.
	 */
	String getVersion();
	
	/**
	 * Checks if the character creation is enabled.
	 * @return True if the character creation is enabled, false otherwise.
	 */
	boolean isCharacterCreationEnabled();
	
	/**
	 * Sets whether the character creation is enabled or not.
	 * @param enabled The character creation status.
	 */
	void setCharacterCreationEnabled(boolean enabled);
	
	/**
	 * Checks if the server is restricted only to administrators.
	 * @return True if the server is restricted, false otherwise.
	 */
	boolean isRestrictedToAdmins();
	
	/**
	 * Sets whether the server is restricted to administrators or not.
	 * @param restricted The restriction status.
	 */
	void setRestrictedToAdmins(boolean restricted);
		
}
