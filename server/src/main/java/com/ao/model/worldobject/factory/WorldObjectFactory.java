package com.ao.model.worldobject.factory;

import com.ao.model.worldobject.*;
import com.ao.model.worldobject.properties.WorldObjectProperties;

import java.util.HashMap;

/**
 * Factory to create instances of items based on their properties.
 */

public class WorldObjectFactory {

    protected static final HashMap<WorldObjectType, Class<? extends WorldObject>> worldObjectMapper;

    static {
        worldObjectMapper = new HashMap<>();
        worldObjectMapper.put(WorldObjectType.ACCESSORY, Accessory.class);
        worldObjectMapper.put(WorldObjectType.DEXTERITY_POTION, DexterityPotion.class);
        worldObjectMapper.put(WorldObjectType.AMMUNITION, Ammunition.class);
//		worldObjectMapper.put(WorldObjectType.ANVIL, Anvil.class);
        worldObjectMapper.put(WorldObjectType.ARMOR, Armor.class);
        worldObjectMapper.put(WorldObjectType.BACKPACK, Backpack.class);
        worldObjectMapper.put(WorldObjectType.BOAT, Boat.class);
        worldObjectMapper.put(WorldObjectType.DEATH_POTION, DeathPotion.class);
        worldObjectMapper.put(WorldObjectType.DOOR, Door.class);
        worldObjectMapper.put(WorldObjectType.DRINK, Drink.class);
        worldObjectMapper.put(WorldObjectType.EMPTY_BOTTLE, EmptyBottle.class);
        worldObjectMapper.put(WorldObjectType.FILLED_BOTTLE, FilledBottle.class);
        worldObjectMapper.put(WorldObjectType.FOOD, Food.class);
//		worldObjectMapper.put(WorldObjectType.FORGE, Forge.class);
        worldObjectMapper.put(WorldObjectType.FORUM, Forum.class);
        worldObjectMapper.put(WorldObjectType.GRABABLE_PROP, GrabableProp.class);
        worldObjectMapper.put(WorldObjectType.HELMET, Helmet.class);
        worldObjectMapper.put(WorldObjectType.HP_POTION, HPPotion.class);
        worldObjectMapper.put(WorldObjectType.INGOT, Ingot.class);
        worldObjectMapper.put(WorldObjectType.KEY, Key.class);
        worldObjectMapper.put(WorldObjectType.MANA_POTION, ManaPotion.class);
        worldObjectMapper.put(WorldObjectType.MINE, Mine.class);
        worldObjectMapper.put(WorldObjectType.MINERAL, Mineral.class);
        worldObjectMapper.put(WorldObjectType.MONEY, Gold.class);
        worldObjectMapper.put(WorldObjectType.MUSICAL_INSTRUMENT, MusicalInstrument.class);
        worldObjectMapper.put(WorldObjectType.PARCHMENT, Parchment.class);
        worldObjectMapper.put(WorldObjectType.POISON_POTION, PoisonPotion.class);
        worldObjectMapper.put(WorldObjectType.PROP, Prop.class);
        worldObjectMapper.put(WorldObjectType.RANGED_WEAPON, RangedWeapon.class);
        worldObjectMapper.put(WorldObjectType.SHIELD, Shield.class);
        worldObjectMapper.put(WorldObjectType.SIGN, Sign.class);
        worldObjectMapper.put(WorldObjectType.MAGICAL_WEAPON, Staff.class);
        worldObjectMapper.put(WorldObjectType.STRENGTH_POTION, StrengthPotion.class);
        worldObjectMapper.put(WorldObjectType.TELEPORT, Teleport.class);
        worldObjectMapper.put(WorldObjectType.TREE, Tree.class);
        worldObjectMapper.put(WorldObjectType.MELEE_WEAPON, Weapon.class);
        worldObjectMapper.put(WorldObjectType.WOOD, Wood.class);
    }

    /**
     * Creates a new instance of the appropriate AbstractItem given its properties.
     *
     * @param woProperties properties from which to create an object
     * @param amount       amount of the given object to create
     * @return the newly created object
     */
    public AbstractItem getWorldObject(WorldObjectProperties woProperties, int amount) throws WorldObjectFactoryException {
        @SuppressWarnings("unchecked")
        Class<? extends AbstractItem> woClass = (Class<? extends AbstractItem>) worldObjectMapper.get(woProperties.getType());
        try {
            return woClass.getConstructor(woProperties.getClass(), int.class).newInstance(woProperties, amount);
        } catch (Exception e) {
            throw new WorldObjectFactoryException(e);
        }
    }

    /**
     * Creates a new instance of the appropriate WorldObject given its properties.
     *
     * @param woProperties properties from which to create an object
     * @return the newly created object
     */
    public WorldObject getWorldObject(WorldObjectProperties woProperties) throws WorldObjectFactoryException {
        Class<? extends WorldObject> woClass = worldObjectMapper.get(woProperties.getType());
        try {
            return woClass.getConstructor(woProperties.getClass()).newInstance(woProperties);
        } catch (Exception e) {
            throw new WorldObjectFactoryException(e);
        }
    }

}
