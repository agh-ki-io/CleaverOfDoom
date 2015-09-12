package pl.edu.agh.game.stolen_assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.edu.agh.game.CleaverOfDoom;

import java.io.PrintStream;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-16
 */
public class Debug {
    private static ShapeRenderer shapeRenderer = null;

    public static final TextureRegion pixTexture = new TextureRegion(new Texture(Gdx.files.internal("pix.png")));
    public static final TextureRegion circTexture = new TextureRegion(new Texture(Gdx.files.internal("circ.png")));

    private static final PrintStream logger = System.out;

//    public static void drawCircle(float x, float y, float r, SpriteBatch batch) {
//        ShapeRenderer shapeRenderer = getShapeRenderer();
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.setColor(Color.CYAN);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(x, y, r, 5);
//        shapeRenderer.end();
//
//    }
    public static void drawCircle(float x, float y, float r, SpriteBatch batch) {
        if (CleaverOfDoom.DEBUG) {
            batch.draw(circTexture, x - r, y - r, 0, 0, r* 2, r *2, 1, 1, 0);
        }
    }

    public static ShapeRenderer getShapeRenderer() {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }
        return shapeRenderer;
    }

    public static void drawDot(float x, float y, int scale, SpriteBatch batch) {
        if (CleaverOfDoom.DEBUG) {
            batch.draw(Debug.pixTexture, x, y, 0, 0, 1, 1, scale, scale, 0);
        }
    }

    public static void log(String message) {
        logger.println(message);
    }
}
