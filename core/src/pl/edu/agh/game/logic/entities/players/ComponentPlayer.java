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
import pl.edu.agh.game.logic.effects.Effect;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.ArrowCircleSkill;
import pl.edu.agh.game.logic.skills.FinalAnnihilationSkill;
import pl.edu.agh.game.logic.skills.ShootArrowSkill;
import pl.edu.agh.game.logic.skills.Skill;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.settings.GameSettings;
import pl.edu.agh.game.stolen_assets.Debug;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer extends Player {
    private final InputState inputState;
    private boolean destroyed = false;

    private int collisionGroups = 1;

    Collection<Skill> skills = new LinkedList<>();

    public ComponentPlayer(float x , float y, StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, InputState inputState, Level level) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, level);
        this.inputState = inputState;

        setPosition(x, y);
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
        updateSkills(deltaTime);
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
            else if (inputState.isSkill3Used()) {
                drawableComponent.setAnimation(AnimationType.ATTACK);
                skills.add(new FinalAnnihilationSkill(level, this));
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
}
