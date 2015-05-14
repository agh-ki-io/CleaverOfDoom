package pl.edu.agh.game.logic.stats;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class StatsComponent {
    private int health;
    private int maxHealth;
    private float movementSpeedMultiplier;
    private float attackSpeedMultiplier;

    public StatsComponent(int health, float movementSpeedMultiplier, float attackSpeedMultiplier) {
        this.maxHealth = health;
        this.health = health;
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void incMaxHealth(int inc){
        this.maxHealth+=inc;
    }

    public float getMovementSpeedMultiplier() {
        return movementSpeedMultiplier;
    }

    public void setMovementSpeedMultiplier(float movementSpeedMultiplier) {
        this.movementSpeedMultiplier = movementSpeedMultiplier;
    }

    public float getAttackSpeedMultiplier() {
        return attackSpeedMultiplier;
    }

    public void setAttackSpeedMultiplier(float attackSpeedMultiplier) {
        this.attackSpeedMultiplier = attackSpeedMultiplier;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
