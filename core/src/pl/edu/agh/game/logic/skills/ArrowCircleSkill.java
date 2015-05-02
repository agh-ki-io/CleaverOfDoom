package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-02
 */
public class ArrowCircleSkill extends Skill {
    private final static Direction[] directions = {Direction.EAST, Direction.NORTHEAST, Direction.NORTH, Direction.NORTHWEST, Direction.WEST, Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST};
    private final float stepDuration;
    private int arrowVelocity;
    private final int steps;
    private int currentStep = 0;
    private float lastShotFiredTime = 0;
    private float skillDurationLeft;

    public ArrowCircleSkill(Level level, pl.edu.agh.game.logic.entities.Character skillUser, float stepDuration, int arrowVelocity) {
        super(level, skillUser);
        this.stepDuration = stepDuration;
        this.arrowVelocity = arrowVelocity;
        steps = directions.length;
        skillDurationLeft = stepDuration * steps;
    }

    @Override
    public boolean isDestroyed() {
        return skillDurationLeft < 0;
    }

    @Override
    public void update(float deltaTime) {
        skillDurationLeft -= deltaTime;
        lastShotFiredTime += deltaTime;
        if (lastShotFiredTime > stepDuration) {
            lastShotFiredTime -= stepDuration;
            OneWayProjectile projectile = EntityFactory.getNewArrow(skillUser.getX(), skillUser.getY(), arrowVelocity, directions[currentStep], level);
            level.addCharacter(projectile);
            currentStep++;
        }
    }
}
