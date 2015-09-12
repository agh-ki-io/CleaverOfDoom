package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.players.Warrior;
import pl.edu.agh.game.settings.GameSettings;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class DeathRunEffect extends Effect {
    GameSettings settings = GameSettings.getInstance();
    private float ttl;

    public DeathRunEffect(float ttl) {
        super(ttl);
        this.ttl = ttl;
    }

    public void act(Character character) {
    }

    public void dispose() {
        ((Warrior) getFirst()).multiplier = 1;
        ((Warrior) getFirst()).hurt = false;
        settings.getEffectsToDelete().add(this);
    }

    @Override
    public void addToSet(Character character) {
        if (character instanceof Warrior) {
            characters.add(character);
            ((Warrior) character).multiplier = 3;
            ((Warrior) character).hurt = true;
        }
    }
}
