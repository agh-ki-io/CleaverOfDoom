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

import static java.lang.Math.abs;

public class FollowingPlayerEnemy extends OnePointEnemy {
    private final static float POINT_TTL_VALUE = 3f;
    private float point_ttl = POINT_TTL_VALUE;
    private final static float MOVE_DELTA = 200;

    GraphPath<IndexedNodeImplementation> graphPath;

    int index;
    float playerX;
    float playerY;

    public FollowingPlayerEnemy(StatsComponent statsComponent, MovementComponent movementComponent, DamageComponent damageComponent, CollidableComponent<Circle> collidableComponent, DrawableComponent drawableComponent, SkillComponent skillComponent, Level level, int collisionGroups) {
        super(statsComponent, movementComponent, damageComponent, collidableComponent, drawableComponent, skillComponent, level, collisionGroups);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (playerX != level.getPlayers()[1].getX() || playerY != level.getPlayers()[1].getY()) {
            playerX = level.getPlayers()[1].getX();
            playerY = level.getPlayers()[1].getY();

            System.out.println(playerX + " " + playerY);

            graphPath = level.getPathFinder().findPath((int) getX(), (int) getY(), (int) playerX, (int) playerY);

            index = 1;

            if (index < graphPath.getCount())
                setNewDestination(graphPath.get(index).getX(), graphPath.get(index).getY());
        }

        if (index + 1 < graphPath.getCount() && (abs((int)getX() - (int)graphPath.get(index).getX()) < 15) && (abs((int)getY() - (int)graphPath.get(index).getY())) < 15) {
            index++;
            setNewDestination(graphPath.get(index).getX(), graphPath.get(index).getY());
        }

//        point_ttl -= deltaTime;
//        if (point_ttl <= 0) {
//            point_ttl += POINT_TTL_VALUE;
//            setNewDestination(getX() + getRandom(), getY() + getRandom());
//            useSkill(0);
//        }
    }

    private float getRandom() {
        return (ThreadLocalRandom.current().nextFloat() - 0.5f) * MOVE_DELTA;
    }
}
