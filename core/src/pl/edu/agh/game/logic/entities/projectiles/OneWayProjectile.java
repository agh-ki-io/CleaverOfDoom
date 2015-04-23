package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.stolen_assets.Debug;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class OneWayProjectile implements Updatable, Drawable, Collidable {
    private float x;
    private float y;
    private Animation animation;
    private float velocity;
    private float diagonalVelocity;
    private float dx;
    private float dy;
    private int scale = 4;

    private final Circle collisionArea;

    public OneWayProjectile(float x, float y, Animation animation, float velocity, Direction direction) {
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.velocity = velocity;
        this.dx = direction.getDx();
        this.dy = direction.getDy();
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);

        collisionArea = new Circle(x, y, 2.25f * scale);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        move(dx, dy, deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        float originX = animation.getOriginX();
        float originY = animation.getOriginY();
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        Debug.drawCircle(collisionArea.x - originX, collisionArea.y - originY, collisionArea.radius, batch);
        batch.draw(Debug.pixTexture, x - originX, y - originY, 0, 0, 1, 1, scale, scale, 0);
    }

    private void move(float dx, float dy, float deltaTime) {
        if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
            this.x += diagonalVelocity * deltaTime * dx;
            this.y += diagonalVelocity * deltaTime * dy;
        } else {
            this.x += velocity * deltaTime * dx;
            this.y += velocity * deltaTime * dy;
        }

        collisionArea.setPosition(x, y);
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
}
