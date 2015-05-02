package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.math.*;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-23
 */
public class CollisionUtil {

    public static boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        Vector2 center=new Vector2(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    public static boolean overlaps(Polygon p, Rectangle r) {
        Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
                r.height, 0, r.height });
        rPoly.setPosition(r.x, r.y);
        return Intersector.overlapConvexPolygons(rPoly, p);
    }

    public static boolean collisonGroupMatches(int onesCollisionGroup, int anothersCollisionGroup) {
        return (onesCollisionGroup & anothersCollisionGroup) == 0;
    }

    public static boolean overlaps(Shape2D thisShape, Shape2D otherShape) {
        if (thisShape instanceof Circle) {
            if (otherShape instanceof Circle) {
                return Intersector.overlaps((Circle) thisShape, (Circle) otherShape);
            } else if (otherShape instanceof Rectangle) {
                return Intersector.overlaps((Circle) thisShape, (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return CollisionUtil.overlaps((Polygon) otherShape, (Circle) thisShape);
            }
        } else if (thisShape instanceof Rectangle) {
            if (otherShape instanceof Circle) {
                return Intersector.overlaps((Circle) otherShape, (Rectangle) thisShape);
            } else if (otherShape instanceof Rectangle) {
                return Intersector.overlaps((Rectangle) thisShape, (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return CollisionUtil.overlaps((Polygon) otherShape, (Rectangle) thisShape);
            }
        } else if (thisShape instanceof Polygon) {
            if (otherShape instanceof Circle) {
                return CollisionUtil.overlaps((Polygon) thisShape, (Circle) otherShape);
            } else if (otherShape instanceof Rectangle) {
                return CollisionUtil.overlaps((Polygon) thisShape, (Rectangle) otherShape);
            } else if (otherShape instanceof Polygon) {
                return Intersector.overlapConvexPolygons((Polygon) thisShape, (Polygon) otherShape);
            }
        }
        return false;
    }

    public static boolean overlaps(Collidable one, Collidable another) {
        Shape2D thisShape = one.getShape();
        Shape2D otherShape = another.getShape();
        return overlaps(thisShape, otherShape);
    }
}
