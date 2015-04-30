package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollideableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.damage.ReductionStrategy;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.players.ComponentPlayer;
import pl.edu.agh.game.logic.entities.players.Player;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-17
 */
public class EntityFactory {
    private static final Map<String, Texture> loadedTextures = new HashMap<>();

    private static final Map<String, String> rangerAttributes = new HashMap<>();
    private static final Map<String, String> thiefAttributes = new HashMap<>();
    private static final Map<String, String> arrowAttributes = new HashMap<>();

    private static final Map<String, Animation> rangerAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"), loadedTextures, rangerAttributes);
    private static final Map<String, Animation> thiefAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/thief_a.xml"), loadedTextures, thiefAttributes);
    private static final Map<String, Animation> arrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes);
    private static final Map<String, Animation> enemyArrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes); //tu trzeba zmienic grafike


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

    public static ComponentPlayer getNewPlayer(Player.Profession profession, Level level, InputState inputState) {
        switch (profession) {
            case ARCHER:
                return getNewArcher(level, inputState);
            case ROGUE:
            case BARBARIAN:
            case WARRIOR:
            default:
                throw new RuntimeException("Not implemented.");
        }
    }

    private static ComponentPlayer getNewArcher(Level level, InputState inputState) {
        StatsComponent statsComponent = new StatsComponent(500, 1.2f, 0.7f);
        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        CollideableComponent<Circle> collideableComponent = new CollideableComponent<>(new Circle(0, 0, collisionRange * 4), level.getMap());
        int velocity = 300;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collideableComponent);
        DamageComponent damageComponent = new DamageComponent(statsComponent);

        damageComponent.setReductionStrategy(new ReductionStrategy() {
            @Override
            public int reduce(Damage damage) {
                return damage.getValue();
            }
        });

        ComponentPlayer player = new ComponentPlayer(
                240, 7310,
                statsComponent,
                movementComponent,
                damageComponent,
                collideableComponent,
                new DrawableComponent(rangerAnimationMap),
                inputState,
                level
        );

        return player;
    }
}
