package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.projectiles.StaticShapedAttack;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-01
 */
public class RogueMeleeAttackSkill extends Skill {
    private final StaticShapedAttack attack1;
    private final StaticShapedAttack attack2;
    //o ile od srodka gracza w danym kierunku przesunac srodek ataku. (pixele * skala)
    private static final float DELTA_MULTIPLIER = 5 * 5;

    public RogueMeleeAttackSkill(Level level, Character skillUser) {
        super(level, skillUser);
        attack1 = new StaticShapedAttack(skillUser.getX(), skillUser.getY(), 3 * 3, new Damage(DamageType.PHYSICAL, 200), 1, 0.2f);
        attack2 = new StaticShapedAttack(skillUser.getX(), skillUser.getY(), 3 * 3, new Damage(DamageType.PHYSICAL, 200), 1, 0.2f);
        level.addCharacter(attack1);
        level.addCharacter(attack2);
    }

    @Override
    public boolean isDestroyed() {
        return attack1.isDestroyed() && attack2.isDestroyed();
    }

    @Override
    public void update(float deltaTime) {
        float dx = skillUser.drawableComponent.getLastUsableDirection().getDx() * DELTA_MULTIPLIER;
        float dy = skillUser.drawableComponent.getLastUsableDirection().getDy() * DELTA_MULTIPLIER;
        attack1.setPosition(skillUser.getX() + dx * 1.2f + 4, skillUser.getY() + dy * 0.9f);
        attack2.setPosition(skillUser.getX() + dx * 0.9f - 4, skillUser.getY() + dy * 1.2f);
    }
}
