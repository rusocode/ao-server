package com.ao.model.map;

/**
 * A city in the game.
 * @author zaxtor
 */
public class City{
	
	private int map;
	private byte x;
	private byte y;
	
	/**
	 * Creates a new city instance.
	 * @param map The city map.
	 * @param x   The city x co-ordinate for users spawn.
	 * @param y   The city y co-ordinate for users spawn.
	 */
	public City(int map, byte x, byte y){
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieves the city map.
	 * @return The city map.
	 */
	public int getMap() {
		return map;
	}
	
	/**
	 * Retrieves the city x co-ordinate for users spawn.
	 * @return The city x co-ordinate for users spawn.
	 */
	public byte getX() {
		return x;
	}
	
	/**
	 * Retrieves the city y co-ordinate for users spawn.
	 * @return The city y co-ordinate for users spawn.
	 */
	public byte getY() {
		return y;
	}
	
}
