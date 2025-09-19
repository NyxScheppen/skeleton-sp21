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
        showCharacter(player.position);

        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                switch (c){
                    case 'W':
                        player.moveForward("W");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("S");
                        }
                        break;
                    case 'A':
                        player.moveForward("A");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("D");
                        }
                        break;
                    case 'S':
                        player.moveForward("S");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("A");
                        }
                        break;
                    case 'D':
                        player.moveForward("D");
                        if(world[player.position.x][player.position.y] ==Tileset.WALL){
                            player.moveForward("A");
                        }
                        break;
                    case'Q':
                        return player;
                }
            }
            showCharacter(player.position);
        }
    }

    private static void showCharacter(Position p){
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.yellow);
        StdDraw.text(p.x, p.y,"@");
        StdDraw.show();
    }
}
