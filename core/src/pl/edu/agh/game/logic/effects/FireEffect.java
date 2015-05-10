package pl.edu.agh.game.logic.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.settings.GameSettings;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class FireEffect extends Effect {
    Music fireSound;
    Music fireSoundStill;

    public FireEffect(float ttl) {
        super(ttl);
        fireSound = Gdx.audio.newMusic(Gdx.files.internal("fire.mp3"));
        fireSound.setVolume(GameSettings.getInstance().getSfxVolume());
        fireSound.play();
        fireSoundStill = Gdx.audio.newMusic(Gdx.files.internal("fire2.mp3"));
        fireSoundStill.setVolume(GameSettings.getInstance().getSfxVolume());
        fireSoundStill.setLooping(true);
        fireSoundStill.play();
    }

    public void act(Character character) {
        character.damage(new Damage(DamageType.FIRE,20));
    }

    public void dispose() {
        fireSound.dispose();
        fireSoundStill.dispose();
    }

}
