package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public class ShootArrowSkill extends Skill {
    private static final float cost = 20;
    private static final int baseDmg = 100;
    private int skillLvlBonus;
    public ShootArrowSkill(float velocity, Level level, Character skillUser,int skillLvl) {
        super(level, skillUser,cost);
        OneWayProjectile projectile = EntityFactory.getNewArrow(skillUser.getX(), skillUser.getY(), velocity, skillUser.drawableComponent.getLastUsableDirection(), level);
        level.addCharacter(projectile);
    }

    @Override
    public boolean isDestroyed() {
        return true;
    }

    @Override
    public void update(float deltaTime) {
    }
}
