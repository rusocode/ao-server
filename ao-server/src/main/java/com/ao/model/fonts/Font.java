package com.ao.model.fonts;

/**
 * Defines available skills.
 */
public enum Font {
    TALK,
    FIGHT,
    WARNING,
    INFO,
    INFOBOLD,
    EXECUTION,
    PARTY,
    POISON,
    GUILD,
    SERVER,
    GUILDMSG,
    COUNCIL,
    CHAOSCOUNCIL,
    COUNCILSee,
    CHAOSCOUNCILSee,
    SENTINEL,
    GMMSG,
    GM,
    CITIZEN,
    CONSE,
    GOD;
	
	/**
	 * The amount of existing fonts.
	 */
	public static final int AMOUNT = Font.values().length;
	public static final Font[] VALUES = Font.values();
}
