package pl.edu.agh.game.logic.damage;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class Damage {
    DamageType type;
    int value;

    public Damage(DamageType type, int value) {
        this.type = type;
        this.value = value;
    }

    public DamageType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
