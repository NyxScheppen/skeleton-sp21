package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.File;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;


    public static File f;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    public TETile[][] interactWithInputString(String input) {

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        // 处理下输入
        DrawWorld dw = new DrawWorld(WIDTH, HEIGHT, 1919810);

        dw.initWorld(finalWorldFrame);
        dw.drawWorld(finalWorldFrame, input);
        return finalWorldFrame;
    }

    private void saveGame(TETile[][] world) {
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        TETile[][] t = e.interactWithInputString("n123q");
        e.ter.initialize(WIDTH, HEIGHT);
        e.ter.renderFrame(t);
    }
}
