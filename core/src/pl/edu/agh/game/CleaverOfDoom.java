package pl.edu.agh.game;

import com.badlogic.gdx.Game;
import pl.edu.agh.game.screens.PlayableScreen;
import pl.edu.agh.game.ui.UserInterface;

public class CleaverOfDoom extends Game {
	public static final float EPSILON = 1E-2f;
	public static final boolean DEBUG = false;
	private final UserInterface userInterface;

	public CleaverOfDoom(UserInterface userInterface) {
		this.userInterface = userInterface;
	}


	@Override
	public void create () {
        setScreen(new PlayableScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}
}
