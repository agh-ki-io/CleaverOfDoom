package pl.edu.agh.game.input;

import com.badlogic.gdx.InputProcessor;
import pl.edu.agh.game.settings.InputSettings;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-03
 */
public class DesktopInputProcessor implements Input, InputProcessor {
    private final InputSettings inputSettings;
    private final InputState inputState;

    public DesktopInputProcessor(InputSettings inputSettings, InputState inputState) {
        this.inputSettings = inputSettings;
        this.inputState = inputState;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == inputSettings.getMoveDownKey()) inputState.setyDirection(inputState.getyDirection() - 1);
        else if (keycode == inputSettings.getMoveUpKey()) inputState.setyDirection(inputState.getyDirection() + 1);
        else if (keycode == inputSettings.getMoveLeftKey()) inputState.setxDirection(inputState.getxDirection() - 1);
        else if (keycode == inputSettings.getMoveRightKey()) inputState.setxDirection(inputState.getxDirection() + 1);
        else if (keycode == inputSettings.getSkill1Key()) inputState.setSkill1Used(true);
        else if (keycode == inputSettings.getSkill2Key()) inputState.setSkill2Used(true);
        else if (keycode == inputSettings.getSkill3Key()) inputState.setSkill3Used(true);
        else if (keycode == inputSettings.getSkill4Key()) inputState.setSkill4Used(true);
        else if (keycode == inputSettings.getMenuKey()) inputState.setMenuOn(true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == inputSettings.getMoveDownKey()) inputState.setyDirection(inputState.getyDirection() + 1);
        else if (keycode == inputSettings.getMoveUpKey()) inputState.setyDirection(inputState.getyDirection() - 1);
        else if (keycode == inputSettings.getMoveLeftKey()) inputState.setxDirection(inputState.getxDirection() + 1);
        else if (keycode == inputSettings.getMoveRightKey()) inputState.setxDirection(inputState.getxDirection() - 1);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public InputState getInputState() {
        return inputState;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }
}
