package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.graphics.AnimationType;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Debug;
import pl.edu.agh.game.stolen_assets.EntityFactory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer extends pl.edu.agh.game.logic.entities.Character<Circle> {
    private final InputState inputState;
    private boolean destroyed = false;

    //Wszystkie poza 1
    private int collisionGroups = 1;

    Collection<OneWayProjectile> projectiles = new LinkedList<>();

    public ComponentPlayer(float x , float y, StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, InputState inputState, Level level) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, level);
        this.inputState = inputState;

        setPosition(x, y);
    }

    @Override
    public void collide(Collidable collidable) {

    }

    @Override
    public int getCollisionGroups() {
        return collisionGroups;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);

        for (OneWayProjectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        useSkills();
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);
        collidableComponent.getShape().setPosition(getX(), getY());

//        for (OneWayProjectile projectile : projectiles) {
//            projectile.update(deltaTime);
//        }
    }

    private void useSkills() {
        if (drawableComponent.isFree()) {
            if (inputState.isSkill1Used()) {
                Direction direction = drawableComponent.getLastUsableDirection();
                drawableComponent.setAnimation(AnimationType.ATTACK);
                OneWayProjectile projectile = EntityFactory.getNewArrow(getX(), getY(), 700, direction);
                level.addCharacter(projectile);
//     projectiles.add(projectile);
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
