package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-14
 */
public interface SkillBuilder {
    Skill build(Level level, pl.edu.agh.game.logic.entities.Character skillUser);
}
