package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

public class character {

    public Position position;
    public int health;

    public character(Position position){
        this.position = position;
        this.health = 5;
    }

    public void moveForward(String direction){
        StdDraw.clear();
        switch(direction){
            case "W":
                position.shift(0, 1);
                break;
            case "D":
                position.shift(1, 0);
                break;
            case "S":
                position.shift(0, -1);
                break;
            case "A":
                position.shift(-1, 0);
                break;
        }
    }

}
