package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static byow.Core.generateGallery.generateGallery;
import static byow.Core.generateRoom.generateRoom;

public class DrawWorld {

    public static int WIDTH;
    public static int HEIGHT;

    private static long SEED;
    private static Random RANDOM;

    public DrawWorld(int width, int height, long seed) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    public void initWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void drawWorld(TETile[][] world) {
        // 考虑一下画房间的算法
        Position[] p = new Position[200];
        for(int i = 0; i < p.length; i++) {
            p[i] = new Position(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
        }
        List<Position> plist = new ArrayList<>();
        for(int i = 0; i < p.length; i++) {
            int x,y;
            do{
                x = RANDOM.nextInt(15);
                y = RANDOM.nextInt(15);
            }while(x < 3 || y < 3);
            Room Entry = generateRoom(x, y, world, p[i]);
            if(Entry != null){
                plist.add(Entry.leftDown.shift(0, 1));
            }
        }
        generateGallery(world, plist, SEED);
    }
}

