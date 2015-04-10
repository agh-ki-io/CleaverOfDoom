package pl.edu.agh.game.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-07
 */
public class Frame {
    private float frameDuration;
    private final TextureRegion frame;
    private float originX;
    private float originY;

    public Frame(float frameDuration, TextureRegion frame, float originX, float originY) {
        this.frameDuration = frameDuration;
        this.frame = frame;
        this.originX = originX;
        this.originY = originY;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public TextureRegion getFrame() {
        return frame;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }
}
