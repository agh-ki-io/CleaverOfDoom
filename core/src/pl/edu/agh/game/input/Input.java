package pl.edu.agh.game.input;

import com.badlogic.gdx.InputProcessor;
import pl.edu.agh.game.input.InputState;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public interface Input {
    InputState getInputState();
    InputProcessor getInputProcessor();
}
