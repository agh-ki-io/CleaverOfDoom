package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.players.Spearman;
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
        ((Spearman) getFirst()).multiplier = 1;
        ((Spearman) getFirst()).hurt = false;
        settings.getEffectsToDelete().add(this);
    }

    @Override
    public void addToSet(Character character) {
        if (character instanceof Spearman) {
            characters.add(character);
            ((Spearman) character).multiplier = 3;
            ((Spearman) character).hurt = true;
        }
    }
}
