package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.GameEntity;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.Updatable;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.collisions.CollisionUtil;
import pl.edu.agh.game.logic.damage.Damagable;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageType;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.entities.players.Spearman;
import pl.edu.agh.game.stolen_assets.Debug;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class Weapon implements Updatable, Drawable, Collidable, GameEntity {
    private final int size;
    private final float movementMultiplier;
    private float x;
    private float y;
    private Animation animation;
    private float velocity = 0;
    private float diagonalVelocity;
    private float dx;
    private float dy;
    private int scale = 4;
    private boolean destroyed = false;
    private float ttl = 1.5f;

    private int collisionGroup;

    private final Damage damage;
    private final CollidableComponent<Circle> collidableComponent;

    private float relaxation;
    private float throwVelocity;
    private boolean thrown = false;
    private Spearman spearman;

    public Weapon(float x, float y, Animation animation, float relaxation, float throwVelocity, Level level, int collisionGroup, Spearman spearman, int dmg, int size, float movementMultiplier) {
        if (spearman != null) System.out.println(x+" "+y+" "+spearman.getX()+" "+spearman.getY());
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.relaxation = relaxation;
        this.throwVelocity = throwVelocity;
        this.collisionGroup = collisionGroup;
        this.size = size;
        this.movementMultiplier = movementMultiplier;
        level.addCharacter(this);

        this.spearman = spearman;
        damage = new Damage(DamageType.PHYSICAL, dmg);
        collidableComponent = new CollidableComponent<>(new Circle(x, y, size), level.getMap());
    }

    @Override
    public void update(float deltaTime) {
//        if (spearman != null)
//        System.out.println("location: "+x+" "+y+ thrown+velocity+";  player location: "+spearman.getX()+" "+spearman.getY());
//        else
//            System.out.println("location: "+x+" "+y+ thrown + velocity);
        if (thrown) {
            move(dx, dy);
            velocity *= (1-relaxation);
            diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
        }
        if (velocity <= 0.3f) {
            velocity = 0;
            diagonalVelocity = 0;
            thrown = false;
        }
        if (spearman != null) {
            x = spearman.getX();
            y = spearman.getY();
        }
//        System.out.println(spearman);
        if (animation != null)
        animation.update(deltaTime);
//        move(dx, dy, deltaTime);
//        if (velocity > 0)
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (animation != null) {
            float originX = animation.getOriginX();
            float originY = animation.getOriginY();
            batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
            Debug.drawCircle(collidableComponent.getShape().x - originX, collidableComponent.getShape().y - originY, collidableComponent.getShape().radius, batch);
            Debug.drawDot(x - originX, y - originY, scale, batch);
        }
    }

    private void move(float dx, float dy) {
            float newX;
            float newY;

//        System.out.println(dx+" "+dy+" "+this.x + " "+velocity + " "+ deltaTime +" "+ dx + "      "+x+" "+y);
            if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
                newX = this.x + diagonalVelocity * dx;
                newY = this.y + diagonalVelocity * dy;
            } else {
                newX = this.x + velocity * dx;
                newY = this.y + velocity * dy;
            }

            if (!collidableComponent.collision(newX, newY, "blocked")) {
                collidableComponent.getShape().setPosition(x, y);
                this.x = newX;
                this.y = newY;
            }
    }

    public void throwThis() {
        dx = spearman.drawableComponent.getLastUsableDirection().getDx();
        dy = spearman.drawableComponent.getLastUsableDirection().getDy();
        velocity = throwVelocity;
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
        thrown = true;

//        t
        spearman = null;
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return collidableComponent.overlaps(collidable);
    }

    @Override
    public void collide(Collidable collidable) {
        if (CollisionUtil.collisonGroupMatches(getCollisionGroups(), collidable.getCollisionGroups())) {
            if (collidable instanceof Damagable && thrown) {
                ((Damagable) collidable).damage(damage);
                velocity = 0;
                diagonalVelocity = 0;
                thrown = false;
            }
        }
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return collisionGroup;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public int getSize() {
        return size;
    }

    public float getMovementMultiplier() {
        return movementMultiplier;
    }
    public Damage getDamage() {
        return damage;
    }

    public void setSpearman(Spearman spearman) {
        this.spearman = spearman;
    }

}
