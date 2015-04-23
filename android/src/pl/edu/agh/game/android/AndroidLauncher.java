package pl.edu.agh.game.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.input.JoystickInput;
import pl.edu.agh.game.ui.UserInterface;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		JoystickInput joystickInput = new JoystickInput();
		UserInterface userInterface = new UserInterface(joystickInput);
		joystickInput.setUserInterface(userInterface);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		initialize(new CleaverOfDoom(userInterface), config);
	}
}
