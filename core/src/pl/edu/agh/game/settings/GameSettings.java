package pl.edu.agh.game.settings;

import pl.edu.agh.game.logic.effects.Effect;

import java.util.List;
import java.util.Vector;

/**
 * Created by Admin on 2015-05-10.
 */
public class GameSettings {
    private static GameSettings ourInstance = new GameSettings();
    private float musicVolume = 0.1f;
    private float sfxVolume = 1.0f;
    private List<Effect> effectsToUpdate = new Vector<>();

    public List<Effect> getEffectsToUpdate() {
        return effectsToUpdate;
    }
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }
    public void setSfxVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }
    public float getMusicVolume(){
        return musicVolume;
    }
    public float getSfxVolume(){
        return sfxVolume;
    }

    public static GameSettings getInstance() {
        return ourInstance;
    }

    private GameSettings() {
    }
}
