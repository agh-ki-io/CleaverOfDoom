package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.players.Spearman;
import pl.edu.agh.game.logic.entities.projectiles.StaticShapedAttack;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-01
 */
public class SpearmanMeleeAttackSkill extends Skill {
    private final StaticShapedAttack attack;
    //o ile od srodka gracza w danym kierunku przesunac srodek ataku. (pixele * skala)
    private static float DELTA_MULTIPLIER;// = 6 * 7;

    public SpearmanMeleeAttackSkill(Level level, Spearman skillUser) {
        super(level, skillUser);
        DELTA_MULTIPLIER = skillUser.getWeapon().getMovementMultiplier();
        attack = new StaticShapedAttack(skillUser.getX(), skillUser.getY(), skillUser.getWeapon().getSize(), new Damage(DamageType.PHYSICAL, skillUser.getWeapon().getDamage().getValue()), 1, 0.2f);
        level.addCharacter(attack);
    }

    @Override
    public boolean isDestroyed() {
        return attack.isDestroyed();
    }

    @Override
    public void update(float deltaTime) {
        float dx = skillUser.drawableComponent.getLastUsableDirection().getDx() * DELTA_MULTIPLIER;
        float dy = skillUser.drawableComponent.getLastUsableDirection().getDy() * DELTA_MULTIPLIER;
        attack.setPosition(skillUser.getX() + dx, skillUser.getY() + dy);
    }
}
