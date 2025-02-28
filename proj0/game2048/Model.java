package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author nyx
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed = false;
        checkGameOver();
        if(side == Side.NORTH){
            for(int i = 0;i < board.size();i++){
                 int[] m = helper(board,i);
                 score += m[0];
                 if(m[1] == 1){
                    changed = true;
                }
            }
            return changed;
        }
        else{
            board.setViewingPerspective(side);
            changed = tilt(Side.NORTH);
            if (changed) {
                setChanged();
            }
            board.setViewingPerspective(Side.NORTH);
            return changed;
        }
    }
    public int[] helper(Board b,int i){
        int x = 0;
        int m = 0;
        // 当前上面那个数
        int y = 0;
        int[] score = {0,0};
        for(int j = 3;j >= 0;j -= 1){
            Tile t = b.tile(i,j);
            if(t == null){
                x += 1;
            }
            else{
                // 第一个数且不是第一个
                if(y == 0 && x != 0) {
                    b.move(i,3,t);
                    y = t.value();
                    score[1] = 1;
                    m = 2;
                }
                // 第一个就有数
                else if(y == 0 && x == 0){
                    y = t.value();
                    m = 2;
                }
                // 上面有数,且上面的数与自身相等
                else if(y == t.value()){
                    b.move(i,m+1,t);
                    score[0] += 2*y;
                    score[1] = 1;
                    y = 1;
                }
                // 上面有数且与自身不相等
                else {
                    b.move(i,m,t);
                    score[1] = 1;
                    m -= 1;
                    y = t.value();
                }
            }
        }
        return score;
    }
    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        boolean x = false;
        for(int i = 0;i < b.size();i += 1){
            for(int j = 0;j < b.size();j += 1){
                if(b.tile(i,j) == null){
                    x = true;
                }
            }
        }
        return x;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        boolean x = false;
        for(int i = 0;i < b.size();i += 1){
            for(int j = 0;j < b.size();j += 1){
                if(b.tile(i,j) != null){
                    if(b.tile(i,j).value() == MAX_PIECE){
                        x = true;
                    }
                }
            }
        }
        return x;
    }
    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        boolean x = false;
        if(emptySpaceExists(b))
        {
            x = true;
        }
        else
        {
            for (int i = 0; i < b.size(); i += 1)
            {
                for (int j = 0; j < b.size() - 1; j += 1)
                {
                    if (b.tile(i, j).value() == b.tile(i, j + 1).value())
                    {
                        x = true;
                    }
                }
            }
            for (int i = 0; i < b.size()-1; i += 1)
            {
                for (int j = 0; j < b.size(); j += 1)
                {
                    if (b.tile(i, j).value() == b.tile(i+1, j).value())
                    {
                        x = true;
                    }
                }
            }
        }
        return x;
    }

    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
