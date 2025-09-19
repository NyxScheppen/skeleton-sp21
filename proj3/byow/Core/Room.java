package byow.Core;

public class Room {
    public Position leftDown;
    public Position rightUp;

    public Room(Position leftDown, Position rightUp) {
        this.leftDown = leftDown;
        this.rightUp = rightUp;
    }
}
