package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Shape2D;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class CollidableComponent<ShapeType extends Shape2D> {
    private final ShapeType collisionArea;
    private final TiledMapTileLayer collisionLayer;
    private final TiledMap map;

    private int collidableWidth;
    private int collidableHeight;
    private int tileWidth;
    private int tileHeight;

    public CollidableComponent(ShapeType collisionArea, TiledMap map) {
        this.collisionArea = collisionArea;
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("background");
        tileWidth = 75;//collisionLayer.getWidth()/2;
        tileHeight = 75;//collisionLayer.getHeight()/2;
        this.map = map;
    }

    public boolean overlaps(Collidable collidable) {
        return CollisionUtil.overlaps(getShape(), collidable.getShape());
    }

    public ShapeType getShape() {
        return collisionArea;
    }

    public void setSize(int[] size) {
        collidableWidth = size[0];
        collidableHeight = size[1];
    }

    public TiledMapTileLayer getLayer(){
        return collisionLayer;
    }

    public boolean collision(float x, float y, String collisionType) {
        return collisionLayer
                .getCell((int) ((x + collidableWidth / 2) / tileWidth), (int) ((y + collidableHeight / 2) / tileHeight))
                .getTile()
                .getProperties()
                .containsKey(collisionType);
    }

    public TiledMap getMap() {
        return map;
    }
}
