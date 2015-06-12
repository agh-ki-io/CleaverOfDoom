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

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class ComponentPlayer extends Player {
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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        inputState.showMenu();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        super.regenerate();
        useSkills();
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);
        collidableComponent.getShape().setPosition(getX(), getY());
        if (movementComponent.getVelocity()==-1) destroy();
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
