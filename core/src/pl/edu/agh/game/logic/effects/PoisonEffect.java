package pl.edu.agh.game.logic.effects;

import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.settings.GameSettings;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class PoisonEffect extends Effect {
    GameSettings settings = GameSettings.getInstance();
    private float ttl;
    public PoisonEffect(float ttl, boolean init) {
        super(ttl);
        this.ttl = ttl;
        if (init) {
            settings.poisonSound.setVolume(settings.getSfxVolume());
            if (!settings.poisonSound.isPlaying())
                settings.poisonSound.play();
        }
    }
    public void act(Character character) {
        character.damage(new Damage(DamageType.POISON,5));
    }
    public void dispose() {
        settings.poisonSound.stop();
        settings.getEffectsToDelete().add(this);
    }
    @Override
    public void addToSet(Character character) {
        characters.add(character);
    }
}
