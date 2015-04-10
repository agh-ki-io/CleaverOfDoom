package pl.edu.agh.game.logic;

import pl.edu.agh.game.Game;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public enum Direction {
    NORTH       ("north", 0, 1),
    NORTHWEST   ("northwest", -1, 1),
    NORTHEAST   ("northeast", 1, 1),
    SOUTH       ("south", 0, -1),
    SOUTHWEST   ("southwest", -1, -1),
    SOUTHEAST   ("southeast", 1, -1),
    WEST        ("west", -1, 0),
    EAST        ("east", 1, 0),
    LAST        ("last", 0, 0);

    private final String string;
    private float dx;
    private float dy;

    Direction(String string, float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        this.string = string;
    }


    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    @Override
    public String toString() {
        return string;
    }

    public static Direction fromVector(float x, float y) {
        if (x > Game.EPSILON) {
            if (y > Game.EPSILON) return NORTHEAST;
            else if ( y < -Game.EPSILON) return SOUTHEAST;
            else return EAST;
        } else if (x < -Game.EPSILON) {
            if (y > Game.EPSILON) return NORTHWEST;
            else if ( y < -Game.EPSILON) return SOUTHWEST;
            else return WEST;
        } else {
            if (y > Game.EPSILON) return NORTH;
            else if ( y < -Game.EPSILON) return SOUTH;
            else return LAST;
        }
    }
}
