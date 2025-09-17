package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final long SEED = 350234;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final Random RANDOM = new Random(SEED);
    int wantsize = 3;
    int wanttessize = 5;

    public void addHexagon(TETile[][] world, int s, Position p, TETile t) {
        if(s < 2) return;

        int width = 3 * s - 2;
        int height = 2 * s;
        for (int row = 0; row < height; row++) {
            int rowY = p.y + row;
            int rowWidth = s + 2 * Math.min(row, height - row - 1);
            int rowXStart = p.x + (width - rowWidth) / 2;
            for (int col = 0; col < rowWidth; col++) {
                world[rowXStart + col][rowY] = t;
            }
        }
    }

    private Position getTopRightNeighbor(Position p, int s) {
        return p.shift(2 * s - 1, s);
    }

    private Position getBottomRightNeighbor(Position p, int s) {
        return p.shift(2 * s - 1, -s);
    }

    private Position getRightNeighbor(Position p, int s) {
        return p.shift(2 * s - 1, 0);
    }

    private void addHexagonColumn(TETile[][] world, int s, Position p, int num) {
        if(num < 1) return;

        addHexagon(world, s, p, randomTile());

        if(num > 1) {
            Position pUp = Position(p, s);
            addHexagonColumn(world, s, pUp, num - 1);
        }
    }

    private static  class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    public static Position Position(Position p, int s) {
        return p.shift(0, -2 * s);
    }

    private void init(int WIDTH, int HEIGHT, TETile[][] hexWorld) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void drawWorld(TETile [][] hexWorld, int hexsize, Position p, int tessize) {

        for(int i = 0; i < tessize; i++) {
            p = getTopRightNeighbor(p, hexsize);
            addHexagonColumn(hexWorld, hexsize, p, tessize + i);
        }

        for(int i = 0; i < tessize - 1; i++) {
            p = getBottomRightNeighbor(p, hexsize);
            addHexagonColumn(hexWorld, hexsize, p, 2 * tessize - i - 2);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        HexWorld hw = new HexWorld();
        hw.init(WIDTH, HEIGHT, hexWorld);
        Position p = new Position(10, 50);
        hw.drawWorld(hexWorld, hw.wantsize, p, hw.wanttessize);
        ter.renderFrame(hexWorld);
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.FLOOR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }
}
