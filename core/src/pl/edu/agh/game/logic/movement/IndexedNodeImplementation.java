package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

/**
 * Created by kcpr on 14.05.15.
 */
public class IndexedNodeImplementation implements IndexedNode {

    int x;
    int y;
    int index;

    Array<Connection> connections = new Array<>();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IndexedNodeImplementation(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Array<Connection> getConnections() {
        return null;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

}
