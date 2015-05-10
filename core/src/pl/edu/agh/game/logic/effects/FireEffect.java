package pl.edu.agh.game.logic.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class FireEffect extends Effect {
    Sound fireSound;
    Music fireSoundStill;

    public FireEffect(float ttl) {
        super(ttl);
        fireSound = Gdx.audio.newSound(Gdx.files.internal("fire.mp3"));
        fireSound.play();
        fireSoundStill = Gdx.audio.newMusic(Gdx.files.internal("fire2.mp3"));
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
