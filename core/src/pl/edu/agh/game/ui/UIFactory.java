package pl.edu.agh.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.io.File;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public class UIFactory {
    public static final String texturesFolder = "touchpad" + File.separator;

    public static Touchpad getTouchpad() {
        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(texturesFolder + "touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture(texturesFolder + "touchKnob.png"));
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");

        Touchpad touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15, 15, 200, 200);
        return touchpad;
    }

    public static Button getButton() {
        Button.ButtonStyle buttonStyle;

        Skin buttonSkin = new Skin();
        buttonSkin.add("up", new Texture(texturesFolder + "touchKnob.png"));
        buttonSkin.add("down", new Texture(texturesFolder + "touchKnobDown.png"));
        buttonSkin.add("checked", new Texture(texturesFolder + "touchKnob.png"));

        buttonStyle = new Button.ButtonStyle(
                buttonSkin.getDrawable("up"),
                buttonSkin.getDrawable("down"),
                buttonSkin.getDrawable("checked")
        );


        Button button = new Button(buttonStyle);

        float rightBound = Gdx.graphics.getWidth();
        button.setBounds(rightBound - (75 + 72), 75, 72, 72);
        return button;
    }
}
