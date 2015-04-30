package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import pl.edu.agh.game.graphics.AnimatedMap;
import pl.edu.agh.game.logic.Level;


/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public class LevelFactory {
    public static Level initializeLevel(String mapID) {
        switch (mapID) {
            case "testMap":
                return initializeTestMap();
            default:
                throw new RuntimeException("Not implemented.");
        }
    }

    private static Level initializeTestMap() {
        TiledMap map = new TmxMapLoader().load("stolen_assets/maps/untitled.tmx");
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, 1.5f);
        new AnimatedMap(map);
        return new Level(map, renderer);
    }
}
