package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.collisions.CollisionUtil;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.drawable.WeaponType;
import pl.edu.agh.game.logic.entities.projectiles.SpearPoint;
import pl.edu.agh.game.logic.entities.projectiles.Weapon;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.*;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.EntityFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class Warrior extends ComponentPlayer {
//    private final InputState inputState;
//    private boolean destroyed = false;
    private Weapon weapon;
    private Weapon fist;
    private boolean taker = false;
    private float ttl = 0;
    public boolean hurt = false;
    public float multiplier = 1;
    private final Damage damage = new Damage(DamageType.PHYSICAL, 500);

//    private int collisionGroups = 1;

    Collection<Skill> skills = new LinkedList<>();

    public Warrior(float x, float y, StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, SkillComponent skillComponent, InputState inputState, Level level) {
        super(statsComponent, movementComponent, damageComponent, collidableComponent, drawableComponent, skillComponent, inputState, level);
        super.setPosition(x, y);
        //Animation animation, float relaxation, float throwVelocity, Level level, int collisionGroup, Spearman spearman, int dmg
        weapon = EntityFactory.getNewWeapon(getX(),getY(),level, this, WeaponType.SPEAR, 0.035f,30.0f,100,4*4,6*6);//new Weapon(x,y,arrowAnimationMap.get(direction.toString()),0.1f,2.0f,level,1,this,100,4*4,6*6);
        fist = EntityFactory.getNewWeapon(getX(),getY(),level, this, WeaponType.FIST, 1.0f,0.0f,20,2*2,4*5);
    }

    public Direction getDirection() {
        return movementComponent.getDirection();
    }

    @Override
    public void update(float deltaTime) {
        if (ttl > 0) {
            ttl -= deltaTime;
        } else {
            taker = false;
        }
        super.update(deltaTime);
        weapon.setWarrior(this);
        updateSkills(deltaTime);
        useSkills();
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime*multiplier);
        if (!weapon.equals(fist)) weapon.movementComponent.move(inputState.getxDirection(), inputState.getyDirection(), deltaTime * multiplier);
        collidableComponent.getShape().setPosition(getX(), getY());
    }

//    @Override
//    protected void useSkills() {
////        if (drawableComponent.isFree()) {
////            if (inputState.isSkill1Used()) {
////                drawableComponent.setAnimation(AnimationType.ATTACK);
////                skills.add(new SpearmanMeleeAttackSkill(level, this));
//////                skills.add(new MeleeAttackSkill(level, this));
////            }
////            else if (inputState.isSkill2Used()) {
////                drawableComponent.setAnimation(AnimationType.CHANNELLING);
////                skills.add(new ThrowAndTakeSkill(level, this));
////            }
////            else if (inputState.isSkill3Used()) {
////                drawableComponent.setAnimation(AnimationType.ATTACK);
////                skills.add(new PoisonedKnifeSkill(level,this));
////            }
////            else if (inputState.isSkill4Used()) {
////                drawableComponent.setAnimation(AnimationType.ATTACK);
////                skills.add(new DeathRunSkill(level,this));
////            }
////        }
//    }

    private void updateSkills(float deltaTime) {
        for (Skill skill : skills) {
            skill.update(deltaTime);
        }
        //W javie nie mozna usuwac elementow w ludzki sposob w kolekcji ;_;
        Iterator<Skill> iterator = skills.iterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            if (skill.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    public void throwOrTake() {
        if (weapon.equals(fist)) {
            taker = true;
            ttl = 0.5f;
        } else {
            weapon.throwThis();
            weapon = fist;
            taker = false;
        }
    }

    @Override
    public void collide(Collidable collidable) {
        if (collidable instanceof Weapon && taker) {
            weapon = (Weapon)collidable;
            weapon.setWarrior(this);
        } else if (hurt && CollisionUtil.collisonGroupMatches(getCollisionGroups(), collidable.getCollisionGroups())) {
            if (collidable instanceof Damagable) {
                ((Damagable) collidable).damage(damage);
            }
        }
        if (collidable instanceof SpearPoint && taker) {
            Weapon tmp = ((SpearPoint)collidable).getNextSpear();
            if (tmp!=null) {
                weapon = ((SpearPoint)collidable).getNextSpear();
                weapon.setWarrior(this);
                taker = false;
            }
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

//    @Override
//    public int getCollisionGroups() {
//        return collisionGroups;
//    }



//        @Override
//    public void draw(SpriteBatch batch) {
//        super.draw(batch);
//
//        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);
//    }

//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        updateSkills(deltaTime);
//        useSkills();
//        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);
//        collidableComponent.getShape().setPosition(getX(), getY());
//    }

//    private void useSkills() {
//        if (drawableComponent.isFree()) {
//            if (inputState.isSkill1Used()) {
//                drawableComponent.setAnimation(AnimationType.ATTACK);
//                skills.add(new ShootArrowSkill(700, level, this));
////                skills.add(new MeleeAttackSkill(level, this));
//            }
//            else if (inputState.isSkill2Used()) {
//                drawableComponent.setAnimation(AnimationType.CHANNELLING);
//                skills.add(new ArrowCircleSkill(level, this, 0.031f, 700));
//            }
//            else if (inputState.isSkill3Used()) {
//                drawableComponent.setAnimation(AnimationType.ATTACK);
//                skills.add(new FinalAnnihilationSkill(level, this));
//            }
//        }
//    }


//    @Override
//    public boolean isDestroyed() {
//        return destroyed;
//    }
}
