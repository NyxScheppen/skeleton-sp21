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
        switch(direction){
            case "W":
                position = position.shift(0, 1);
                break;
            case "D":
                position = position.shift(1, 0);
                break;
            case "S":
                position = position.shift(0, -1);
                break;
            case "A":
                position = position.shift(-1, 0);
                break;
        }
    }

}
