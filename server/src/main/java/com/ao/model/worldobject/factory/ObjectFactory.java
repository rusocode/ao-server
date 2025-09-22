package com.ao.model.worldobject.factory;

import com.ao.model.worldobject.*;
import com.ao.model.worldobject.Object;
import com.ao.model.worldobject.properties.ObjectProperties;

import java.util.HashMap;

/**
 * Factory to create instances of items based on their properties.
 */

public class ObjectFactory {

    protected static final HashMap<ObjectType, Class<? extends Object>> objectMapper;

    static {
        objectMapper = new HashMap<>();
        objectMapper.put(ObjectType.ACCESSORY, Accessory.class);
        objectMapper.put(ObjectType.DEXTERITY_POTION, DexterityPotion.class);
        objectMapper.put(ObjectType.AMMUNITION, Ammunition.class);
//		worldObjectMapper.put(WorldObjectType.ANVIL, Anvil.class);
        objectMapper.put(ObjectType.ARMOR, Armor.class);
        objectMapper.put(ObjectType.BACKPACK, Backpack.class);
        objectMapper.put(ObjectType.BOAT, Boat.class);
        objectMapper.put(ObjectType.DEATH_POTION, DeathPotion.class);
        objectMapper.put(ObjectType.DOOR, Door.class);
        objectMapper.put(ObjectType.DRINK, Drink.class);
        objectMapper.put(ObjectType.EMPTY_BOTTLE, EmptyBottle.class);
        objectMapper.put(ObjectType.FILLED_BOTTLE, FilledBottle.class);
        objectMapper.put(ObjectType.FOOD, Food.class);
//		worldObjectMapper.put(WorldObjectType.FORGE, Forge.class);
        objectMapper.put(ObjectType.FORUM, Forum.class);
        objectMapper.put(ObjectType.GRABABLE_PROP, GrabableProp.class);
        objectMapper.put(ObjectType.HELMET, Helmet.class);
        objectMapper.put(ObjectType.HP_POTION, HPPotion.class);
        objectMapper.put(ObjectType.INGOT, Ingot.class);
        objectMapper.put(ObjectType.KEY, Key.class);
        objectMapper.put(ObjectType.MANA_POTION, ManaPotion.class);
        objectMapper.put(ObjectType.MINE, Mine.class);
        objectMapper.put(ObjectType.MINERAL, Mineral.class);
        objectMapper.put(ObjectType.MONEY, Gold.class);
        objectMapper.put(ObjectType.MUSICAL_INSTRUMENT, MusicalInstrument.class);
        objectMapper.put(ObjectType.PARCHMENT, Parchment.class);
        objectMapper.put(ObjectType.POISON_POTION, PoisonPotion.class);
        objectMapper.put(ObjectType.PROP, Prop.class);
        objectMapper.put(ObjectType.RANGED_WEAPON, RangedWeapon.class);
        objectMapper.put(ObjectType.SHIELD, Shield.class);
        objectMapper.put(ObjectType.SIGN, Sign.class);
        objectMapper.put(ObjectType.MAGICAL_WEAPON, Staff.class);
        objectMapper.put(ObjectType.STRENGTH_POTION, StrengthPotion.class);
        objectMapper.put(ObjectType.TELEPORT, Teleport.class);
        objectMapper.put(ObjectType.TREE, Tree.class);
        objectMapper.put(ObjectType.MELEE_WEAPON, Weapon.class);
        objectMapper.put(ObjectType.WOOD, Wood.class);
    }

    /**
     * Creates a new instance of the appropriate Object given its properties.
     *
     * @param objectProperties properties from which to create an object
     * @return the newly created object
     */
    public Object getObject(ObjectProperties objectProperties) throws ObjectFactoryException {
        Class<? extends Object> object = objectMapper.get(objectProperties.getType());
        try {
            return object.getConstructor(objectProperties.getClass()).newInstance(objectProperties);
        } catch (Exception e) {
            throw new ObjectFactoryException(e);
        }
    }

    /**
     * Creates a new instance of the appropriate AbstractItem given its properties.
     *
     * @param objectProperties object properties from which to create an object
     * @param amount           amount of the given object to create
     * @return the newly created object
     */
    public AbstractItem getObject(ObjectProperties objectProperties, int amount) throws ObjectFactoryException {
        @SuppressWarnings("unchecked")
        Class<? extends AbstractItem> object = (Class<? extends AbstractItem>) objectMapper.get(objectProperties.getType());
        try {
            return object.getConstructor(objectProperties.getClass(), int.class).newInstance(objectProperties, amount);
        } catch (Exception e) {
            throw new ObjectFactoryException(e);
        }
    }

}
