package pl.edu.agh.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.stolen_assets.Debug;

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
    private int scale = 4;

    public OneWayProjectile(float x, float y, Animation animation, float velocity, Direction direction) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.velocity = velocity;
        this.dx = direction.getDx();
        this.dy = direction.getDy();
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        move(dx, dy, deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, animation.getOriginX(), animation.getOriginY(), animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        batch.draw(Debug.pixTexture, x, y, 0, 0, 1, 1, scale, scale, 0);
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
}
