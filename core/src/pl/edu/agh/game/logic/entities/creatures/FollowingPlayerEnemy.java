package pl.edu.agh.game.logic.entities.creatures;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Circle;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.IndexedNodeImplementation;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.skills.SkillComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

import java.util.concurrent.ThreadLocalRandom;

public class FollowingPlayerEnemy extends OnePointEnemy {
    private final static float POINT_TTL_VALUE = 3f;
    private float point_ttl = POINT_TTL_VALUE;
    private final static float MOVE_DELTA = 200;

    // At least this following two should depend on type of enemy.
    private final int CHASE_RANGE = 500;
    private int ATTACK_DISTANCE = 40;
    private final int CHASE_RANGE_IN_TILES = (int)(Math.sqrt(2) * CHASE_RANGE / 75); // 75 should be taken from map.

    GraphPath<IndexedNodeImplementation> graphPath;

    int index;

    private float playerX;
    private float playerY;

    private float collisionRadius;

    public FollowingPlayerEnemy(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, SkillComponent skillComponent, Level level, int collisionGroups) {
        super(statsComponent, movementComponent, damageComponent, collidableComponent, drawableComponent, skillComponent, level, collisionGroups);
        collisionRadius = collidableComponent.getShape().radius;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float currentPlayerX = level.getPlayers()[1].getX();
        float currentPlayerY = level.getPlayers()[1].getY();

        double distanceToPlayer = Math.hypot(getX() - currentPlayerX, getY() - currentPlayerY);

        if ((playerX != currentPlayerX || playerY != currentPlayerY) &&
                distanceToPlayer < CHASE_RANGE) {
            playerX = currentPlayerX;
            playerY = currentPlayerY;

            //System.out.println(playerX + " " + playerY);

            graphPath = level.getPathFinder().findPath((int) getX(), (int) getY(), (int) playerX, (int) playerY);
            
            if (graphPath.getCount() == 0 || graphPath.getCount() > CHASE_RANGE_IN_TILES)
                graphPath = null;
            else {
                index = 1;

                if (index < graphPath.getCount())
                    setNewDestination(graphPath.get(index).getX(), graphPath.get(index).getY());
            }
        }

        // If hero is no longer in range it will try to chase him anyway.
        if (graphPath == null) {
            point_ttl -= deltaTime;
            if (point_ttl <= 0) {
                point_ttl += POINT_TTL_VALUE;
                setNewDestination(getX() + getRandom(), getY() + getRandom());
                useSkill(0);
            }
        } else if (index + 1 < graphPath.getCount()) {


//            System.out.println("i: " + index + " " + dx + " " + dy);
            if (Math.hypot(getX() - graphPath.get(index).getX(), getY() - graphPath.get(index).getY()) < collisionRadius) {
                index++;
                setNewDestination(graphPath.get(index).getX(), graphPath.get(index).getY());
//                System.out.println("New destination " + graphPath.get(index).getX() + " " + graphPath.get(index).getY());
            }
//            else
//                System.out.println(getX() + " " + getY());
        }
        else if (distanceToPlayer >= CHASE_RANGE)
            graphPath = null;

        if (distanceToPlayer < ATTACK_DISTANCE) useSkill(0);


    }

    private float getRandom() {
        return (ThreadLocalRandom.current().nextFloat() - 0.5f) * MOVE_DELTA;
    }

    public void setATTACK_DISTANCE(int ATTACK_DISTANCE) {
        this.ATTACK_DISTANCE = ATTACK_DISTANCE;
    }

}
