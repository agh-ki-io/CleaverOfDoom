package pl.edu.agh.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.input.DesktopInputProcessor;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.input.JoystickInput;
import pl.edu.agh.game.settings.DesktopInputSettings;
import pl.edu.agh.game.ui.UserInterface;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920 / 2;
		config.height = 1080 / 2;

		DesktopInputProcessor inputProcessor = new DesktopInputProcessor(new DesktopInputSettings(), new InputState());
		JoystickInput joystickInput = new JoystickInput();
		UserInterface userInterface = new UserInterface(inputProcessor);
		joystickInput.setUserInterface(userInterface);
		new LwjglApplication(new CleaverOfDoom(userInterface), config);
	}
}
