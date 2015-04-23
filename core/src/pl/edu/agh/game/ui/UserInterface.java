package pl.edu.agh.game.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.game.input.Input;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public class UserInterface extends Group {
    private final Input input;

    public UserInterface(Input input) {
        this.input = input;
    }

    public void init() {
    }

    public void update(float deltaTime) {
        getStage().act(deltaTime);
    }

    public void draw() {
        getStage().draw();
    }

    @Override
    public Stage getStage() {
        return super.getStage();
    }

    public void setStage(Stage stage) {
        super.setStage(stage);
    }

    public Input getInput() {
        return input;
    }
}
