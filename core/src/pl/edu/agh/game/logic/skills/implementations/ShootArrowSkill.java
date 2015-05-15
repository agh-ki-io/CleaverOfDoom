package pl.edu.agh.game.logic.skills.implementations;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.skills.Skill;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public class ShootArrowSkill extends Skill {
    public ShootArrowSkill(float velocity, Level level, Character skillUser, int collisionGroups) {
        super(level, skillUser);
        OneWayProjectile projectile = EntityFactory.getNewArrow(skillUser.getX(), skillUser.getY(), velocity, skillUser.drawableComponent.getLastUsableDirection(), level, collisionGroups);
        level.addCharacter(projectile);
    }

    public ShootArrowSkill(float velocity, Level level, Character skillUser) {
        this(velocity, level, skillUser, skillUser.getCollisionGroups());
    }

    @Override
    public boolean isDestroyed() {
        return true;
    }

    @Override
    public void update(float deltaTime) {
    }
}
