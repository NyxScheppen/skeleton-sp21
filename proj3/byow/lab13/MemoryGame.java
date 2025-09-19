package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalTime;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!",
                                                    "go get it!", "Winter is come"};

    public static void main(String[] args) {
        /*
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
         */
        MemoryGame game = new MemoryGame(40, 40, 350234);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            int num = rand.nextInt(26);
            sb.append(CHARACTERS[num]);
        }

        return sb.toString();
    }

    public void drawFrame(String s) {
        showCharacter(s);
    }

    private static double getTime() {
        return LocalTime.now().getSecond() + LocalTime.now().getNano() / 1000000000.0;
    }


    public void flashSequence(String letters) {

        for (char s : letters.toCharArray()) {
            StdDraw.clear(StdDraw.BLACK);
            drawFrame(String.valueOf(s));
            double start = getTime();
            while (getTime() - start < 1) {
            }
            StdDraw.clear(StdDraw.BLACK);
            drawFrame("");
            start = getTime();
            while (getTime() - start < 0.5) {
            }
        }
    }

    public String solicitNCharsInput(int n) {

        StringBuilder sb = new StringBuilder();

        while(StdDraw.hasNextKeyTyped()){
            sb.append(StdDraw.nextKeyTyped());
        }

        return sb.toString();
    }

    public void startGame() {
        int round = 0;
        boolean b = true;

        while(true){
            String s = "Round:" + round;
            showCharacter(s);
            String letter = generateRandomString(round * 5);
            flashSequence(letter);
            if(!solicitNCharsInput(round + 1).equals(letter)){
               break;
            }
            round++;
        }

        String s = "Game Over! You made it to round:" + round;
        showCharacter(s);

    }

    private void showCharacter(String s){
        Font mf = new Font(s, Font.BOLD, 30);
        StdDraw.setFont(mf);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();
    }

}
