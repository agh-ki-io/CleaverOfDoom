package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.settings.GameSettings;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */

public class Cooldown implements Updatable {
    private float ttl;
    private Effect effect;
    private boolean ended;
    private float epsilon;

    public Cooldown(float ttl, Effect effect) {
        this.effect = effect;
        this.ended = false;
        this.epsilon = 0.01f;
        this.ttl = ttl;
    }

    public boolean isOver() {
        return ended;
    }

    @Override
    public void update(float deltaTime) {
        ttl -= deltaTime;
        if (ttl <= 0) {
            ended = true;
            effect.dispose();
            GameSettings.getInstance().getEffectsToUpdate().remove(effect);
        }
    }

    public boolean shouldAct() {
        float tmp = ttl - (float)(int)ttl;
        return (tmp < epsilon && tmp > -epsilon);
    }

}
