package pl.edu.agh.game.graphics;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by Admin on 2015-04-21.
 */
public class AnimatedMap {

    private TiledMap map;

    public AnimatedTiledMapTile prepareTile(String name, float speed, String property, String value) {

        Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);

        Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("tileset").iterator();
        while(tiles.hasNext()) {
            TiledMapTile tile = tiles.next();
            if(tile.getProperties().containsKey("animation") && tile.getProperties().get("animation", String.class).equals(name)) {
                frameTiles.add((StaticTiledMapTile) tile);
            }
        }

        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(speed, frameTiles);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");

        for (int x = 0; x < layer.getWidth(); x++)
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if(cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals(name)) {
                    cell.setTile(animatedTile);
                }
            }
        animatedTile.getProperties().put(property, value);
        return animatedTile;
    }

    public AnimatedMap(TiledMap map) {

        this.map = map;
        float waterSpeed = 0.25f;
        float fireSpeed = 0.20f;
        float slimeSpeed = 0.15f;

        prepareTile("slime",slimeSpeed,"slime","");
        prepareTile("coveredslime",slimeSpeed,"","");
        prepareTile("fire1",fireSpeed,"blocked","");
        prepareTile("fire2",fireSpeed,"blocked","");
        prepareTile("smallfire1",fireSpeed,"blocked","");
        prepareTile("smallfire2",fireSpeed,"blocked","");
        prepareTile("water",waterSpeed,"water","");
        prepareTile("wave",waterSpeed,"water","");
        prepareTile("hand1",waterSpeed,"water","");
        prepareTile("hand2",waterSpeed,"water","");
        prepareTile("hand3",waterSpeed,"water","");
        prepareTile("hand4",waterSpeed,"water","");
        prepareTile("hand5",waterSpeed,"water","");
        prepareTile("hand6",waterSpeed,"water","");
        prepareTile("hand7",waterSpeed,"water","");
        prepareTile("hand8",waterSpeed,"water","");
        prepareTile("hand9",waterSpeed,"water","");

    }

}
