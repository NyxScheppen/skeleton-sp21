package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

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
    static Commit headof = readforheadof();


    private static index readforstage(){
        index newindex;
        if(Index.exists()){
            newindex = readObject(Index, index.class);
        }else{
            newindex = null;
        }
        return newindex;
    }

    private static Commit readforheadof(){
        Commit newindex;
        if(head.exists()){
            newindex = readObject(head, Commit.class);
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
        Commit initial = new Commit("initial commit", null, "00:00:00 UTC, Thursday, 1 January 1970",new HashSet<String>());
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
        writeContents(head, Utils.serialize(initial));
        headof = initial;
        branch("master");

        index dummy = new index();

        try {
            Index.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeContents(Index, Utils.serialize(dummy));

        writeContents(head, Utils.serialize(initial));
        String dir = initial.getHash_code().substring(0,2);
        File newdir = join(commits,dir);
        if(!newdir.exists()){
            newdir.mkdir();
        }
        File newcmt = join(newdir, initial.getHash_code().substring(2));
        try {
            newcmt.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newcmt, Utils.serialize(initial));
        blobs newblob = new blobs("initial", null);
        File newfile = join(blobsm, newblob.getSha_1());
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newfile, Utils.serialize(newblob));
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
        stage.add(filename, readContents(input));
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
        writeContents(head, Utils.serialize(newcommit));
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
        writeContents(newcmt, Utils.serialize(newcommit));
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
        writeContents(newbranch, Utils.serialize(headof));
        return newbranch;
    }

    public static void getlog(){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        Commit nowcommit = headof;
        while(nowcommit != null){
            System.out.print("===\n");
            System.out.print("commit " + nowcommit.getHash_code());
            System.out.print("\nDate: " + nowcommit.getTimestamp());
            System.out.print("\n" + nowcommit.getMessage());
            System.out.print("\n\n" + "===\n");
            if(nowcommit.getParent() != null){
                File newcommit = join(commits, nowcommit.getParent().substring(0,2));
                File newcommitagain = join(newcommit, nowcommit.getParent().substring(2));
                nowcommit = readObject(newcommitagain, Commit.class);
            }
            else{
                break;
            }
        }
    }
}
