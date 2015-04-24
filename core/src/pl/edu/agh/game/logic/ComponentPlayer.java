package pl.edu.agh.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.Movable;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.EntityFactory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer implements Movable, Damagable, Collidable, Updatable, Drawable {
    private final StatsComponent statsComponent;
    private final MovementComponent movementComponent;
    private final DamageComponent damageComponent;
    private final CollidableComponent collidableComponent;
    private final DrawableComponent drawableComponent;
    private final InputState inputState;

    Collection<OneWayProjectile> projectiles = new LinkedList<>();

    public ComponentPlayer(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent collidableComponent, DrawableComponent drawableComponent, InputState inputState) {
        this.statsComponent = statsComponent;
        this.movementComponent = movementComponent;
        this.damageComponent = damageComponent;
        this.collidableComponent = collidableComponent;
        this.drawableComponent = drawableComponent;
        this.inputState = inputState;

        setPosition(188, 7313);
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return false;
    }

    @Override
    public void collide(Collidable collidable) {

    }

    @Override
    public Shape2D getShape() {
        return null;
    }

    @Override
    public void damage(Damage damage) {
        damageComponent.receiveDamage(damage);
        if (statsComponent.isDead()) destroy();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        drawableComponent.draw(batch);

        for (OneWayProjectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    @Override
    public void move(float dx, float dy, float deltaTime) {
        movementComponent.move(dx, dy, deltaTime);
    }

    @Override
    public void setPosition(float x, float y) {
        movementComponent.setPosition(x, y);
    }

    @Override
    public float getX() {
        return movementComponent.getX();
    }

    @Override
    public float getY() {
        return movementComponent.getY();
    }

    @Override
    public void update(float deltaTime) {
        drawableComponent.update(deltaTime);
        useSkills();
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);

        for (OneWayProjectile projectile : projectiles) {
            projectile.update(deltaTime);
        }
    }

    private void useSkills() {
        if (drawableComponent.isFree()) {
            if (inputState.isSkill1Used()) {
                Direction direction = drawableComponent.getLastUsableDirection();
                OneWayProjectile projectile = EntityFactory.getNewArrow(getX(), getY(), 1000, direction);
                projectiles.add(projectile);
            }
        }
    }
}
