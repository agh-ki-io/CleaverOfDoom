package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.graphics.AnimationType;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Debug;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer extends Player {
    private final InputState inputState;
    private boolean destroyed = false;

    private int collisionGroups = 1;

    public ComponentPlayer(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, SkillComponent skillComponent, InputState inputState, Level level) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, skillComponent, level, inputState);
    }

    @Override
    public void collide(Collidable collidable) {
    }

    @Override
    public void damage(Damage damage) {
        super.damage(damage);
        System.out.println(this + " received: " + damage.getValue() + " " + damage.getType() + " damage.");
        System.out.println("Health left: " + statsComponent.getHealth());
    }

    @Override
    public int getCollisionGroups() {
        return collisionGroups;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        useSkills();
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);
        collidableComponent.getShape().setPosition(getX(), getY());
    }

    private void useSkills() {
        if (drawableComponent.isFree()) {
            if (inputState.isSkill1Used()) {
                drawableComponent.setAnimation(AnimationType.ATTACK);
                skills.add(new ShootArrowSkill(700, level, this));
//                skills.add(new MeleeAttackSkill(level, this));
            }
            else if (inputState.isSkill2Used()) {
                drawableComponent.setAnimation(AnimationType.CHANNELLING);
                skills.add(new ArrowCircleSkill(level, this, 0.031f, 700));
            }
        }
    }

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

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void useSkill(int id) {
        switch (id) {
            case 0:
                drawableComponent.setAnimation(AnimationType.ATTACK);
                skillComponent.useSkill(0);
                break;
            case 1:
                drawableComponent.setAnimation(AnimationType.CHANNELLING);
                skillComponent.useSkill(1);
                break;
            case 2:
                drawableComponent.setAnimation(AnimationType.CHANNELLING);
                skillComponent.useSkill(2);
                break;
            case 3:
                drawableComponent.setAnimation(AnimationType.CHANNELLING);
                skillComponent.useSkill(3);
                break;
                default:
                    throw new RuntimeException("Character does not have skill of given id.");
        }
    }
}
