package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class startGame {

    public static character playgame(character player, TETile[][] world){

        if(player == null){
            for(int i = 0; i < world.length; i++){
                for(int j = 0; j < world[0].length; j++){
                    if(world[i][j] == Tileset.FLOOR){
                        player = new character(new Position(i, j));
                        break;
                    }
                }
            }
        }
        showCharacter(world, player.position);

        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                int x = player.position.x;
                int y = player.position.y;
                switch (c){
                    case 'W':
                        player.moveForward("W");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("S");
                            break;
                        }
                        world[x][y] = Tileset.FLOOR;
                        break;
                    case 'A':
                        player.moveForward("A");
                        if(world[player.position.x][player.position.y] == Tileset.WALL){
                            player.moveForward("D");
                            break;
                        }
                        world[x][y] = Tileset.FLOOR;
                        break;
                    case 'S':
                        player.moveForward("S");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("A");
                            break;
                        }
                        world[x][y] = Tileset.FLOOR;
                        break;
                    case 'D':
                        player.moveForward("D");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("A");
                            break;
                        }
                        world[x][y] = Tileset.FLOOR;
                        break;
                    case'Q':
                        return player;
                }
                showCharacter(world, player.position);
            }
        }
    }

    private static void showCharacter(TETile[][] world, Position p){
        world[p.x][p.y] =  Tileset.AVATAR;
        Engine.ter.renderFrame(world);
    }
}
