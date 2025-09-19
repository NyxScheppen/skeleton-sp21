package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static File f;

    public Engine(){
        f = new File("save.txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 初始化
        ter.initialize(WIDTH, HEIGHT);
    }


    public void interactWithKeyboard() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        int key = 0, i = 0;

        printFrontYard();

        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if(c == 'N'){
                    StdDraw.clear(Color.black);
                    String s = "please type in random seed";
                    showCharacter(s, new Position(WIDTH/2, HEIGHT/2), 30);
                }
                if(c == 'L'){
                    Game game = loadGame();
                    play(game.world, game.player);
                }
                if(Character.isDigit(c)){
                    key *= Math.pow(10, i);
                    key += c;
                    i++;
                }
                if(c == 'S'){
                    goWorld(finalWorldFrame, key);
                    play(finalWorldFrame, null);
                }
            }
        }
    }


    public TETile[][] interactWithInputString(String input) {

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        goWorld(finalWorldFrame, 350234);
        play(finalWorldFrame, null);
        return finalWorldFrame;
    }


    private void goWorld(TETile[][] world, long key){
        DrawWorld dw = new DrawWorld(WIDTH, HEIGHT, key);
        dw.initWorld(world);
        dw.drawWorld(world);
    }

    private void play(TETile[][] world, character player){
        ter.renderFrame(world);
        character c = startGame.playgame(player, world);
        saveGame(world, c);
    }

    private void saveGame(TETile[][] world, character player) {
        Game game = new Game(world, player);
        game.savegame(f.toPath());
    }

    private Game loadGame(){
        Game game = Game.loadGame(f.toPath());
        return game;
    }

    private void printFrontYard(){
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        showCharacter("WELCOME TO CS61B'S WORLD!", new Position(WIDTH/2, 30), 20);
        showCharacter("New Game(N)", new Position(WIDTH/2,20), 15);
        showCharacter("Load Game(L)", new Position(WIDTH/2, 15), 15);
        showCharacter("Quit(Q)", new Position(WIDTH/2, 10), 15);
    }




    private void showCharacter(String s, Position p, int size){
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(p.x, p.y, s);
        StdDraw.show();
    }
}