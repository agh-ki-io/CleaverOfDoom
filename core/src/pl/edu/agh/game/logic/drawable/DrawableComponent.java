package pl.edu.agh.game.logic.drawable;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.graphics.AnimationType;
import pl.edu.agh.game.logic.Direction;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Debug;

import java.util.Map;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class DrawableComponent {
    private StatsComponent statsComponent;
    private MovementComponent movementComponent;

    private Animation animation;
    private Direction lastUsableDirection;
    private AnimationType animationType = AnimationType.STANDBY;
    private final Map<String, Animation> animationMap;
    private Character character;

    public DrawableComponent(Map<String, Animation> animationMap) {
        this.animationMap = animationMap;

        lastUsableDirection = Direction.SOUTH;
        animation = animationMap.get("south");
    }

    public void draw(SpriteBatch batch) {
        float originX = animation.getOriginY() + 1;
        float originY = animation.getOriginX();
        float x = movementComponent.getX();
        float y = movementComponent.getY();
        int scale = 4;
        batch.draw(animation.getCurrentFrame(), (int) x, (int) y, originX, originY, animation.getCurrentFrame().getRegionWidth(), animation.getCurrentFrame().getRegionHeight(), scale, scale, 0);
        batch.draw(Debug.pixTexture, x, y, 0, 0, 1, 1, scale, scale, 0);
    }

    public void update(float deltaTime) {
        switch (animationType) {
            case MOVEMENT:
                animation.update(deltaTime * statsComponent.getMovementSpeedMultiplier());
                break;
            case ATTACK:
                animation.update(deltaTime * statsComponent.getAttackSpeedMultiplier());
                break;
            default:
                animation.update(deltaTime);
        }

        Direction direction = movementComponent.getDirection();
        if (isFree()) {
            if (!direction.equals(Direction.LAST)) {
                animationType = AnimationType.MOVEMENT;
                lastUsableDirection = direction;
                animation = animationMap.get(lastUsableDirection.toString() + "-walk");
                animation.setPlayMode(PlayMode.LOOP);
            } else if (direction.equals(Direction.LAST)) {
                animationType = AnimationType.STANDBY;
                animation = animationMap.get(lastUsableDirection.toString());
            }
        }
    }

    public boolean isFree() {
        return animation.isFinished()
                || animation.getPlayMode().equals(PlayMode.LOOP)
                || animation.getPlayMode().equals(PlayMode.LOOP_PINGPONG)
                || animation.getPlayMode().equals(PlayMode.LOOP_RANDOM)
                || animation.getPlayMode().equals(PlayMode.LOOP_REVERSED);
    }

    public Direction getLastUsableDirection() {
        return lastUsableDirection;
    }

    public void setCharacter(Character character) {
        this.character = character;

        this.statsComponent = character.statsComponent;
        this.movementComponent = character.movementComponent;
    }

    /**
     * Check is animation is free before setting animation.
     * @param animationType
     */
    public void setAnimation(AnimationType animationType) {
        switch (animationType) {
            case ATTACK:
                this.animationType = animationType;
                animation = animationMap.get(lastUsableDirection.toString() + "-attack");
                animation.reset();
                animation.setPlayMode(PlayMode.NORMAL);
        }
    }
}
