package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.collisions.CollisionUtil;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.stolen_assets.Debug;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class OneWayProjectile implements Updatable, Drawable, Collidable, GameEntity {
    private float x;
    private float y;
    private Animation animation;
    private float velocity;
    private float diagonalVelocity;
    private float dx;
    private float dy;
    private int scale = 4;
    private boolean destroyed = false;
    private float ttl = 1000;

    private final Damage damage = new Damage(DamageType.PHYSICAL, 100);

    private final CollidableComponent<Circle> collidableComponent;

    public OneWayProjectile(float x, float y, Animation animation, float velocity, Direction direction) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.velocity = velocity;
        this.dx = direction.getDx();
        this.dy = direction.getDy();
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);

        collidableComponent = new CollidableComponent<>(new Circle(x, y, 2.25f * scale));
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        move(dx, dy, deltaTime);
        ttl -= deltaTime;
        if (ttl < 0) destroyed = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        float originX = animation.getOriginX();
        float originY = animation.getOriginY();
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        Debug.drawCircle(collidableComponent.getShape().x - originX, collidableComponent.getShape().y - originY, collidableComponent.getShape().radius, batch);
        batch.draw(Debug.pixTexture, x - originX, y - originY, 0, 0, 1, 1, scale, scale, 0);
    }

    private void move(float dx, float dy, float deltaTime) {
        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            this.x += diagonalVelocity * deltaTime * dx;
            this.y += diagonalVelocity * deltaTime * dy;
        } else {
            this.x += velocity * deltaTime * dx;
            this.y += velocity * deltaTime * dy;
        }

        collidableComponent.getShape().setPosition(x, y);
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return collidableComponent.overlaps(collidable);
    }

    @Override
    public void collide(Collidable collidable) {
        if (CollisionUtil.collisonGroupMatches(getCollisionGroups(), collidable.getCollisionGroups())) {
            if (collidable instanceof Damagable) {
                ((Damagable) collidable).damage(damage);
            }
        }
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return Integer.MAX_VALUE ^ 1;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
