package pl.edu.agh.game.logic.util;

import pl.edu.agh.game.logic.Updatable;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-14
 */
public class CoolDown implements Updatable {
    private float ttl;
    private float startingTTL;

    public CoolDown(float startingTTL) {
        this.startingTTL = startingTTL;
    }

    @Override
    public void update(float deltaTime) {
        if (ttl > 0) ttl -= deltaTime;
    }

    public boolean isOver() {
        return ttl <= 0;
    }

    public void reset() {
        ttl = startingTTL;
    }

    public float getPercentage() {
        if (ttl > 0) return (startingTTL - ttl) / startingTTL;
        else return 1.0f;
    }
}
