package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.entities.*;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */

public class Cooldown implements Updatable {
    private float ttl;
    private float epsilon = 0.1f;
    private Effect effect;
    private int lastActed;

    public Cooldown(float ttl, Effect effect) {
        this.effect = effect;
        this.ttl = ttl;
        this.lastActed = (int)ttl+1;
    }

    public boolean isOver() {
        return ttl <= 0;
    }

    @Override
    public void update(float deltaTime) {
        ttl -= deltaTime;
        if (shouldAct()) {
            for (pl.edu.agh.game.logic.entities.Character character : effect.characters) {
                effect.act(character);
            }
        }
//        System.out.println("updaed: "+ttl);
    }

    public boolean shouldAct() {
        float tmp = ttl - (float)(int)ttl;
//        System.out.println(isOver()+" "+ttl+" "+(tmp < epsilon && tmp > -epsilon)+" "+((int)ttl != lastActed));
        if (isOver()) {
            effect.dispose();
        }
        if ((tmp < epsilon && tmp > -epsilon && (int)ttl != lastActed)) {
            lastActed = (int)ttl;
            return true;
        } else return false;
    }

}
