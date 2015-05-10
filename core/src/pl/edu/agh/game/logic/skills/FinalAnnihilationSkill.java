package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.effects.FireEffect;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.entities.projectiles.StaticShapedAttack;
import pl.edu.agh.game.logic.entities.projectiles.StaticShapedEffect;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-02
 */
public class FinalAnnihilationSkill extends Skill {
    private final StaticShapedAttack attack;
    private final StaticShapedEffect effect;
//    private final FireEffect effect;
    //o ile od srodka gracza w danym kierunku przesunac srodek ataku. (pixele * skala)
    private static final float DELTA_MULTIPLIER = 4 * 4;

    public FinalAnnihilationSkill(Level level, pl.edu.agh.game.logic.entities.Character skillUser) {
        super(level, skillUser);
        effect = new StaticShapedEffect(skillUser.getX(), skillUser.getY(), 300, 2, 0.2f, new FireEffect(5));
        attack = new StaticShapedAttack(skillUser.getX(), skillUser.getY(), 4 * 4, new Damage(DamageType.PHYSICAL, 9000000), 1, 0.2f);
        level.addCharacter(attack);
        level.addCharacter(effect);
    }

    @Override
    public boolean isDestroyed() {
        return attack.isDestroyed() && effect.isDestroyed();
    }

    @Override
    public void update(float deltaTime) {
        float dx = skillUser.drawableComponent.getLastUsableDirection().getDx() * DELTA_MULTIPLIER;
        float dy = skillUser.drawableComponent.getLastUsableDirection().getDy() * DELTA_MULTIPLIER;
        attack.setPosition(skillUser.getX() + dx, skillUser.getY() + dy);
        effect.setPosition(skillUser.getX(), skillUser.getY());
    }
}
