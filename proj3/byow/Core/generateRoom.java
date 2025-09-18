package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;


public class generateRoom {

    private static boolean isInBounds(TETile[][] world, int x, int y) {
        return x > 0 && y > 0 && x < world.length - 1 && y < world[0].length - 1;
    }

    private static boolean isoccurred(TETile[][] world, Position p){
        return world[p.x][p.y] != Tileset.NOTHING;
    }

    // 在输入位置的斜上方生成一个房间

    public static Room generateRoom(int width, int height, TETile[][] world, Position p) {
        if (canPlaceRoom(width, height, world, p)) {
            generateRoomHelper(width, height, world, p);
            return new Room(p, p.shift(width - 1, height - 1));
        }
        return null;
    }

    private static boolean canPlaceRoom(int width, int height, TETile[][] world, Position p){

        if(!isInBounds(world, p.x, p.y) || world[p.x][p.y] != Tileset.NOTHING || !isInBounds(world, p.x + width + 1, p.y + height + 1) || p.x - 1 < 0 || p.y - 1 < 0) {
            return false;
        }

        for(int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                 if (isoccurred(world, new Position(p.x - 1 + i, p.y - 1 + j))) {
                    return false;
                }
            }
        }
        return true;
    }


    private static void generateRoomHelper(int width, int height, TETile[][] world, Position p){
        for(int i = 0; i < width; i++){
            world[p.x + i][p.y] = Tileset.WALL;
        }
        for(int j = 0; j < height; j++){
            world[p.x][p.y + j] = Tileset.WALL;
        }

        for(int i = 0; i < width; i++){
            world[p.x + i][p.y + height - 1] = Tileset.WALL;

        }

        for(int j = 0; j < height; j++){
            world[p.x + width - 1][p.y + j] = Tileset.WALL;
        }

        for(int i = 0; i < width - 2; i++){
            for(int j = 0; j < height - 2; j++){
                world[i + p.x + 1][j + p.y + 1] = Tileset.FLOOR;
            }
        }
    }


}
