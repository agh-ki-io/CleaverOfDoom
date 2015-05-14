package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.entities.Character;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-14
 */
public class SkillComponent implements Updatable {
    private final List<SkillBuilder> skillBuilders;
    private final Collection<Skill> skills;
    private final Level level;
    private Character skillUser;

    public SkillComponent(List<SkillBuilder> skillBuilders, Level level, Character skillUser) {
        this.skillBuilders = skillBuilders;
        this.level = level;
        this.skillUser = skillUser;
        skills = new LinkedList<>();
    }

    public void useSkill(int id) {
        skills.add(skillBuilders.get(id).build(level, skillUser));
    }

    @Override
    public void update(float deltaTime) {
        for (Skill skill : skills) {
            skill.update(deltaTime);
        }

        //W javie nie mozna usuwac elementow w ludzki sposob w kolekcji ;_;
        Iterator<Skill> iterator = skills.iterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            if (skill.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    public void setSkillUser(Character skillUser) {
        this.skillUser = skillUser;
    }
}
