package pl.edu.agh.game.logic.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.Movable;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.skills.SkillUser;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-23
 */
public abstract class Character<CollidableShapeType extends Shape2D> implements SkillUser, Movable, Damagable, Collidable, Updatable, Drawable, GameEntity {
    public final StatsComponent statsComponent;
    public final MovementComponent movementComponent;
    public final DamageComponent damageComponent;
    public final CollidableComponent<CollidableShapeType> collidableComponent;
    public final DrawableComponent drawableComponent;
    public final SkillComponent skillComponent;
    protected Level level;

    public Character(StatsComponent statsComponent, DamageComponent damageComponent, CollidableComponent<CollidableShapeType> collidableComponent, DrawableComponent drawableComponent, MovementComponent movementComponent, SkillComponent skillComponent, Level level) {
        this.statsComponent = statsComponent;
        this.damageComponent = damageComponent;
        this.collidableComponent = collidableComponent;
        this.drawableComponent = drawableComponent;
        this.movementComponent = movementComponent;
        this.skillComponent = skillComponent;
        this.drawableComponent.setCharacter(this);
        this.level = level;
    }



    @Override
    public boolean overlaps(Collidable collidable) {
        return collidableComponent.overlaps(collidable);
    }

    @Override
    public abstract void collide(Collidable collidable);

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public void damage(Damage damage) {
        damageComponent.receiveDamage(damage);
        if (statsComponent.isDead()) destroy();
    }

    @Override
    public abstract void destroy();

    @Override
    public void draw(SpriteBatch batch) {
        drawableComponent.draw(batch);
    }

    @Override
    public void move(float dx, float dy, float deltaTime) {
        movementComponent.move(dx, dy, deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        skillComponent.update(deltaTime);
        drawableComponent.update(deltaTime);
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
}
