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
import pl.edu.agh.game.logic.damage.PercentageReductionStrategy;
import pl.edu.agh.game.logic.damage.ReductionStrategy;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.drawable.WeaponType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.creatures.OnePointEnemy;
import pl.edu.agh.game.logic.entities.creatures.FollowingPlayerEnemy;
import pl.edu.agh.game.logic.entities.players.ComponentPlayer;
import pl.edu.agh.game.logic.entities.players.Player;
import pl.edu.agh.game.logic.entities.players.Warrior;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.entities.projectiles.SpearPoint;
import pl.edu.agh.game.logic.entities.projectiles.Weapon;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.*;
import pl.edu.agh.game.logic.skills.implementations.*;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.logic.util.Cooldown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-17
 */
public class EntityFactory {
    public static InputState player1InputState;

    private static final float archerRegenRatio = 25;
    private static final float archerStartEnergy = 500;
    private static final int[] archerSkillsUsageToLvl={7,5,5,5};
    private static final int[] wariorSkillsUsageToLvl={7,5,5,5};
    private static final ArrayList<Integer> archerSkillCosts=new ArrayList<>(Arrays.asList(new Integer[]{25, 200, 30, 40}));
    private static final ArrayList<Integer> warriorSkillCosts =new ArrayList<>(Arrays.asList(new Integer[]{20, 20, 200, 300}));

    private static final int[] enemySkillsUsageToLvl={100,100,100,100};
    private static final ArrayList<Integer> enemySkillCosts=new ArrayList<>(Arrays.asList(new Integer[]{30, 0, 0, 0}));


    private static final Map<String, Texture> loadedTextures = new HashMap<>();

    private static final Map<String, CharacterProperties> loadedProperties = new HashMap<>();

    private static final Map<String, String> rangerAttributes = new HashMap<>();
    private static final Map<String, String> thiefAttributes = new HashMap<>();
    private static final Map<String, String> arrowAttributes = new HashMap<>();
    private static final Map<String, String> fistAttributes = new HashMap<>();

    private static final Map<String, Animation> rangerAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"), loadedTextures, rangerAttributes);
    private static final Map<String, Animation> thiefAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/thief_a.xml"), loadedTextures, thiefAttributes);
    private static final Map<String, Animation> arrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes);
    private static final Map<String, Animation> fistAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/fist.xml"), loadedTextures, fistAttributes);
    private static final Map<String, Animation> spearpointAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/maps/spearpoint.xml"), loadedTextures, fistAttributes);
    private static final Map<String, Animation> enemyArrowAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"), loadedTextures, arrowAttributes); //tu trzeba zmienic grafike
    private static Map<WeaponType,Map<String, Animation>> weaponTypes = new HashMap<>();
