package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.settings.GameSettings;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public abstract class Effect {

//    protected Character character;
    public Set<Character> characters;
    protected Cooldown cooldown;

    public Effect(float ttl) {
//        this.character = character;
        cooldown = new Cooldown(ttl, this);
        characters = new HashSet<>();
        GameSettings.getInstance().getEffectsToUpdate().add(this);
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public abstract void act(Character character);
    public abstract void dispose();
//    public abstract Effect copy();
    public abstract void addToSet(Character character);

    public Character getFirst(){
        Iterator<Character> iterator = characters.iterator();
        return iterator.next();
    }

}
