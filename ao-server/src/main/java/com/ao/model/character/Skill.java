package com.ao.model.character;

/**
 * Defines available skills.
 */
public enum Skill {
	LUCK,
	MAGIC,
	STEAL,
	COMBAT_TACTICS,
	HAND_TO_HAND_COMBAT,
	MEDITATE,
	STAB,
	HIDING,
	SURVIVE,
	LUMBER,
	TRADE,
	DEFENSE,
	FISHING,
	MINING,
	WOODWORK,
	IRONWORK,
	LEADERSHIP,
	TAME,
	PROJECTILES,
	WRESTLING,
	SAILING;
	
	/**
	 * The amount of existing skills.
	 * 
	 * TODO: I don't really like this...any better alternative?
	 */
	public static final int AMOUNT = Skill.values().length;
	public static final Skill[] VALUES = Skill.values();
}
