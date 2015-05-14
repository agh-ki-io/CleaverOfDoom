package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.util.CoolDown;

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
    private final List<CoolDown> skillCoolDowns;
    private final Level level;
    private final Collection<Skill> skills;
    private Character skillUser;

    public SkillComponent(List<SkillBuilder> skillBuilders, List<CoolDown> skillCoolDowns, Level level, Character skillUser) {
        this.skillBuilders = skillBuilders;
        this.skillCoolDowns = skillCoolDowns;
        this.level = level;
        this.skillUser = skillUser;
        skills = new LinkedList<>();
    }

    public void useSkill(int id) {
        CoolDown coolDown = skillCoolDowns.get(id);
        if (coolDown.isOver()) {
            skills.add(skillBuilders.get(id).build(level, skillUser));
            coolDown.reset();
        }
    }

    @Override
    public void update(float deltaTime) {
        for (CoolDown skillCoolDown : skillCoolDowns) {
            skillCoolDown.update(deltaTime);
        }

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

    public List<CoolDown> getSkillCoolDowns() {
        return skillCoolDowns;
    }
}
