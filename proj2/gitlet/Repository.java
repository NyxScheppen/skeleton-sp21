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
    private static index stage = readforstage();
    public static File blobsm = join(GITLET_DIR, "blobs");
    static Commit headof = readObject(head, Commit.class);


    private static index readforstage(){
        index newindex;
        if(Index.exists()){
            newindex = readObject(Index, index.class);
        }else{
            newindex = null;
        }
        return newindex;
    }
    
    public static boolean initist(){
        if(!GITLET_DIR.exists()){
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
        blobsm.mkdir();

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
        stage = readObject(Index, index.class);

        File initialcommit = join(commits, sha1(initial));
        try {
            initialcommit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(initialcommit, initial);
    }

    public static void add(String filename){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
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
        stage.remove(name);
    }

    public static void commit(String message){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        Commit newcommit = new Commit(message, headof.getHash_code(), null, stage.Keyset());
        writeContents(head, newcommit);
        String dir = newcommit.getHash_code().substring(0,2);
        File newdir = join(commits,dir);
        if(!newdir.exists()){
            newdir.mkdir();
        }
        File newcmt = join(newdir, newcommit.getHash_code().substring(2));
        try {
            newcmt.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newcmt, newcommit);
        stage.clear();
    }

    public static File branch(String name){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
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

    public static void getlog(){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        Commit nowcommit = headof;
        while(nowcommit != null){
            System.out.print("===");
            System.out.print("commit " + nowcommit.getHash_code());
            System.out.print("Date: " + nowcommit.getTimestamp());
            System.out.print(nowcommit.getMessage());
            System.out.print("\n" + "===");
            File newcommit = join(commits, nowcommit.getParent());
            nowcommit = readObject(newcommit, Commit.class);
        }
    }
}
