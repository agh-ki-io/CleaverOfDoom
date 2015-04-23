package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.EntityFactory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer extends pl.edu.agh.game.logic.entities.Character {
    private final InputState inputState;

    Collection<OneWayProjectile> projectiles = new LinkedList<>();

    public ComponentPlayer(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent collidableComponent, DrawableComponent drawableComponent, InputState inputState) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent);
        this.inputState = inputState;

        setPosition(500, 500);
    }

    @Override
    public void collide(Collidable collidable) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        for (OneWayProjectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

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
                OneWayProjectile projectile = EntityFactory.getNewArrow(getX(), getY(), 200, direction);
                projectiles.add(projectile);
            }
        }
    }
}
