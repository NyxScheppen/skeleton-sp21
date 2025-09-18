package byow.Core;

public class Room {
    public Position leftUp;
    public Position rightDown;

    public Room(Position leftUp, Position rightDown) {
        this.leftUp = leftUp;
        this.rightDown = rightDown;
    }

    public int getsize() {
        return (rightDown.x - leftUp.x) * (rightDown.y - leftUp.y);
    }
}
