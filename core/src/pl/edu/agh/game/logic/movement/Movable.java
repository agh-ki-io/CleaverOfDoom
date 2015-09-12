package pl.edu.agh.game.logic.movement;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public interface Movable {
    void move(float dx, float dy, float deltaTime);
    void setPosition(float x, float y);
    float getX();
    float getY();
}
