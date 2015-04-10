package pl.edu.agh.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.game.Game;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.stolen_assets.Util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class Player implements Updatable, Drawable {
    private float x;
    private float y;
    private float velocity = 300;
    private float diagonalVelocity = (float) (Math.sqrt(2)/2 * velocity);
    private float scale = 4;

    private Animation animation;
    private final Map<String, Animation> animationMap;
    private final Map<String, Animation> shotsAnimationMap;
    private final InputState inputState;

    private Direction direction = Direction.SOUTH;

    Collection<OneWayProjectile> projectiles = new LinkedList<>();

    public Player(InputState inputState) {
        this.inputState = inputState;
        animationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"));
        shotsAnimationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/projectiles/player_arrow_1.xml"));
        animation = animationMap.get("south-walk");
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(animation.getCurrentFrame(), x, y, animation.getOriginX(), animation.getOriginY(), animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        for (OneWayProjectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    public void update(float deltaTime) {
        Direction newDirection = Direction.fromVector(inputState.getxDirection(), inputState.getyDirection());

        if (animation.isFinished() || animation.getPlayMode().equals(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP) || animation.getPlayMode().equals(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_PINGPONG) || animation.getPlayMode().equals(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_REVERSED)) {
            if (!newDirection.equals(Direction.LAST)) {
                direction = newDirection;
                animation = animationMap.get(direction.toString() + "-walk");
                animation.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
            } else if (newDirection.equals(Direction.LAST)) {
                animation = animationMap.get(direction.toString());
            }

            if (inputState.isSkill1Used()) {
                animation = animationMap.get(direction.toString() + "-attack");
                animation.reset();
                attackSkill1(direction);
            }
        }

        for (OneWayProjectile projectile : projectiles) {
            projectile.update(deltaTime);
        }
        move(inputState.getxDirection(), inputState.getyDirection(), deltaTime);
        animation.update(deltaTime);
    }

    private void move(float dx, float dy, float deltaTime) {
        if (Math.abs(dx) > Game.EPSILON && Math.abs(dy) > Game.EPSILON) {
            this.x += diagonalVelocity * deltaTime * dx;
            this.y += diagonalVelocity * deltaTime * dy;
        } else {
            this.x += velocity * deltaTime * dx;
            this.y += velocity * deltaTime * dy;
        }
    }

    public void attackSkill1(Direction direction) {
        OneWayProjectile projectile = new OneWayProjectile(x, y, shotsAnimationMap.get(direction.toString()), 2000, direction.getDx(), direction.getDy());
        projectiles.add(projectile);
    }
}
