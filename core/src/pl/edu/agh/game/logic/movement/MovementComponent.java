package pl.edu.agh.game.logic.movement;

import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.entities.players.Spearman;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class MovementComponent {
    private final StatsComponent statsComponent;
    private final CollidableComponent collidableComponent;
    private float x;
    private float y;

    private float velocity;

    private float waterVelocity;

    private float waterDiagonalVelocity;
    private float normalVelocity;
    private float normalDiagonalVelocity;
    private Direction direction;
    private float diagonalVelocity;

    public float getVelocity() {
        return velocity;
    }

    public float getDiagonalVelocity() {
        return diagonalVelocity;
    }

    public void setVelocity(float velocity, float diagonalVelocity) {
        this.velocity = velocity;
        this.diagonalVelocity = diagonalVelocity;
    }

    public MovementComponent(float velocity, float diagonalVelocity, StatsComponent statsComponent, CollidableComponent collidableComponent) {
        this.velocity = velocity;
        this.diagonalVelocity = diagonalVelocity;
        this.statsComponent = statsComponent;
        this.collidableComponent = collidableComponent;
        normalVelocity = velocity;
        normalDiagonalVelocity = diagonalVelocity;
        this.waterVelocity = velocity / 2;
        this.waterDiagonalVelocity = diagonalVelocity / 2;
    }

    public void move(float dx, float dy, float deltaTime) {
        direction = Direction.fromVector(dx, dy);
        float oldX = getX();
        float oldY = getY();
        float newX = getX() + velocity * deltaTime * dx * statsComponent.getMovementSpeedMultiplier();
        float newY = getY() + velocity * deltaTime * dy * statsComponent.getMovementSpeedMultiplier();
        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            newX = getX() + diagonalVelocity * deltaTime * dx * statsComponent.getMovementSpeedMultiplier();
            newY = getY() + diagonalVelocity * deltaTime * dy * statsComponent.getMovementSpeedMultiplier();
        }

        if(collidableComponent.collision(newX, newY, "blocked")) {
            if (collidableComponent.collision(oldX, newY, "blocked")) newY = oldY;
            if (collidableComponent.collision(newX, oldY, "blocked")) newX = oldX;
        }
        if(collidableComponent.collision(newX, newY, "water")) {
            if(!collidableComponent.collision(oldX, oldY, "stairs") && !collidableComponent.collision(oldX, oldY, "water")) {
                if (collidableComponent.collision(oldX, newY, "water")) newY = oldY;
                if (collidableComponent.collision(newX, oldY, "water")) newX = oldX;
            } else {
                velocity = waterVelocity;
                diagonalVelocity = waterDiagonalVelocity;
            }
        } else {
            if (collidableComponent.collision(oldX, oldY, "water") && !collidableComponent.collision(newX, newY, "stairs")) {
                if (!collidableComponent.collision(oldX, newY, "water")) newY = oldY;
                if (!collidableComponent.collision(newX, oldY, "water")) newX = oldX;
            } else {
                velocity = normalVelocity;
                diagonalVelocity = normalDiagonalVelocity;
            }
        }
        if(collidableComponent.collision(newX, newY, "pit")) {
            velocity = 0;
            diagonalVelocity = 0;
        }
        if(collidableComponent.collision(newX, newY, "slime")) {
            velocity = 0;
            diagonalVelocity = 0;
        }
        setPosition(newX,newY);

    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Direction getDirection() {
        if (direction != null) {
            return direction;
        } else return Direction.LAST;
    }

    public void moveWeapon(Spearman spearman) {
        this.setPosition(spearman.getX(),spearman.getY());
    }
}
