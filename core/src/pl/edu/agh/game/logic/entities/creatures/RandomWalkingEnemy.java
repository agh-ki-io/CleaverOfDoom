package pl.edu.agh.game.logic.entities.creatures;

import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.effects.Effect;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-11
 */
public class RandomWalkingEnemy extends OnePointEnemy {
    private final static float POINT_TTL_VALUE = 0.7f;
    private float point_ttl = POINT_TTL_VALUE;
    private final static float MOVE_DELTA = 200;

    public RandomWalkingEnemy(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, Level level, int collisionGroups) {
        super(statsComponent, movementComponent, damageComponent, collidableComponent, drawableComponent, level, collisionGroups);
    }

    @Override
    public void update(float deltaTime) {
//        List<Effect> toRemove = new Vector<>();
//        for (Effect effect : effects) {
//            if (effect.getCooldown().shouldAct())
//                effect.act(this);
//            if (effect.getCooldown().isOver()) toRemove.add(effect);
//        }
//        effects.removeAll(toRemove);
        super.update(deltaTime);

        point_ttl -= deltaTime;
        if (point_ttl <= 0) {
            point_ttl += POINT_TTL_VALUE;
            setNewDestination(getX() + getRandom(), getY() + getRandom());
//            System.out.println(newPosition);
        }
    }

    private float getRandom() {
        return (ThreadLocalRandom.current().nextFloat() - 0.5f) * MOVE_DELTA;
    }

//    public void addEffect(Effect effect) {
//        effects.add(effect);
//    }

}
