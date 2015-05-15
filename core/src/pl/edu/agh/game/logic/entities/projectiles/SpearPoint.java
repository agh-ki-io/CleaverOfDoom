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
import pl.edu.agh.game.logic.drawable.WeaponType;
import pl.edu.agh.game.stolen_assets.Debug;
import pl.edu.agh.game.stolen_assets.EntityFactory;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-10
 */
public class SpearPoint implements Updatable, Drawable, Collidable, GameEntity {
    private float x;
    private float y;
    private Animation animation;
    private float throwVelocity;
    private int scale = 4;
    private boolean destroyed = false;
    private float relaxation;
    private int dmg;
    private int size;
    private float movementMultiplier;
    private int number;
    private Level level;

    private final Damage damage = new Damage(DamageType.PHYSICAL, 100);
    private final CollidableComponent<Circle> collidableComponent;

    public SpearPoint(float x, float y, Animation animation, Level level, float relaxation, float throwVelocity, int dmg, int size, float movementMultiplier, int number) {
        System.out.println("Spear point at: "+x+" "+y+" animation: "+ animation);
        this.x = x;
        this.y = y;
        this.animation = animation;
        this.level = level;
        this.relaxation = relaxation;
        this.throwVelocity = throwVelocity;
        this.dmg = dmg;
        this.size = size;
        this.movementMultiplier = movementMultiplier;
        this.number = number;
        collidableComponent = new CollidableComponent<>(new Circle(x, y, 5.0f * scale), level.getMap());
    }

    public Weapon getNextSpear() {
        if (number > 0) {
            number--;
            return EntityFactory.getNewWeapon(x,y,level, null, WeaponType.SPEAR, relaxation, throwVelocity, dmg, size, movementMultiplier);
        } else return null;
    }

    @Override
    public void draw(SpriteBatch batch) {
        float originX = animation.getOriginX();
        float originY = animation.getOriginY();
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        Debug.drawCircle(collidableComponent.getShape().x - originX, collidableComponent.getShape().y - originY, collidableComponent.getShape().radius, batch);
        Debug.drawDot(x - originX, y - originY, scale, batch);
    }

    @Override
    public boolean overlaps(Collidable collidable) {
        return collidableComponent.overlaps(collidable);
    }

    @Override
    public void collide(Collidable collidable) {
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return 0;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
    }
}
