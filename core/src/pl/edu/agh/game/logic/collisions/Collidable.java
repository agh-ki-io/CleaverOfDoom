package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.math.Shape2D;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public interface Collidable {
    boolean overlaps(Collidable collidable);
    void collide(Collidable collidable);
    Shape2D getShape();
}
