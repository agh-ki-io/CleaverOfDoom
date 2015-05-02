package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Level;
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
    private float ttl = 1.5f;

    private int collisionGroup;

    private final Damage damage = new Damage(DamageType.PHYSICAL, 100);
    private final CollidableComponent<Circle> collidableComponent;

    public OneWayProjectile(float x, float y, Animation animation, float velocity, Direction direction, Level level, int collisionGroup) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.velocity = velocity;
        this.collisionGroup = collisionGroup;
        this.dx = direction.getDx();
        this.dy = direction.getDy();
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);

        collidableComponent = new CollidableComponent<>(new Circle(x, y, 2.25f * scale), level.getMap());
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
        Debug.drawDot(x - originX, y - originY, scale, batch);
    }

    private void move(float dx, float dy, float deltaTime) {
        float newX;
        float newY;

        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            newX = this.x + diagonalVelocity * deltaTime * dx;
            newY = this.y + diagonalVelocity * deltaTime * dy;
        } else {
            newX = this.x + velocity * deltaTime * dx;
            newY = this.y + velocity * deltaTime * dy;
        }

        if (!collidableComponent.collision(newX, newY, "blocked")) {
            collidableComponent.getShape().setPosition(x, y);
            this.x = newX;
            this.y = newY;
        }
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
                this.destroyed = true;
            }
        }
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return collisionGroup;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
