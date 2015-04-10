package pl.edu.agh.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Player;
import pl.edu.agh.game.stolen_assets.Util;
import pl.edu.agh.game.ui.UserInterface;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import java.util.Map;

public class Game extends ApplicationAdapter {
	public static final float EPSILON = 1E-2f;
	InputState inputState;
	SpriteBatch batch;
	Texture img;

	private Player player;

	private final Input input;
	private final UserInterface userInterface;

	private double lastSkill;

	public Game(UserInterface ui) {
		this.input = ui.getInput();
		this.userInterface = ui;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
//		animationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"));
//		animation = animationMap.get("channeling");


		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		Stage stage = new Stage(viewport, batch);
		userInterface.setStage(stage);
		stage.addActor(userInterface);
		inputState = input.getInputState();
		Gdx.input.setInputProcessor(input.getInputProcessor());


		player = new Player(inputState);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(animation.getCurrentFrame(), x, y, animation.getOriginX(), animation.getOriginY(), animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), 4, 4, 0);
//		userInterface.draw(batch, 1);
		player.draw(batch);
		batch.end();

		userInterface.draw();
	}

	public void update(float deltaTime) {

//		Direction newDirection = Direction.fromVector(inputState.getxDirection(), inputState.getyDirection());
//
//		if (animation.isFinished() || animation.getPlayMode().equals(PlayMode.LOOP) || animation.getPlayMode().equals(PlayMode.LOOP_PINGPONG) || animation.getPlayMode().equals(PlayMode.LOOP_REVERSED)) {
//			if (!newDirection.equals(Direction.LAST)) {
//				direction = newDirection;
//				animation = animationMap.get(direction.toString() + "-walk");
//				animation.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
//			} else if (newDirection.equals(Direction.LAST)) {
//				animation = animationMap.get(direction.toString());
//			}
//
//			if (inputState.isSkill1Used()) {
//				animation = animationMap.get(direction.toString() + "-attack");
//				System.out.println(lastSkill - System.currentTimeMillis());
//				lastSkill = System.currentTimeMillis();
//				animation.reset();
//			}
//
//		}
//
//		animation.update(deltaTime);

//		if (Math.abs(inputState.getxDirection()) > Game.EPSILON && Math.abs(inputState.getyDirection()) > Game.EPSILON) {
//			x += diagonalVelocity * deltaTime * inputState.getxDirection();
//			y += diagonalVelocity * deltaTime * inputState.getyDirection();
//		} else {
//			x += velocity * deltaTime * inputState.getxDirection();
//			y += velocity * deltaTime * inputState.getyDirection();
//		}
		player.update(deltaTime);
		userInterface.update(deltaTime);
	}
}
