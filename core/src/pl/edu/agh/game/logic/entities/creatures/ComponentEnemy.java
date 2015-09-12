package pl.edu.agh.game.logic.entities.creatures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.projectiles.OneWayProjectile;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Debug;
import pl.edu.agh.game.stolen_assets.EntityFactory;

import java.awt.*;
import java.util.Queue;


public class ComponentEnemy extends pl.edu.agh.game.logic.entities.Character<Circle> {
    private final InputState inputState;
    private final Queue<Point> points;

    private Point new_pos = new Point();

    private final float vers = 1f;
    private final float Eps = 2*vers;
    private boolean fire = false;
    private boolean destroyed = false;

    public ComponentEnemy(float x, float y,StatsComponent statsComponent, MovementComponent movementComponent,
                          DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent,
                          DrawableComponent drawableComponent, InputState inputState,
                          SkillComponent skillComponent,
                          Queue<Point> queue, Level level) {
        super(statsComponent, damageComponent, collidableComponent, drawableComponent, movementComponent, skillComponent, level);
        this.inputState = inputState;
        this.points = queue;
        this.new_pos = points.poll();
        setPosition(x, y);
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
        //System.out.println(this + " received: " + damage.getValue() + " " + damage.getType() + " damage.");
        super.damage(damage);
    }

    @Override
    public Shape2D getShape() {
        return collidableComponent.getShape();
    }

    @Override
    public int getCollisionGroups() {
        return 2 | 4;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        Debug.drawCircle(collidableComponent.getShape().x, collidableComponent.getShape().y, collidableComponent.getShape().radius, batch);
    }

    @Override
    public void update(float deltaTime) {
        drawableComponent.update(deltaTime);
        float x;
        float y;
        boolean new_d = false;
        if(Math.abs((float)this.new_pos.getX()-this.movementComponent.getX()) <= this.Eps &&
                Math.abs((float)this.new_pos.getY()-this.movementComponent.getY()) <= this.Eps)
        {
            this.movementComponent.setPosition((float) this.new_pos.getX(),(float)this.new_pos.getY());
            this.points.add(this.new_pos);
            this.new_pos=this.points.poll();
            new_d = true;
        }
        x = (float)this.new_pos.getX() - this.movementComponent.getX();
        y = (float)this.new_pos.getY() - this.movementComponent.getY();
        if(x>0)
            x = vers;
        else if(x < 0)
            x=-vers;
        else x = 0f;

        if(y>0)
            y = vers;
        else if(y<0)
            y = -vers;
        else y= 0f;

        if (this.fire)
            attack();
        this.fire= false;
        if(new_d)
            this.fire=true;
        move(x, y, deltaTime);

        collidableComponent.getShape().setPosition(getX(), getY());
    }

    private void attack() {
        if (drawableComponent.isFree()) {
            Direction direction = this.drawableComponent.getLastUsableDirection();
            OneWayProjectile projectile = EntityFactory.getNewEnemyArrow(getX(), getY(), 700, direction, level);
            level.addCharacter(projectile);
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void useSkill(int id) {

    }
}
