package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.math.*;


/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class CollidableComponent<ShapeType extends Shape2D> {
    private final ShapeType collisionArea;

    public CollidableComponent(ShapeType collisionArea) {
        this.collisionArea = collisionArea;
    }

    public boolean overlaps(Collidable collidable) {
        Shape2D otherShape = collidable.getShape();
        if (getShape() instanceof Circle) {
            if (otherShape instanceof Circle) {
                return Intersector.overlaps((Circle) getShape(), (Circle) otherShape);
            } else if (otherShape instanceof Rectangle) {
                return Intersector.overlaps((Circle) getShape(), (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return CollisionUtil.overlaps((Polygon) otherShape, (Circle) getShape());
            }
        } else if (getShape() instanceof Rectangle) {
            if (otherShape instanceof Circle) {
                return Intersector.overlaps((Circle) otherShape, (Rectangle) getShape());
            } else if (otherShape instanceof Rectangle) {
                return Intersector.overlaps((Rectangle) getShape(), (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return CollisionUtil.overlaps((Polygon) otherShape, (Rectangle) getShape());
            }
        } else if (getShape() instanceof Polygon) {
            if (otherShape instanceof Circle) {
                return CollisionUtil.overlaps((Polygon) getShape(), (Circle) otherShape);
            } else if (otherShape instanceof Rectangle) {
                return CollisionUtil.overlaps((Polygon) getShape(), (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return Intersector.overlapConvexPolygons((Polygon) getShape(), (Polygon) otherShape);
            }
        }
        return false;
    }

    public ShapeType getShape() {
        return collisionArea;
    }
}
