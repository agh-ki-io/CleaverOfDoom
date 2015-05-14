package pl.edu.agh.game.logic.damage;

import java.util.Random;

/**
 * @author - Michal Furmanek
 *         Created on  2015-04-16
 */
public class PercentageReductionStrategy implements ReductionStrategy {
    private Random rand = new Random();
    private int percent;
    public PercentageReductionStrategy(int percent) {
        this.percent = percent;
    }

    public int reduce(Damage damage) {
        if ((rand.nextInt()%100) < percent) return 0;
        else return damage.getValue();
    }

}
