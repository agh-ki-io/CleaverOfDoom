package pl.edu.agh.game.graphics;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-07
 */
public interface Animation {
    TextureRegion getCurrentFrame();
    void update(float timeDelta);
    void reset();
    int getCurrentFrameIndex();
    boolean isFinished();
    PlayMode getPlayMode();
    void setPlayMode(PlayMode playMode);
    float getAnimationDuration();
    float getOriginX();
    float getOriginY();
}
