package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.entities.players.Spearman;

/**
 * Created by Admin on 2015-05-13.
 */
public class ThrowAndTakeSkill extends Skill {
    public ThrowAndTakeSkill(Level level, Spearman skillUser) {
        super(level, skillUser);
        skillUser.throwOrTake();
    }
    @Override
    public boolean isDestroyed() {
        return true;
    }
    @Override
    public void update(float deltaTime) {
    }
}
