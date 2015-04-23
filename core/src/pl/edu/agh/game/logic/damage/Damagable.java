package pl.edu.agh.game.logic.damage;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public interface Damagable {
    void damage(Damage damage);
    void destroy();
}
