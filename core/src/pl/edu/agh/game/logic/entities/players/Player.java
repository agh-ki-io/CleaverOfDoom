package pl.edu.agh.game.logic.entities.players;

import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public abstract class Player extends pl.edu.agh.game.logic.entities.Character<Circle> {
    public Player(StatsComponent statsComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, MovementComponent movementComponent, Level level) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, level);
    }

    public void incHP(){
        super.useSkill();
    }

    public enum Profession {
        ROGUE,
        BARBARIAN,
        ARCHER,
        WARRIOR,
    }
}
