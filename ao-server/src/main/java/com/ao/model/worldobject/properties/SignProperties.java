package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */
public class SignProperties extends WorldObjectProperties {
	
	protected int bigGraphic;
	protected String text;
	
	/**
	 * Creates a new SignProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param bigGraphic The big graphic for the item.
	 * @param text The text for the item.
	 */
	public SignProperties(WorldObjectType type, int id, String name, int graphic, 
			int bigGraphic, String text) {
		super(type, id, name, graphic);
		
		this.bigGraphic = bigGraphic;
		this.text = text;
	}

	/**
	 * @return the big graphic.
	 */
	public int getBigGraphic() {
		return bigGraphic;
	}
	
	/**
	 * @return the text.
	 */
	public String getText() {
		return text;
	}
}
