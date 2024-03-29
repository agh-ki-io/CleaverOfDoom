package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.graphics.Frame;
import pl.edu.agh.game.graphics.VariableFrameDurationAnimation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-03-31
 */
public class Util {

    public static void loadEnemy(String name, FileHandle xmlFile, Map<String, Texture> loadedTextures, Map<String, CharacterProperties> loadedProperties) {
        if (!loadedProperties.containsKey(name) || loadedProperties.get(name) == null) {
            XmlReader reader = new XmlReader();
            CharacterProperties characterProperties = new CharacterProperties();
            loadedProperties.put(name, characterProperties);
            try {
                XmlReader.Element parsed = reader.parse(xmlFile);

                //atrybuty z naglowka
                for (ObjectMap.Entry<String, String> stringStringEntry : parsed.getAttributes()) {
                    characterProperties.properties.put(stringStringEntry.key, stringStringEntry.value);
                }

                //atr z dictionary
                XmlReader.Element behavior = parsed.getChildByName("behavior");
                XmlReader.Element dictionary = behavior.getChildByName("dictionary");

                for (int i = 0; i < dictionary.getChildCount(); i++) {
                    XmlReader.Element element = dictionary.getChild(i);
                    if (element.getName().equals("entry")) characterProperties.properties.put(element.getAttribute("name"), element.getChild(0).getText());
                }

                //loot z dict
                for (XmlReader.Element child : dictionary.getChildrenByName("array")) {
                    if (child.getAttribute("name").equals("loot")) {
                        for (int i = 0; i < child.getChildCount(); i+=2) {
                            characterProperties.loot.put(Integer.parseInt(child.getChild(i).getText()), child.getChild(i+1).getText());
                        }
                    }
                }

                Array<XmlReader.Element> sprites = parsed.getChildrenByName("sprite");

                for (XmlReader.Element sprite : sprites) {
                    String animationName = sprite.getAttribute("name");
                    int scale = Integer.valueOf(sprite.getAttribute("scale"));

                    String texture = "stolen_assets" + File.separator + sprite.getChildByName("texture").getText();

                    if (!loadedTextures.containsKey(texture)) {
                        loadedTextures.put(texture, new Texture(Gdx.files.internal(texture)));
                    }

                    Texture tx = loadedTextures.get(texture);

                    String[] origin = sprite.getChildByName("origin").getText().split(" ");
                    Array<XmlReader.Element> frames = sprite.getChildrenByName("frame");

                    Array<Frame> frams = new Array<Frame>();

                    int duration;

                    for (XmlReader.Element frame : frames) {
                        duration = frame.getIntAttribute("time", 1);
                        int[] values = stringArrayToIntArray(frame.getText().split(" "));
                        frams.add(new Frame(
                                (float) duration / 1000,
                                new TextureRegion(tx, values[0], values[1], values[2], values[3]),
                                Integer.valueOf(origin[0]),
                                Integer.valueOf(origin[1])
                        ));
                    }

                    characterProperties.animations.put(animationName, new VariableFrameDurationAnimation(frams));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Animation> playerAnimationFromXml(FileHandle xmlFile, Map<String, Texture> loadedTextures, Map<String, String> properties) {
        XmlReader reader = new XmlReader();
        Map<String, Animation> result = new HashMap<String, Animation>();
        try {
            XmlReader.Element parsed = reader.parse(xmlFile);

            properties.put("collision", parsed.getAttribute("collision"));

            Array<XmlReader.Element> sprites = parsed.getChildrenByName("sprite");

            for (XmlReader.Element sprite : sprites) {
                String animationName = sprite.getAttribute("name");
                int scale = Integer.valueOf(sprite.getAttribute("scale"));

                String texture = "stolen_assets" + File.separator + sprite.getChildByName("texture").getText();

                if (!loadedTextures.containsKey(texture)) {
                    loadedTextures.put(texture, new Texture(Gdx.files.internal(texture)));
                }

                Texture tx = loadedTextures.get(texture);

                String[] origin = sprite.getChildByName("origin").getText().split(" ");
                Array<XmlReader.Element> frames = sprite.getChildrenByName("frame");

                Array<Frame> frams = new Array<Frame>();

                int duration;

                for (XmlReader.Element frame : frames) {
                    duration = frame.getIntAttribute("time", 1);
                    int[] values = stringArrayToIntArray(frame.getText().split(" "));
                    frams.add(new Frame(
                            (float) duration / 1000,
                            new TextureRegion(tx, values[0], values[1], values[2], values[3]),
                            Integer.valueOf(origin[0]),
                            Integer.valueOf(origin[1])
                    ));
                }

                result.put(animationName, new VariableFrameDurationAnimation(frams));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int[] stringArrayToIntArray(String[] strings) {
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.valueOf(strings[i]);
        }
        return result;
    }
}
