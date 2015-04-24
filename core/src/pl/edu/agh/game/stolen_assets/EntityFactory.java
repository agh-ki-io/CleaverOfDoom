package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;

import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-17
 */
public class EntityFactory {
    static final Map<String, Animation> rangerAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"));
    static final Map<String, Animation> thiefAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/thief_a.xml"));
    static final Map<String, Animation> arrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"));
    static final Map<String, Animation> enemyArrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml")); //tu trzeba zmienic grafike


    public static OneWayProjectile getNewArrow(float x, float y, float velocity, Direction direction, TiledMap map) {
        velocity = 1000;
        return new OneWayProjectile(x, y, arrowAnimationMap.get(direction.toString()), velocity, direction, map, 1);
    }

    public static OneWayProjectile getNewEnemyArrow(float x, float y, float velocity, Direction direction, TiledMap map) {
        velocity = 1000;
        return new OneWayProjectile(x, y, enemyArrowAnimationMap.get(direction.toString()), velocity, direction, map, 2);
    }

    public static Map<String, Animation> getRangerAnimationMap() {
        return rangerAnimationMap;
    }

    public static Map<String, Animation> getThiefAnimationMap() {
        return thiefAnimationMap;
    }
}
