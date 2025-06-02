package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

public class Repository {

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File head = join(GITLET_DIR, "head");
    public static final File Objects = join(GITLET_DIR, "Objects");
    public static final File Index = join(GITLET_DIR, "Index");
    public static final File refs = join(GITLET_DIR, "refs");
    public static File commits = join(refs, "commits");
    public static File heads = join(refs, "heads");
    static index stage = readObject(Index, index.class);
    public static File blobs = join(GITLET_DIR, "blobs");
    static Commit headof = readObject(head, Commit.class);

    public static boolean initist(){
        if(!GITLET_DIR.exists()){
            System.out.print("Not in an initialized Gitlet directory.");
            return false;
        }
        return true;
    }

    public static void init(){
        Commit initial = new Commit("initial commit", null, "00:00:00 UTC, Thursday, 1 January 1970",null);
        if(initist()){
           System.out.print("A Gitlet version-control system already exists in the current directory.");
           return;
        }
        GITLET_DIR.mkdir();
        Objects.mkdir();
        refs.mkdir();
        heads.mkdir();
        commits.mkdir();
        blobs.mkdir();

        try {
            head.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(head, initial.getHash_code());
        headof = readObject(head, Commit.class);
        branch("master");

        try {
            Index.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File initialcommit = join(commits, sha1(initial));
        try {
            initialcommit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(initialcommit, initial);
    }

    public static void indexinit(){
        Commit head = readObject(heads, Commit.class);
        for(String sha_1 : head.files){
            File headcommit = join(blobs,sha_1);
            stage.add(headcommit.getName(), readContents(headcommit));
        }
    }

    public static void add(String filename){
        if(!initist()){
            return;
        }
        indexinit();
        File input = join(CWD, filename);
        if(!input.exists()){
            System.out.print("File does not exist");
            return;
        }
        stage.add(filename, serialize(input));
    }

    public static void rm(String name){
        if(!initist()){
            return;
        }
        if(!stage.contains(name)){
            System.out.print("No reason to remove the file.");
            return;
        }
        stage.remove(name);
    }

    public static void commit(String message){
        if(!initist()){
            return;
        }
        // 搞到缓冲区里的文件
        Commit newcommit = new Commit(message, sha1(headof), null, stage.Keyset());
        // 在头文件那里更新
        writeContents(head, newcommit);
        // 在branch里更新
        stage.bloblism(blobs);
        stage.clear();
    }

    public static File branch(String name){
        if(!initist()){
            return null;
        }
        File newbranch = join(heads,name);
        try {
            newbranch.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newbranch, headof.getHash_code());
        return newbranch;
    }
}
