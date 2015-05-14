package pl.edu.agh.game.logic.damage;

import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class DamageComponent {
    private ReductionStrategy reductionStrategy;
    private final StatsComponent statsComponent;

    public DamageComponent(StatsComponent statsComponent) {
        this.statsComponent = statsComponent;
    }

    public ReductionStrategy getReductionStrategy() {
        return reductionStrategy;
    }

    public void setReductionStrategy(ReductionStrategy reductionStrategy) {
        this.reductionStrategy = reductionStrategy;
    }

    public void receiveDamage(Damage damage) {
        statsComponent.incMaxHealth(1);
        statsComponent.setHealth(statsComponent.getHealth() - reductionStrategy.reduce(damage));
    }
}
