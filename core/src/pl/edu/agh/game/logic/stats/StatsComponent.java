package pl.edu.agh.game.logic.stats;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class StatsComponent {
    private float health;
    private float curHealth;
    private float movementSpeedMultiplier;
    private float attackSpeedMultiplier;
    private float regenRatio;
    private final long regenTimeMillis=1000;
    private long startTime;


    public StatsComponent(float health, float movementSpeedMultiplier, float attackSpeedMultiplier, float regenRatio) {
        this.health = health; //maxEnergy
        this.curHealth = health;
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.regenRatio = regenRatio;
        this.startTime=System.currentTimeMillis();
    }

    public float getHealth() {
        return curHealth;
    }

    public void regenerate(){
        if((System.currentTimeMillis()-this.startTime >= this.regenTimeMillis) && (this.curHealth<this.health) ) {
            this.curHealth += this.regenRatio;
            if (this.curHealth > this.health)
                this.curHealth=this.health;
            this.startTime=System.currentTimeMillis();
            //System.out.println("\n\tRegeneracja, current HP: " + this.curHealth);
        }
    }

    public float getMaxHealth(){
        return this.health;
    }

    public void incEnergy(float i){
        this.health+=i;
    }

    public void setHealth(float health) {
        this.curHealth = health;
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
        return curHealth <= 0;
    }
}
