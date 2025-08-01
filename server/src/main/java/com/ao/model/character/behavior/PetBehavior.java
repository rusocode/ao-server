package com.ao.model.character.behavior;

import com.ao.model.character.Character;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.movement.MovementStrategy;

public class PetBehavior implements Behavior {

    private final MovementStrategy movement;
    private final Character character;
    private final AttackStrategy attack;
    private Character target;

    public PetBehavior(MovementStrategy movement, AttackStrategy attack, Character character) {
        this.movement = movement;
        this.character = character;
        this.attack = attack;
    }

    @Override
    public void attackedBy(Character character) {
        // TODO Don't attack npcs if the attack is magic
        if (target != character) {
            movement.setTarget(character);
            target = character;
        }
    }

    @Override
    public void takeAction() {
        if (target != null && character.getPosition().inVisionRange(target.getPosition())) attack.attack(target);
        else movement.setTarget(character); // Follow the pet master
        // Move, move!
        character.moveTo(movement.move(character.getPosition()));
    }

}
