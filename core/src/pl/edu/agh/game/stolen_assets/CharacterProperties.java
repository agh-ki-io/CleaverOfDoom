package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.utils.IntMap;
import pl.edu.agh.game.graphics.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-10
 */
public class CharacterProperties {
    public final Map<String, Animation> animations;
    public final Map<String, String> properties;
    public final IntMap<String> loot;

    public CharacterProperties() {
        this.animations = new HashMap<>();
        this.properties = new HashMap<>();
        this.loot = new IntMap<>();
    }
}
