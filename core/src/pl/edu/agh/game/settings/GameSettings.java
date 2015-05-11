package pl.edu.agh.game.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import pl.edu.agh.game.logic.effects.Effect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Admin on 2015-05-10.
 */
public class GameSettings {
    private static GameSettings ourInstance = new GameSettings();
    private float musicVolume = 0.1f;
    private float sfxVolume = 1.0f;
    public Music fireSound = Gdx.audio.newMusic(Gdx.files.internal("fire.mp3"));
    public Music fireSoundStill = Gdx.audio.newMusic(Gdx.files.internal("fire2.mp3"));
    private List<Effect> effectsToUpdate = new Vector<>();
    private List<Effect> effectsToDelete = new Vector<>();
    private HashSet<Music> musicEffects = new HashSet<>();

    public List<Effect> getEffectsToUpdate() {
        return effectsToUpdate;
    }
    public List<Effect> getEffectsToDelete() {
        return effectsToDelete;
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

    public Set<Music> getMusicEffects() { return musicEffects; }

    private GameSettings() {
    }
}