//    private static final Map<String, >

    private static void fillWeapons() {
        weaponTypes.put(WeaponType.FIST,fistAnimationMap);
        weaponTypes.put(WeaponType.SPEAR,arrowAnimationMap);
    }

    public static OneWayProjectile getNewArrow(float x, float y, float velocity, Direction direction, Level level, int collisionGroups) {
        velocity = 1000;
        return new OneWayProjectile(x, y, arrowAnimationMap.get(direction.toString()), velocity, direction, level, collisionGroups);
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
                return getNewWarrior(level, player1InputState);
            default:
                throw new RuntimeException("Not implemented.");
        }
    }

    private static ComponentPlayer getNewArcher(Level level, InputState inputState) {
        StatsComponent statsComponent = new StatsComponent(archerStartEnergy, 1.2f, 0.7f, archerRegenRatio);
        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        final CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, collisionRange * 4), level.getMap());
        int velocity = 300;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collidableComponent);
        DamageComponent damageComponent = new DamageComponent(statsComponent);

        damageComponent.setReductionStrategy(new ReductionStrategy() {
            @Override
            public int reduce(Damage damage) {
                return damage.getValue();
            }
        });

        ArrayList<SkillBuilder> builders = new ArrayList<>();
        ArrayList<Cooldown> cooldowns = new ArrayList<>();
        SkillComponent skillComponent = new SkillComponent(
                builders,
                cooldowns,
                level,
                null,
                archerSkillCosts,
                archerSkillsUsageToLvl
        );
        final ComponentPlayer player = new ComponentPlayer(
                statsComponent,
                movementComponent,
                damageComponent,
                collidableComponent,
                new DrawableComponent(rangerAnimationMap),
                skillComponent,
                inputState,
                level
        );

        skillComponent.setSkillUser(player);

        player.setPosition(240, 7300);

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new ShootArrowSkill(700, level, skillUser);
            }
        });

        cooldowns.add(new Cooldown(0));

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new ArrowCircleSkill(level, skillUser, 0.031f, 700);
            }
        });

        cooldowns.add(new Cooldown(0));

        return player;
    }

    public static <T extends Updatable & Drawable & Collidable & GameEntity> Character getNewEnemy(String name, int collisionGroups, Level<T> level) {
        return getOnePointEnemy(name, collisionGroups,  level);
//        throw new RuntimeException(name + " not found or failed to initialize.");
    }

    private static ComponentPlayer getNewWarrior(Level level, InputState inputState) {
        StatsComponent statsComponent = new StatsComponent(500, 1.2f, 2.7f, archerRegenRatio);
        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, collisionRange * 4), level.getMap());
        int velocity = 230;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collidableComponent);
        DamageComponent damageComponent = new DamageComponent(statsComponent);

        damageComponent.setReductionStrategy(new ReductionStrategy() {
            @Override
            public int reduce(Damage damage) {
                return damage.getValue();
            }
        });
        damageComponent.setReductionStrategy(new PercentageReductionStrategy(20));

        ArrayList<SkillBuilder> builders = new ArrayList<>();
        ArrayList<Cooldown> cooldowns = new ArrayList<>();
        SkillComponent skillComponent = new SkillComponent(
                builders,
                cooldowns,
                level,
                null,
                warriorSkillCosts,
                wariorSkillsUsageToLvl
        );

        Warrior player = new Warrior(
                240, 7310,
                statsComponent,
                movementComponent,
                damageComponent,
                collidableComponent,
                new DrawableComponent(rangerAnimationMap),
                skillComponent,
                inputState,
                level
        );

        skillComponent.setSkillUser(player);

        player.setPosition(240, 7300);

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new WarriorMeleeAttackSkill(level, (Warrior) skillUser);
            }
        });

        cooldowns.add(new Cooldown(0));

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new ThrowAndTakeSkill(level, (Warrior)skillUser);
            }
        });

        cooldowns.add(new Cooldown(0));

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new PoisonedKnifeSkill(level, skillUser);
            }
        });

        cooldowns.add(new Cooldown(3));

        builders.add(new SkillBuilder() {
            @Override
            public Skill build(Level level, Character skillUser) {
                return new DeathRunSkill(level, skillUser);
            }
        });

        cooldowns.add(new Cooldown(5));


        return player;
    }

    public static Weapon getNewWeapon(float x, float y, Level level, Warrior warrior, WeaponType type, float relaxation, float throwVelocity, int dmg, int size, float movementMultiplier) {
       fillWeapons();
        return new Weapon(x,y,new DrawableComponent(weaponTypes.get(type)),relaxation,throwVelocity,level,1, warrior,dmg,size,movementMultiplier);
    }

    public static SpearPoint getNewSpearPoint(float x, float y, Level level, float relaxation, float throwVelocity, int dmg, int size, float movementMultiplier, int number) {
        fillWeapons();
        return new SpearPoint(x, y, thiefAnimationMap.get("north"), level, relaxation, throwVelocity, dmg, size, movementMultiplier, number);
    }

    //    new Weapon(x,y,arrowAnimationMap.get(direction.toString()),0.1f,2.0f,level,1,this,100,4*4,6*6);
    public static OnePointEnemy getOnePointEnemy(String name, int collisionGroups, Level level) {
        Util.loadEnemy(name, Gdx.files.internal("stolen_assets/actors/" + name + ".xml"), loadedTextures, loadedProperties);
        CharacterProperties props = loadedProperties.get(name);

//        String behavior = props.properties.getOrDefault("behavior", "melee");
        Map<String,Integer> distances = new HashMap<>();
        distances.put("boss",200);
        distances.put("skeleton_1",250);

        ArrayList<SkillBuilder> skillBuilders = new ArrayList<>();
        ArrayList<Cooldown> cooldowns = new ArrayList<>();


        switch (name) {
            case "boss":
                skillBuilders.add(new SkillBuilder() {
                    @Override
                    public Skill build(Level level, Character skillUser) {
                        return new ArrowCircleSkill(level,skillUser,0.01f,2,16);
                    }
                });
                cooldowns.add(new Cooldown(0.5f));

                break;
            case "skeleton_1":
                skillBuilders.add(new SkillBuilder() {
                    @Override
                    public Skill build(Level level, Character skillUser) {
                        return new ShootArrowSkill(700, level, skillUser);
                    }
                });
                cooldowns.add(new Cooldown(1f));
                break;
            default:
                skillBuilders.add(new SkillBuilder() {
                    @Override
                    public Skill build(Level level, Character skillUser) {
                        return new MeleeAttackSkill(level, skillUser);
                    }
                });
                cooldowns.add(new Cooldown(2f));
                break;
//                throw new RuntimeException("Unknown monster behavior.");
        }

        SkillComponent skillComponent = new SkillComponent(
                skillBuilders,
                cooldowns,
                level,
                null,
                enemySkillCosts,
                enemySkillsUsageToLvl
        );

        StatsComponent statsComponent = new StatsComponent(
                Integer.parseInt(props.properties.get("hp")),
                Float.parseFloat(props.properties.get("speed")),
                1f,
                2);

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

        OnePointEnemy enemy = new FollowingPlayerEnemy(
                statsComponent,
                movementComponent,
                damageComponent,
                collidableComponent,
                new DrawableComponent(props.animations),
                skillComponent,
                level,
                collisionGroups);

        skillComponent.setSkillUser(enemy);
        if (distances.containsKey(name))

        ((FollowingPlayerEnemy) enemy).setATTACK_DISTANCE(distances.get(name));

        return enemy;
    }

}
