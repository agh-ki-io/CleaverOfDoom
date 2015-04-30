package pl.edu.agh.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.drawable.Drawable;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-24
 */
public class Level<T extends Updatable & Drawable & Collidable & GameEntity> implements Updatable, Drawable{
    private final Collection<T> characters;

    private TiledMap map;
    private TiledMapRenderer renderer;

    private int[] backgroundLayers = {0};
    private int[] foregroundLayers = {};

    public Level(TiledMap map, TiledMapRenderer renderer) {
        this.map = map;
        this.renderer = renderer;
        characters = new ConcurrentLinkedDeque<>();
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void update(float deltaTime) {
        for (T character : characters) {
            character.update(deltaTime);
        }

        checkForCollisions();

        for (T character : characters) {
            if (character.isDestroyed()) characters.remove(character);
        }

        AnimatedTiledMapTile.updateAnimationBaseTime();
    }

    /**
     * Full brutal <3
     */
    private void checkForCollisions() {
        for (T one : characters) {
            for (T another : characters) {
                if (!one.equals(another)) {
                    if (one.overlaps(another)) {
                        one.collide(another);
                    }
                }
            }
        }
    }


    @Override
    public void draw(SpriteBatch batch) {
        for (T character : characters) {
            character.draw(batch);
        }
    }

    public void drawCharactersAndLayers(SpriteBatch batch) {
        renderBackground();

        batch.begin();
        draw(batch);
        batch.end();

        renderForeground();
    }

    public void renderBackground() {
        renderer.render(backgroundLayers);
    }

    public void renderForeground() {
        renderer.render(foregroundLayers);
    }

    public<K extends T> void addCharacter(K character) {
        characters.add(character);
    }

    public<K extends T> void removeCharacter(K character) {
        characters.remove(character);
    }

    public<K extends T> void addCharacters(Collection<? extends K> characters) {
        this.characters.addAll(characters);
    }
}
