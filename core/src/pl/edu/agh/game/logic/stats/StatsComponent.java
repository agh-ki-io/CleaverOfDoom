package pl.edu.agh.game.logic.stats;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class StatsComponent {
    private float energy;
    private float maxEnergy;
    private float movementSpeedMultiplier;
    private float attackSpeedMultiplier;
    private long startTime;
    //private long regenTimeMillis;
    private static final long regenTimeMillis = 1000;
    private int[] skillsLevel;
    private int[] skillUsageCount;
    private int[] skillLvlUp;
    private float skillLUpMultipl;


    public StatsComponent(int energy, float movementSpeedMultiplier, float attackSpeedMultiplier) {
        this.maxEnergy = energy;
        this.energy = energy;
        this.startTime = System.currentTimeMillis();
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.skillsLevel =new int[3];
        this.skillUsageCount = new int[3];
        this.skillLvlUp = new int[3];
        for (int i=0; i<3;i++) {
            this.skillsLevel[i] = 1;
            this.skillUsageCount[i] = 0;
            this.skillLvlUp[i] = 7;
        }
        this.skillLUpMultipl = (float)1.3;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getMaxEnergy() {
        return this.maxEnergy;
    }

    public void incMaxEnergy(float inc){
        this.maxEnergy+=inc;
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
        return this.energy <= 0;
    }

    public void regen(float regen){
        if ((System.currentTimeMillis()-this.startTime >= regenTimeMillis) && this.energy < this.maxEnergy){
            this.energy+=regen;
            this.startTime = System.currentTimeMillis();
            if (this.energy>this.maxEnergy)
                this.energy=this.maxEnergy;
            System.out.println("Current energy: "+this.energy);
        }
    }

    public void consumeEnergy(float energyCost){
        this.energy-=energyCost;
        if(this.energy<0)
            this.energy = 0;
    }


    public void lvlUpSkill(int skillNumber){
        this.skillsLevel[skillNumber]++;
        this.skillLvlUp[skillNumber]=(int)(this.skillLvlUp[skillNumber]*this.skillLUpMultipl);
        this.skillUsageCount[skillNumber]=0;
    }

    public int getSkillUsageCount(int skillNumber){
        return this.skillUsageCount[skillNumber];
    }

    public int getSkillNextLvl(int skillNumber){
        return this.skillLvlUp[skillNumber];
    }

    public int getSkillLevel(int skillNumber){
        return this.skillsLevel[skillNumber];
    }

    public void inccSkillCount(int skillNumber){
        this.skillUsageCount[skillNumber]++;
    }

}
