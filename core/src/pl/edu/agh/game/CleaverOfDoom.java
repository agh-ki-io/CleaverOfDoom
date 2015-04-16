package pl.edu.agh.game;

import com.badlogic.gdx.Game;
import pl.edu.agh.game.screens.MenuScreen;

public class CleaverOfDoom extends Game {
	@Override
	public void create () {
        setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
}
