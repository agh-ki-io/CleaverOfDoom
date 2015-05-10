package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.settings.GameSettings;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public abstract class Effect {

//    protected Character character;
    protected Cooldown cooldown;

    public Effect(float ttl) {
//        this.character = character;
        GameSettings.getInstance().getEffectsToUpdate().add(this);
        cooldown = new Cooldown(ttl, this);
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public abstract void act(Character character);

    public abstract void dispose();

    }
