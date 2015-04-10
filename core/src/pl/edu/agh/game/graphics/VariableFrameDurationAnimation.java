package pl.edu.agh.game.graphics;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;


/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-07
 */
public class VariableFrameDurationAnimation implements Animation {
    private final Array<Frame> frames;
    private float[] reversedPartialSumOfFrameDurations;
    private float[] partialSumOfFrameDurations;
    private float animationDuration;
//    private float durationLeft;
//    private int lastFrameNumber;
    private PlayMode playMode;
    private float stateTime;

    public VariableFrameDurationAnimation(Array<Frame> frames) {
        this(frames, PlayMode.NORMAL);
    }

    public VariableFrameDurationAnimation(Array<Frame> frames, PlayMode playMode) {
        this.frames = frames;
        this.playMode = playMode;

        partialSumOfFrameDurations = new float[frames.size];
        reversedPartialSumOfFrameDurations = new float[frames.size];

        for (int i = 0; i < frames.size; i++) {
            float frameDuration = frames.get(i).getFrameDuration();
            animationDuration += frameDuration;
            partialSumOfFrameDurations[i] = animationDuration;
        }

        float tempDuration = 0;
        for (int i = frames.size - 1; i >= 0; i--) {
            float frameDuration = frames.get(i).getFrameDuration();
            tempDuration += frameDuration;
            reversedPartialSumOfFrameDurations[i] = tempDuration;
        }
//        durationLeft = animationDuration;
    }

    @Override
    public void update(float timeDelta) {
        stateTime += timeDelta;
//        durationLeft -= timeDelta;
    }

    @Override
    public void reset() {
        stateTime = 0;
//        durationLeft = animationDuration;
//        lastFrameNumber = 0;
    }

    @Override
    public TextureRegion getCurrentFrame() {
        return frames.get(getCurrentFrameIndex()).getFrame();
    }

    @Override
    public int getCurrentFrameIndex() {
        if (frames.size == 1) return 0;
        int frameNumber;

        switch(playMode) {
            case NORMAL:
                frameNumber = getFrameNumberNormal(stateTime);
                break;
            case LOOP:
                frameNumber = getFrameNumberNormal(stateTime % animationDuration);
                break;
            case REVERSED:
                frameNumber = getFrameNumberReversed(stateTime);
                break;
            case LOOP_REVERSED:
                frameNumber = getFrameNumberReversed(stateTime % animationDuration);
                break;
            case LOOP_RANDOM:
                frameNumber = ThreadLocalRandom.current().nextInt(frames.size);
                break;
            case LOOP_PINGPONG:
                float tmpState = stateTime % animationDuration;
                if (stateTime % (2 * animationDuration) > tmpState)
                    frameNumber = getFrameNumberReversed(tmpState);
                else frameNumber = getFrameNumberNormal(tmpState);
                break;
            default:
                frameNumber = getFrameNumberNormal(stateTime % animationDuration);
        }
        return frameNumber;
    }

    private int getFrameNumberNormal(float stateTime) {
        int frameNumber = frames.size - 1;
        for (int i = frameNumber; i >= 0; i--) {
            if (stateTime < partialSumOfFrameDurations[i]) {
                frameNumber = i;
            }
        }
        return frameNumber;
    }

    private int getFrameNumberReversed(float stateTime) {
        int frameNumber = 0;
        for (int i = 0; i < frames.size; i++) {
            if (stateTime < reversedPartialSumOfFrameDurations[i]) {
                frameNumber = i;
            }
        }
        return frameNumber;
    }

    @Override
    public boolean isFinished() {
        switch (playMode) {
            case REVERSED:
            case NORMAL:
                return stateTime > animationDuration;
            case LOOP:
            case LOOP_PINGPONG:
            case LOOP_RANDOM:
            case LOOP_REVERSED:
                return false;
            default:
                return true;
        }
    }

    @Override
    public PlayMode getPlayMode() {
        return playMode;
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    @Override
    public float getAnimationDuration() {
        return this.animationDuration;
    }

    @Override
    public float getOriginX() {
        return frames.get(getCurrentFrameIndex()).getOriginX();
    }

    @Override
    public float getOriginY() {
        return frames.get(getCurrentFrameIndex()).getOriginY();
    }
}
