package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.entities.Character;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public abstract class Skill implements Updatable, GameEntity {
    protected final Level level;
    protected final Character skillUser;

    public Skill(Level level, Character skillUser) {
        this.level = level;
        this.skillUser = skillUser;
    }
}
