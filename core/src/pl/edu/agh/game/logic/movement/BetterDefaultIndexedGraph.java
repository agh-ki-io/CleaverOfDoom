package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;
import com.badlogic.gdx.utils.Array;

/**
 * Created by kcpr on 14.05.15.
 */
public class BetterDefaultIndexedGraph extends DefaultIndexedGraph {

    public Array<IndexedNodeImplementation> getNodes() {
        return super.nodes;
    }

    public BetterDefaultIndexedGraph (Array<IndexedNodeImplementation> nodes) {
        super(nodes);
    }


}
