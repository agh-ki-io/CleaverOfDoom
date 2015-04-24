package pl.edu.agh.game.logic.movement;

import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.collisions.CollideableComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class MovementComponent {
    private final StatsComponent statsComponent;
    private final CollideableComponent collideableComponent;
    private float x;
    private float y;
    private float velocity;
    private float diagonalVelocity;
    private float waterVelocity;
    private float waterDiagonalVelocity;
    private float normalVelocity;
    private float normalDiagonalVelocity;
    private Direction direction;

    public MovementComponent(float velocity, float diagonalVelocity, StatsComponent statsComponent, CollideableComponent collideableComponent) {
        this.velocity = velocity;
        this.diagonalVelocity = diagonalVelocity;
        this.statsComponent = statsComponent;
        this.collideableComponent = collideableComponent;
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

        if(collideableComponent.collision(newX, newY, "blocked")) {
            if (collideableComponent.collision(oldX, newY, "blocked")) newY = oldY;
            if (collideableComponent.collision(newX, oldY, "blocked")) newX = oldX;
        }
        if(collideableComponent.collision(newX, newY, "water")) {
            if(!collideableComponent.collision(oldX, oldY, "stairs") && !collideableComponent.collision(oldX, oldY, "water")) {
                if (collideableComponent.collision(oldX, newY, "water")) newY = oldY;
                if (collideableComponent.collision(newX, oldY, "water")) newX = oldX;
            } else {
                velocity = waterVelocity;
                diagonalVelocity = waterDiagonalVelocity;
            }
        } else {
            if (collideableComponent.collision(oldX, oldY, "water") && !collideableComponent.collision(newX, newY, "stairs")) {
                if (!collideableComponent.collision(oldX, newY, "water")) newY = oldY;
                if (!collideableComponent.collision(newX, oldY, "water")) newX = oldX;
            } else {
                velocity = normalVelocity;
                diagonalVelocity = normalDiagonalVelocity;
            }
        }
        if(collideableComponent.collision(newX, newY, "pit")) {
            velocity = 0;
            diagonalVelocity = 0;
        }
        if(collideableComponent.collision(newX, newY, "slime")) {
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
}
