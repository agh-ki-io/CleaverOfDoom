package pl.edu.agh.game.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import pl.edu.agh.game.logic.collisions.Collidable;
import pl.edu.agh.game.logic.drawable.Drawable;
import pl.edu.agh.game.logic.entities.Character;
import pl.edu.agh.game.logic.entities.players.ComponentPlayer;
import pl.edu.agh.game.logic.entities.players.Player;
import pl.edu.agh.game.stolen_assets.EntityFactory;

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

    private final Player[] players = new Player[4];

    public Level(TiledMap map, TiledMapRenderer renderer) {
        this.map = map;
        this.renderer = renderer;
        characters = new ConcurrentLinkedDeque<>();

        initializeCharactersFromMap();
    }

    private void initializeCharactersFromMap() {
        MapLayer objectMapLayer = map.getLayers().get("objects");
        float scale = Float.parseFloat(map.getProperties().get("scale", "1.0", String.class));
        for (MapObject mapObject : objectMapLayer.getObjects()) {
            Integer gid = mapObject.getProperties().get("gid", Integer.class);
            if (gid != null) {
                TiledMapTile tile = map.getTileSets().getTile(gid);
                String objectID = tile.getProperties().get("object_id", "none", String.class);
                int collisionGroups = Integer.parseInt(tile.getProperties().get("collision_groups", "0", String.class));
                if (objectID.equals("none")) {
                    throw new RuntimeException("Object on map does not have object_id property.");
                } else if (objectID.matches("player._spawn")) {
                    ComponentPlayer newPlayer = EntityFactory.getNewPlayer(Player.Profession.ARCHER, this);
                    newPlayer.setPosition(mapObject.getProperties().get("x", Float.class) * scale, mapObject.getProperties().get("y", Float.class) * scale);
                    addCharacter((T) newPlayer);
                    players[java.lang.Character.digit(objectID.charAt(6), 10)] = newPlayer;
                } else {
                    Character enemy  = EntityFactory.getNewEnemy(objectID, collisionGroups, this);
                    enemy.setPosition(mapObject.getProperties().get("x", Float.class) * scale, mapObject.getProperties().get("y", Float.class) * scale);
                    addCharacter((T) enemy);
                }
            }
        }
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

    public Player[] getPlayers() {
        return players;
    }
}
