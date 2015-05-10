package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */

public class Cooldown implements Updatable {
    private float ttl;
    private float epsilon = 0.01f;

    public Cooldown(float ttl) {
        this.ttl = ttl;
    }

    public boolean isOver() {
        return ttl <= 0;
    }

    @Override
    public void update(float deltaTime) {
        ttl -= deltaTime;
    }

    public boolean shouldAct() {
        float tmp = ttl - (float)(int)ttl;
        return (tmp < epsilon && tmp > -epsilon);
    }

}
