package byow.Core;

import byow.TileEngine.TETile;

import java.io.*;
import java.nio.file.Path;

public class Game implements Serializable {

    public TETile[][] world;

    public character player;

    public Game(TETile[][] world, character player){
        this.player = player;
        this.world = world;
    }

    public void savegame(Path path){
        File store = new File(String.valueOf(path));
        if(!store.exists()){
            try {
                store.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileOutputStream output = new FileOutputStream(store);
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject(this);
            output.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Game loadGame(Path path){

        File read = new File(String.valueOf(path));
        FileInputStream fileIn;
        ObjectInputStream in;
        Game game;

        try {
            fileIn = new FileInputStream(read);
            in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return game;
    }

}

