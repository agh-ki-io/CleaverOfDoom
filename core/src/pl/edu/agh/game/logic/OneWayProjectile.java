package pl.edu.agh.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.game.Game;
import pl.edu.agh.game.graphics.Animation;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class OneWayProjectile implements Updatable, Drawable {
    private float x;
    private float y;
    private Animation animation;
    private float velocity;
    private float diagonalVelocity;
    private float dx;
    private float dy;

    public OneWayProjectile(float x, float y, Animation animation, float velocity, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.velocity = velocity;
        this.dx = dx;
        this.dy = dy;
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
    }

    @Override
    public void update(float deltaTime) {
        move(dx, dy, deltaTime);
        animation.update(deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(animation.getCurrentFrame(), x, y, animation.getOriginX(), animation.getOriginY(), animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), 4, 4, 0);
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
}
