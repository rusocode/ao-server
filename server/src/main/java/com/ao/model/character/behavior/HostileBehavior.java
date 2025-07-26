package com.ao.model.character.behavior;

import com.ao.model.character.Character;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.movement.MovementStrategy;

/**
 * Default behavior for hostile creatures.
 */

public class HostileBehavior implements Behavior {

    private MovementStrategy movement;
    private AttackStrategy attack;

    private Character character;
    private Character target;

    /**
     * Creates a new HostileBehavior instance.
     *
     * @param movement  movement strategy to be used
     * @param attack    attack strategy to be used
     * @param character character on which this behavior is applied
     */
    public HostileBehavior(MovementStrategy movement, AttackStrategy attack, Character character) {
        this.movement = movement;
        this.attack = attack;
        this.character = character;
    }

    @Override
    public void attackedBy(Character character) {
        // Don't care, anyway this behavior will attack everyone in his range
    }

    @Override
    public void takeAction() {
        // TODO This has to be rewritten as a WorldMapAction
//		for (final Character chara : character.getPosition().getCharactersNearby()) {
//			if (chara != character && (chara instanceof UserCharacter || ((NPCCharacter) chara).getMaster() != null)) {
//
//				// Is it the same as last target?
//				if (target != chara) {
//					movement.setTarget(chara);
//				}
//
//				attack.attack(chara);
//				target = chara;
//
//				break;
//			}
//		}
//
//		// Move, move!
//		character.moveTo(movement.move(character.getPosition()));
    }

}
