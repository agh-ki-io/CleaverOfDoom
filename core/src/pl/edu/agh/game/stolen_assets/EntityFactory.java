package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.damage.ReductionStrategy;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.creatures.OnePointEnemy;
import pl.edu.agh.game.logic.entities.creatures.RandomWalkingEnemy;
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
    public static InputState player1InputState;

    private static final Map<String, Texture> loadedTextures = new HashMap<>();

    private static final Map<String, CharacterProperties> loadedProperties = new HashMap<>();

    private static final Map<String, String> rangerAttributes = new HashMap<>();
    private static final Map<String, String> thiefAttributes = new HashMap<>();
    private static final Map<String, String> arrowAttributes = new HashMap<>();

    private static final Map<String, Animation> rangerAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"), loadedTextures, rangerAttributes);
    private static final Map<String, Animation> thiefAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/thief_a.xml"), loadedTextures, thiefAttributes);
    private static final Map<String, Animation> arrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes);
    private static final Map<String, Animation> enemyArrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes); //tu trzeba zmienic grafike

//    private static final Map<String, >

    public static OneWayProjectile getNewArrow(float x, float y, float velocity, Direction direction, Level level) {
        velocity = 1000;
        return new OneWayProjectile(x, y, arrowAnimationMap.get(direction.toString()), velocity, direction, level, 1);
    }

    public static OneWayProjectile getNewEnemyArrow(float x, float y, float velocity, Direction direction, Level level) {
        velocity = 1000;
        return new OneWayProjectile(x, y, enemyArrowAnimationMap.get(direction.toString()), velocity, direction, level, 2);
    }

    public static Map<String, Animation> getRangerAnimationMap() {
        return rangerAnimationMap;
    }

    public static Map<String, Animation> getThiefAnimationMap() {
        return thiefAnimationMap;
    }

    public static ComponentPlayer getNewPlayer(Player.Profession profession, Level level) {
        switch (profession) {
            case ARCHER:
                return getNewArcher(level, player1InputState);
            case ROGUE:
            case BARBARIAN:
            case WARRIOR:
            default:
                throw new RuntimeException("Not implemented.");
        }
    }

    private static ComponentPlayer getNewArcher(Level level, InputState inputState) {
        StatsComponent statsComponent = new StatsComponent(500, 1.2f, 2.7f);
        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, collisionRange * 4), level.getMap());
        int velocity = 300;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collidableComponent);
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
                collidableComponent,
                new DrawableComponent(rangerAnimationMap),
                inputState,
                level
        );

        return player;
    }

    public static <T extends Updatable & Drawable & Collidable & GameEntity> Character getNewEnemy(String name, int collisionGroups, Level<T> level) {
        return getOnePointEnemy(name, collisionGroups,  level);
//        throw new RuntimeException(name + " not found or failed to initialize.");
    }

    public static OnePointEnemy getOnePointEnemy(String name, int collisionGroups, Level level) {
        Util.loadEnemy(name, Gdx.files.internal("stolen_assets/actors/" + name + ".xml"), loadedTextures, loadedProperties);
        CharacterProperties props = loadedProperties.get(name);

        StatsComponent statsComponent = new StatsComponent(
                Integer.parseInt(props.properties.get("hp")),
                Float.parseFloat(props.properties.get("speed")),
                1f);

        float collisionRange = Float.valueOf(props.properties.get("collision"));

        CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, collisionRange * 4), level.getMap());
        int velocity = 300;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collidableComponent);
        DamageComponent damageComponent = new DamageComponent(statsComponent);

        damageComponent.setReductionStrategy(new ReductionStrategy() {
            @Override
            public int reduce(Damage damage) {
                return damage.getValue();
            }
        });

        OnePointEnemy enemy = new RandomWalkingEnemy(
                statsComponent,
                movementComponent,
                damageComponent,
                collidableComponent,
                new DrawableComponent(props.animations),
                level,
                collisionGroups);

        return enemy;
    }
}
