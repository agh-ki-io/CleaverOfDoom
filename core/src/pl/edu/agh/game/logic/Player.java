package pl.edu.agh.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.stolen_assets.Debug;
import pl.edu.agh.game.stolen_assets.Util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class Player implements Updatable, Drawable, Damagable, Collidable {
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
        animation = animationMap.get("north-walk");
        animation.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        setPosition(500, 500);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        float originX = animation.getOriginY() + 1;
        float originY = animation.getOriginX();
//        float originX = animation.getCurrentFrame().getRegionWidth() - animation.getOriginX();
//        float originY = animation.getCurrentFrame().getRegionHeight() - animation.getOriginY();
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        batch.draw(Debug.pixTexture, x, y, 0, 0, 1, 1, scale, scale, 0);
        for (OneWayProjectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    public void update(float deltaTime) {
        animation.update(deltaTime);
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
    }

    private void move(float dx, float dy, float deltaTime) {
        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            this.x += diagonalVelocity * deltaTime * dx;
            this.y += diagonalVelocity * deltaTime * dy;
        } else {
            this.x += velocity * deltaTime * dx;
            this.y += velocity * deltaTime * dy;
        }
    }

    public void attackSkill1(Direction direction) {
        OneWayProjectile projectile = new OneWayProjectile(x + animation.getOriginY(), y + animation.getOriginX(), shotsAnimationMap.get(direction.toString()), 200, direction);
        projectiles.add(projectile);
    }

    @Override
    public boolean overlaps(Collidable collidable) {

        return false;
    }

    @Override
    public void collide(Collidable collidable) {

    }

    @Override
    public Shape2D getShape() {
        return null;
    }

    @Override
    public void damage(Damage damage) {

    }

    @Override
    public void destroy() {

    }
}
