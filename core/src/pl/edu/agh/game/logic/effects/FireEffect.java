package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class FireEffect extends Effect {

    public FireEffect(float ttl) {
        super(ttl);
    }

    public void act(Character character) {
        character.damage(new Damage(DamageType.FIRE,20));
    }

}
