package pl.edu.agh.game.logic.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.CleaverOfDoom;
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
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.players.Warrior;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
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
    private DrawableComponent drawableComponent;
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
    public StatsComponent statsComponent;
    public MovementComponent movementComponent;

    private float relaxation;
    private float throwVelocity;
    private boolean thrown = false;
    private Warrior warrior;

    public Weapon(float x, float y, DrawableComponent drawableComponent, float relaxation, float throwVelocity, Level level, int collisionGroup, Warrior warrior, int dmg, int size, float movementMultiplier) {
        this.x = x;
        this.y = y;
        this.drawableComponent = drawableComponent;
        this.statsComponent = new StatsComponent(1000, movementMultiplier, movementMultiplier,0);
        this.collidableComponent = new CollidableComponent<>(new Circle(x, y, size), level.getMap());
        this.movementComponent = new MovementComponent(velocity, diagonalVelocity, statsComponent, collidableComponent);
        this.drawableComponent.setDrawable(this);
        this.relaxation = relaxation;
        this.throwVelocity = throwVelocity;
        this.collisionGroup = collisionGroup;
        this.size = size;
        this.movementMultiplier = movementMultiplier;
        this.warrior = warrior;
        this.damage = new Damage(DamageType.PHYSICAL, dmg);
        level.addCharacter(this);

    }

    @Override
    public void update(float deltaTime) {
        //Ustawienie direction
        Direction direction;
        if (thrown) {
             direction = Direction.fromVector(dx, dy);
        } else {
            if (warrior == null) {
                direction = Direction.LAST;
            } else {
                direction = warrior.getDirection();
            }
        }
        //Wyzerowanei movementu bo strzaly nie maja animacji
        movementComponent.move(0, 0, 0);
        //Chamskie ustawienie direction w drawablu
        if(!direction.equals(Direction.LAST)) drawableComponent.setLastUsableDirection(direction);
        //zapomniales updatnac drawabla
        drawableComponent.update(deltaTime);
        if (thrown) {
            move(dx, dy, deltaTime);
            movementComponent.setPosition(x, y);
            velocity *= (1-relaxation);
            diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
        }
        if (velocity <= 0.3f) {
            velocity = 0;
            diagonalVelocity = 0;
            thrown = false;
        }
        if (warrior != null) {
            movementComponent.moveWeapon(warrior);
            x= warrior.getX();
            y= warrior.getY();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawableComponent.draw(batch);
        Debug.drawCircle(collidableComponent.getShape().x , collidableComponent.getShape().y , collidableComponent.getShape().radius, batch);
        Debug.drawDot(x , y , scale, batch);
    }

    private void move(float dx, float dy, float deltaTime) {

            float newX;
            float newY;

            if (Math.abs(dx) > CleaverOfDoom.EPSILON && Math.abs(dy) > CleaverOfDoom.EPSILON) {
                newX = this.x + diagonalVelocity * dx;
                newY = this.y + diagonalVelocity * dy;
            } else {
                newX = this.x + velocity * dx;
                newY = this.y + velocity * dy;
            }

            if (!collidableComponent.collision(newX, newY, "blocked")&&!collidableComponent.collision(newX, newY, "slime")&&!collidableComponent.collision(newX, newY, "pit")) {
                collidableComponent.getShape().setPosition(x, y);
                this.x = newX;
                this.y = newY;
            }
//        movementComponent.move(dx, dy, deltaTime);

    }

    public void throwThis() {
        dx = warrior.drawableComponent.getLastUsableDirection().getDx();
        dy = warrior.drawableComponent.getLastUsableDirection().getDy();
        velocity = throwVelocity;
        diagonalVelocity = (float) (velocity * Math.sqrt(2)/2);
        thrown = true;

//        t
        warrior = null;
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

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

}
