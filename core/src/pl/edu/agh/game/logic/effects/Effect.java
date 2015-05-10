package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public abstract class Effect {

//    protected Character character;
    protected Cooldown cooldown;

    public Effect(float ttl) {
//        this.character = character;
        cooldown = new Cooldown(ttl);
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public abstract void act(Character character);

}
