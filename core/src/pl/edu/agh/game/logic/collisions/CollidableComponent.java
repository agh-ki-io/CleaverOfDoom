package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.math.*;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class CollidableComponent<ShapeType extends Shape2D> {
    private final ShapeType collisionArea;

    private final TiledMapTileLayer collisionLayer;
    private int collidableWidth;
    private int collidableHeight;
    private int tileWidth;
    private int tileHeight;


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
    
    public CollidableComponent(TiledMap map) {
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("background");
        tileWidth = 75;//collisionLayer.getWidth()/2;
        System.out.println(collisionLayer.getWidth()/2);
        System.out.println(collisionLayer.getHeight()/2);
        tileHeight = 75;//collisionLayer.getHeight()/2;
    }

    public void setSize(int[] size) {
        collidableWidth = size[0];
        collidableHeight = size[1];
    }

    public TiledMapTileLayer getLayer(){
        return collisionLayer;
    }

    public boolean collision(float x, float y, String collisionType) {
        return collisionLayer.getCell((int) ((x + collidableWidth/2) / tileWidth),(int) ((y + collidableHeight/2) / tileHeight)).getTile().getProperties().containsKey(collisionType);
    }
}
