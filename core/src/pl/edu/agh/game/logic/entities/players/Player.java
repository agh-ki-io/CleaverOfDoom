package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public abstract class Player extends pl.edu.agh.game.logic.entities.Character<Circle> {
    protected final InputState inputState;
    public Player(StatsComponent statsComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, MovementComponent movementComponent, SkillComponent skillComponent, Level level, InputState inputState) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, skillComponent, level);
        this.inputState = inputState;
    }

    public enum Profession {
        ROGUE,
        BARBARIAN,
        ARCHER,
        WARRIOR,
    }

    protected void regenerate(){
        this.statsComponent.regenerate();
    }

    protected void useSkills() {
        if (drawableComponent.isFree()) {
            if (inputState.isSkill1Used() && statsComponent.getHealth() >= skillComponent.getSkillCosts(0) && skillComponent.getSkillCooldowns().get(0).isOver()) {
                this.statsComponent.setHealth(this.statsComponent.getHealth() - this.skillComponent.getSkillCosts(0));
                this.statsComponent.incEnergy(0.2f);
                useSkill(0);
                //System.out.println("\n\tCurrent health: " + this.statsComponent.getHealth() + "\n\tMax health: " + this.statsComponent.getMaxHealth());
            } else if (inputState.isSkill2Used() && statsComponent.getHealth() >= skillComponent.getSkillCosts(1) && skillComponent.getSkillCooldowns().get(1).isOver()) {
                this.statsComponent.setHealth(this.statsComponent.getHealth() - this.skillComponent.getSkillCosts(1));
                this.statsComponent.incEnergy(0.22f);
                useSkill(1);
                //System.out.println("\n\tCurrent health: " + this.statsComponent.getHealth() + "\n\tMax health: " + this.statsComponent.getMaxHealth());
            } else if (inputState.isSkill3Used() && statsComponent.getHealth() >= skillComponent.getSkillCosts(2) && skillComponent.getSkillCooldowns().get(2).isOver()) {
                this.statsComponent.setHealth(this.statsComponent.getHealth() - this.skillComponent.getSkillCosts(2));
                this.statsComponent.incEnergy(0.33f);
                useSkill(2);
                //System.out.println("\n\tCurrent health: " + this.statsComponent.getHealth() + "\n\tMax health: " + this.statsComponent.getMaxHealth());
            } else if (inputState.isSkill4Used() && statsComponent.getHealth() >= skillComponent.getSkillCosts(3) && skillComponent.getSkillCooldowns().get(3).isOver()) {
                this.statsComponent.setHealth(this.statsComponent.getHealth() - this.skillComponent.getSkillCosts(3));
                this.statsComponent.incEnergy(0.5f);
                useSkill(3);
                //System.out.println("\n\tCurrent health: " + this.statsComponent.getHealth() + "\n\tMax health: " + this.statsComponent.getMaxHealth());
            }
        }
    }
}
