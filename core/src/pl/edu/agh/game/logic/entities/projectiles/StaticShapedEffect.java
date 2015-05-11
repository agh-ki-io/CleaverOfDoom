package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollisionUtil;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.effects.Effect;
import pl.edu.agh.game.logic.effects.FireEffect;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.stolen_assets.Debug;

import java.util.Set;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-01
 */
public class StaticShapedEffect implements Updatable, Drawable, Collidable, GameEntity {
    private final Effect effect;
    private final Circle circle;
    private int collisionGroups;
    private boolean destroyed;
    private float ttl;

    public StaticShapedEffect(float x, float y, float r, int collisionGroups, float ttl, Effect effect) {
//        this.damage = damage;
        this.effect = effect;
        this.circle = new Circle(x, y, r);
        this.collisionGroups = collisionGroups;
        this.ttl = ttl;
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return CollisionUtil.overlaps(this, collidable);
    }

    @Override
    public void collide(Collidable collidable) {
//        System.out.println(CollisionUtil.collisonGroupMatches(getCollisionGroups(), collidable.getCollisionGroups())+" "+getCollisionGroups()+" "+collidable.getCollisionGroups());
        if (CollisionUtil.collisonGroupMatches(getCollisionGroups(), collidable.getCollisionGroups())) {
            Set<Effect> effects;
            if (collidable instanceof pl.edu.agh.game.logic.entities.Character) {
                effect.addToSet((Character) collidable);
//                effects = ((Character) collidable).effects;
//                if (effects.contains())
//                effects.add(effect.copy());
            }
        }
    }

    @Override
    public Shape2D getShape() {
        return circle;
    }

    @Override
    public int getCollisionGroups() {
        return collisionGroups;
    }

    @Override
    public void draw(SpriteBatch batch) {
        Debug.drawCircle(circle.x, circle.y, circle.radius, batch);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void update(float deltaTime) {
        ttl -= deltaTime;
        if (ttl <= 0) destroyed = true;
    }

    public void setPosition(float x, float y) {
        circle.setPosition(x, y);
    }
}
