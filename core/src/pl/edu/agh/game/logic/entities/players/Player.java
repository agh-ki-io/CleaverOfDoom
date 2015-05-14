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

    protected void useSkills() {
        if (drawableComponent.isFree()) {
            if (inputState.isSkill1Used() && skillComponent.getSkillCooldowns().get(0).isOver()) {
                useSkill(0);
            }
            else if (inputState.isSkill2Used() && skillComponent.getSkillCooldowns().get(1).isOver()) {
                useSkill(1);
            }
            else if (inputState.isSkill3Used() && skillComponent.getSkillCooldowns().get(2).isOver()) {
                useSkill(2);
            }
            else if (inputState.isSkill4Used() && skillComponent.getSkillCooldowns().get(3).isOver()) {
                useSkill(3);
            }
        }
    }
}
