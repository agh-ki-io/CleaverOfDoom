package pl.edu.agh.game.logic.movement;

import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class MovementComponent {
    private final StatsComponent statsComponent;
    private float x;
    private float y;
    private float velocity;
    private float diagonalVelocity;
    private Direction direction;

    public MovementComponent(float velocity, float diagonalVelocity, StatsComponent statsComponent) {
        this.velocity = velocity;
        this.diagonalVelocity = diagonalVelocity;
        this.statsComponent = statsComponent;
    }

    public void move(float dx, float dy, float deltaTime) {
        direction = Direction.fromVector(dx, dy);
        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            setPosition(
                    getX() + diagonalVelocity * deltaTime * dx * statsComponent.getMovementSpeedMultiplier(),
                    getY() + diagonalVelocity * deltaTime * dy * statsComponent.getMovementSpeedMultiplier());
        } else {
            setPosition(
                    getX() + velocity * deltaTime * dx * statsComponent.getMovementSpeedMultiplier(),
                    getY() + velocity * deltaTime * dy * statsComponent.getMovementSpeedMultiplier());
        }
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
