package pl.edu.agh.game.logic.entities.creatures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Debug;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-05-10
 */
public class OnePointEnemy extends pl.edu.agh.game.logic.entities.Character<Circle> {
    private final static float MOVE_EPSILON = 5;
    private final static float TURN_EPSILON = 7;
    private final static float DIRECTION_VALUE = 1;
    protected final Vector2 newPosition = new Vector2();
    protected final Vector2 currentPosition = new Vector2();
    private final Circle newPositionWithEpsilon = new Circle(newPosition, MOVE_EPSILON);
    private boolean destroyed = false;
    private final int collisionGroups;

    public OnePointEnemy(StatsComponent statsComponent, MovementComponent movementComponent,
                         DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent,
                         DrawableComponent drawableComponent, SkillComponent skillComponent, Level level, int collisionGroups) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, skillComponent, level);
        this.collisionGroups = collisionGroups;
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return collidableComponent.overlaps(collidable);
    }

    @Override
    public void collide(Collidable collidable) {
    }

    @Override
    public void damage(Damage damage) {
        System.out.println(this + " received: " + damage.getValue() + " " + damage.getType() + " damage.");
        super.damage(damage);
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return collisionGroups;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);
        Debug.drawCircle(newPositionWithEpsilon.x, newPositionWithEpsilon.y, newPositionWithEpsilon.radius, batch);

    }

    @Override
    public void update(float deltaTime) {
        drawableComponent.update(deltaTime);
        if (!collidableComponent.getShape().contains(newPositionWithEpsilon)) {
            float x;
            float y;

            x = this.newPosition.x - getX();
            y = this.newPosition.y - getY();

            if (x > TURN_EPSILON)
                x = DIRECTION_VALUE;
            else if (x < TURN_EPSILON)
                x = -DIRECTION_VALUE;
            else x = 0f;

            if (y > TURN_EPSILON)
                y = DIRECTION_VALUE;
            else if (y < TURN_EPSILON)
                y = -DIRECTION_VALUE;
            else y = 0f;

            move(x, y, deltaTime);

            collidableComponent.getShape().setPosition(getX(), getY());
        } else {
            if (!movementComponent.getDirection().equals(Direction.LAST)) move(0, 0, deltaTime);
        }
        currentPosition.set(getX(), getY());
    }

    private void attack() {

    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        collidableComponent.getShape().setPosition(x, y);
        setNewDestination(x, y);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setNewDestination(float x, float y) {
        newPosition.x = x;
        newPosition.y = y;

        newPositionWithEpsilon.setPosition(newPosition);
    }

    @Override
    public void useSkill(int id) {

    }
}
