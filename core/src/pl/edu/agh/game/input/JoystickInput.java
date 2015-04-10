package pl.edu.agh.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.edu.agh.game.ui.UIFactory;
import pl.edu.agh.game.ui.UserInterface;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public class JoystickInput implements Input {
    private Button skill1Button;
    private Touchpad touchpad;
    private InputState inputState;
    private UserInterface userInterface;

    public JoystickInput() {
    }

    public void init() {
        skill1Button = UIFactory.getButton();
        touchpad = UIFactory.getTouchpad();
        userInterface.addActor(touchpad);
        userInterface.addActor(skill1Button);
        inputState = getCustomInputState();
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public InputState getInputState() {
        init();
        return inputState;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return userInterface.getStage();
    }

    private InputState getCustomInputState() {
        final float jump = 0.3f;
        final InputState newInputState = new InputState() {
            @Override
            public float getxDirection() {
                if (touchpad.getKnobPercentX() > jump) {
                    return 1;
                } else if (touchpad.getKnobPercentX() < -jump) {
                    return -1;
                } else return 0;
            }

            @Override
            public float getyDirection() {
                if (touchpad.getKnobPercentY() > jump) {
                    return 1;
                } else if (touchpad.getKnobPercentY() < -jump) {
                    return -1;
                } else return 0;
            }
        };

        skill1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newInputState.setSkill1Used(true);
            }
        });

        return newInputState;
    }
}
