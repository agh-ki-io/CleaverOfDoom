package pl.edu.agh.game.logic.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.entities.*;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.settings.GameSettings;

import javax.swing.*;

/**
 * Created by Michal Furmanek on 2015-05-10.
 */
public class FireEffect extends Effect {
    GameSettings settings = GameSettings.getInstance();
//    static Music fireSound;
//    static Music fireSoundStill;
    private float ttl;
    private FireEffect parent;

    public FireEffect(float ttl, boolean init) {
        super(ttl);
//        JOptionPane.showMessageDialog(null,"Create " + this);
        System.out.println("Create " + this);
        this.ttl = ttl;
//        this.silent = silent;
//        this.parent = parent;
        if (init) {
            settings.fireSound.setVolume(settings.getSfxVolume());
            if (!settings.fireSound.isPlaying())
                settings.fireSound.play();
            settings.fireSoundStill.setVolume(GameSettings.getInstance().getSfxVolume());
            settings.fireSoundStill.setLooping(true);
            if (!settings.fireSoundStill.isPlaying())
                settings.fireSoundStill.play();
        }
    }

    public void act(Character character) {
        character.damage(new Damage(DamageType.FIRE,20));
    }

    public void dispose() {
//        JOptionPane.showMessageDialog(null,"Dispose " + this);
        System.out.println("Dispose "+this);
        settings.fireSound.stop();
        settings.fireSoundStill.stop();
        settings.getEffectsToDelete().add(this);
    }

//    @Override
//    public Effect copy() {
//        return new FireEffect(ttl, false);
//    }

    @Override
    public void addToSet(Character character) {
        characters.add(character);
    }


}
