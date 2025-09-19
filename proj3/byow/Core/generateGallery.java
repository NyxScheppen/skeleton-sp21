package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class generateGallery {

    private static Random RANDOM;

    public static void generateGallery(TETile[][] world, List<Position> centers, long SEED) {
        RANDOM = new Random(SEED);
        for (int i = 1; i < centers.size(); i++) {
            drawCorridor(world, centers.get(i - 1), centers.get(i));
        }
    }

    // 加油，ai

    // 生成 wall+floor+wall 的直线走廊
    private static void drawCorridor(TETile[][] world, Position from, Position to) {
        Position cur = new Position(from.x, from.y);
        // 先横向，再纵向（或反之）
        // 如果 to.x > from.x，dx 为 1，表示向右移动；
        // 如果 to.x < from.x，dx 为 -1，表示向左移动；
        // 如果相等，dx 为 0，不移动
        int dx = Integer.compare(to.x, from.x);
        int dy = Integer.compare(to.y, from.y);

        // 横向
        while (cur.x != to.x) {
            carveCorridorTile(world, cur.x, cur.y, true);
            cur = cur.shift(dx, 0);
        }
        // 纵向
        while (cur.y != to.y) {
            carveCorridorTile(world, cur.x, cur.y, false);
            cur = cur.shift(0, dy);
        }
        // 终点
        carveCorridorTile(world, cur.x, cur.y, true);
    }

    // 在(x, y)处挖通走廊，并在两侧加wall
    private static void carveCorridorTile(TETile[][] world, int x, int y, boolean horizontal) {
        world[x][y] = Tileset.FLOOR;
        // 如果是横行
        if (horizontal) {
            if (isInBounds(world, x, y - 1) && world[x][y - 1] == Tileset.NOTHING)
                world[x][y - 1] = Tileset.WALL;
            if (isInBounds(world, x, y + 1) && world[x][y + 1] == Tileset.NOTHING)
                world[x][y + 1] = Tileset.WALL;
            // 拐角包上
            if (isInBounds(world, x - 1, y - 1) && world[x - 1][y - 1] == Tileset.NOTHING)
                world[x - 1][y - 1] = Tileset.WALL;
            if (isInBounds(world, x - 1, y + 1) && world[x - 1][y + 1] == Tileset.NOTHING)
                world[x - 1][y + 1] = Tileset.WALL;
            if (isInBounds(world, x + 1, y - 1) && world[x + 1][y - 1] == Tileset.NOTHING)
                world[x + 1][y - 1] = Tileset.WALL;
            if (isInBounds(world, x + 1, y + 1) && world[x + 1][y + 1] == Tileset.NOTHING)
                world[x + 1][y + 1] = Tileset.WALL;
        }
        // 如果是竖行
        else {
            if (isInBounds(world, x - 1, y) && world[x - 1][y] == Tileset.NOTHING)
                world[x - 1][y] = Tileset.WALL;
            if (isInBounds(world, x + 1, y) && world[x + 1][y] == Tileset.NOTHING)
                world[x + 1][y] = Tileset.WALL;
            // 拐角包上
            if (isInBounds(world, x - 1, y - 1) && world[x - 1][y - 1] == Tileset.NOTHING)
                world[x - 1][y - 1] = Tileset.WALL;
            if (isInBounds(world, x + 1, y - 1) && world[x + 1][y - 1] == Tileset.NOTHING)
                world[x + 1][y - 1] = Tileset.WALL;
            if (isInBounds(world, x - 1, y + 1) && world[x - 1][y + 1] == Tileset.NOTHING)
                world[x - 1][y + 1] = Tileset.WALL;
            if (isInBounds(world, x + 1, y + 1) && world[x + 1][y + 1] == Tileset.NOTHING)
                world[x + 1][y + 1] = Tileset.WALL;
        }
    }

    private static boolean isInBounds(TETile[][] world, int x, int y) {
        return x > 0 && y > 0 && x < world.length - 1 && y < world[0].length - 1;
    }
}
