package pl.edu.agh.game.logic.collisions;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class CollidableComponent {

    private final TiledMapTileLayer collisionLayer;
    private int collidableWidth;
    private int collidableHeight;
    private int tileWidth;
    private int tileHeight;

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
