package pl.edu.agh.game.logic.skills;

import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.util.Cooldown;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-14
 */
public class SkillComponent implements Updatable {
    private final int numberOfSkills= 4;
    private final List<SkillBuilder> skillBuilders;
    private final List<Cooldown> skillCooldowns;
    private final Level level;
    private final Collection<Skill> skills;
    private Character skillUser;
    private final List<Integer> skillCosts;
    private int[] skillsLvls;
    private int[] skillsUsageCount;
    private int[] skillsUsageToLvlUp;


    public SkillComponent(List<SkillBuilder> skillBuilders, List<Cooldown> skillCooldowns, Level level, Character skillUser,List<Integer> skillCosts, int[] s) {
        this.skillBuilders = skillBuilders;
        this.skillCooldowns = skillCooldowns;
        this.level = level;
        this.skillUser = skillUser;
        this.skillCosts = skillCosts;
        this.skillsLvls = new int[this.numberOfSkills];
        this.skillsUsageToLvlUp = new int[this.numberOfSkills];
        this.skillsUsageCount = new int[this.numberOfSkills];
        for (int i=0;i<this.numberOfSkills;i++) {
            this.skillsLvls[i] = 1;
            this.skillsUsageToLvlUp[i]=s[i];
            this.skillsUsageCount[i]=0;
        }
        skills = new LinkedList<>();
    }

    public int getSkillCosts(int id){
        return skillCosts.get(id);
    }

    public int getSkillLvl(int id){
        if (id<numberOfSkills)
            return this.skillsLvls[id];
        return 1;
    }
    
    public void useSkill(int id) {
        Cooldown cooldown = skillCooldowns.get(id);
        if (cooldown.isOver()) {
            skills.add(skillBuilders.get(id).build(level, skillUser));
            tryToLvlUpSkill(id);
            cooldown.reset();
        }
    }

    public void tryToLvlUpSkill(int id){
        this.skillsUsageCount[id]++;
        if(this.skillsUsageCount[id]>=this.skillsUsageToLvlUp[id]){
            this.skillsLvls[id]++;
            this.skillsUsageCount[id]=0;
            this.skillsUsageToLvlUp[id]*=1.3;
        }
        //System.out.println("\n\tSkill: "+id+"\n\tLvl: "+this.skillsLvls[id]+"\n\tUsage count: "+this.skillsUsageCount[id] +
          //      "\n\tSkill usage to lvl up: "+this.skillsUsageToLvlUp[id]);
    }

    @Override
    public void update(float deltaTime) {
        for (Cooldown skillCooldown : skillCooldowns) {
            skillCooldown.update(deltaTime);
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

    public List<Cooldown> getSkillCooldowns() {
        return skillCooldowns;
    }
}
