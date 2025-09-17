package byow.Core;

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position shift(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}
