package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.effects.DeathRunEffect;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.entities.projectiles.StaticShapedEffect;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-02
 */
public class DeathRunSkill extends Skill {
    private final StaticShapedEffect effect;
    public DeathRunSkill(Level level, pl.edu.agh.game.logic.entities.Character skillUser) {
        super(level, skillUser);
        effect = new StaticShapedEffect(skillUser.getX(), skillUser.getY(), 2 * 2, 16, 0.2f, new DeathRunEffect(2));
        level.addCharacter(effect);
        level.addCharacter(EntityFactory.getNewSpearPoint(skillUser.getX(), skillUser.getY(), level, 0.035f, 30.0f, 100, 4 * 4, 6 * 6, 10));
    }
    @Override
    public boolean isDestroyed() {
        return effect.isDestroyed();
    }
    @Override
    public void update(float deltaTime) {
        effect.setPosition(skillUser.getX(), skillUser.getY());
    }
}